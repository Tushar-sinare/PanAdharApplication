package com.netwin.service.impl;

import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dozer.Mapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.netwin.dto.CustomerVendorDetailsDto;
import com.netwin.dto.GtVndrRequestDto;
import com.netwin.dto.GtVndrResponseDto;
import com.netwin.dto.PnVndrRequestDto;
import com.netwin.dto.PnVndrResponseDto;
import com.netwin.entity.GtVendorDetails;
import com.netwin.entity.GtVndrRequest;
import com.netwin.entity.GtVndrResponse;
import com.netwin.entity.PnVendorDetails;
import com.netwin.entity.PnVndrRequest;
import com.netwin.entity.PnVndrResponse;
import com.netwin.repo.GtVndrDetailsRepo;
import com.netwin.repo.GtVndrRequestRepo;
import com.netwin.repo.GtVndrResponseRepo;
import com.netwin.service.GtResponseService;
import com.netwin.service.GtVndrRequestService;
import com.netwin.util.EncryptionAndDecryptionData;
import com.netwin.util.GtNtResponse;
import com.netwin.util.QueryUtil;
@Service
public class GtVndrRequestServiceImpl implements GtVndrRequestService{
private GtVndrDetailsRepo gtVndrDetailsRepo;
	private GtNtResponse gtNtResponse;
	private EncryptionAndDecryptionData encryptionAndDecryptionData;
	private Mapper mapper;
	private GtVndrRequestRepo gtVndrRequestRepo;
	private JdbcTemplate jdbcTemplate;
	private GtVndrResponseRepo gtVndrResponseRepo;
	private GtResponseService gtResponseService;
	@Autowired
	public GtVndrRequestServiceImpl(GtVndrDetailsRepo gtVndrDetailsRepo,GtNtResponse gtNtResponse,EncryptionAndDecryptionData encryptionAndDecryptionData,Mapper mapper,GtVndrRequestRepo gtVndrRequestRepo,JdbcTemplate jdbcTemplate,GtVndrResponseRepo gtVndrResponseRepo,GtResponseService gtResponseService) {
		this.gtVndrDetailsRepo = gtVndrDetailsRepo;
		this.gtNtResponse = gtNtResponse;
		this.encryptionAndDecryptionData = encryptionAndDecryptionData;
		this.mapper = mapper;
		this.gtVndrRequestRepo = gtVndrRequestRepo;
		this.jdbcTemplate = jdbcTemplate;
		this.gtVndrResponseRepo = gtVndrResponseRepo;
		this.gtResponseService = gtResponseService;
	}
	@Override
	public String callVenderRequest(String vendorRequestJson, CustomerVendorDetailsDto customerVendorDetailsDto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, JsonProcessingException {
		String apiUrl = null;
		
		Object id = customerVendorDetailsDto.getGtReqMasSrNo();
		System.out.println(customerVendorDetailsDto.getVendorId());
		GtVendorDetails gtVendorDetails = gtVndrDetailsRepo.findByGtVnDrSrNo(customerVendorDetailsDto.getVendorId());
		
		
		if (gtVendorDetails == null) {
			return gtNtResponse.getNtResponse(425,id.toString());
		}
		apiUrl = gtVendorDetails.getGtVrfyURL();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(gtVendorDetails.getGtVnDrApiUser(), gtVendorDetails.getGtVndrApiPsw());
		String requestBody = vendorRequestJson;
		
		requestBody = requestBody.substring(1, requestBody.length() - 1);
		// Split the string by comma and equal sign
		String[] keyValuePairs = requestBody.split(", ");
		GtVndrRequestDto gtVndrRequestDto = new GtVndrRequestDto();
		// Create a JSONObject and add key-value pairs
		JSONObject jsonObject = new JSONObject();
		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			jsonObject.put(entry[0], entry[1]);
		}
		
		gtVndrRequestDto.setReqDecrypt(jsonObject.toString());
		gtVndrRequestDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(jsonObject.toString()));
		gtVndrRequestDto.setEntryDate(new Date(System.currentTimeMillis()));
		gtVndrRequestDto.setGtReqMasSrNo(customerVendorDetailsDto.getGtReqMasSrNo());
		
		HttpEntity<String> requestEntity = new HttpEntity<>(jsonObject.toString(), headers);
		GtVndrRequest gtVndrRequest = mapper.map(gtVndrRequestDto, GtVndrRequest.class);
		
		gtVndrRequestRepo.save(gtVndrRequest);
	
		Map<String, String> aharRequestJsonMap = jsonStringToMap(gtVndrRequestDto);

		// details object
		// Database field Name Fetch.
		Map<String, Object> netwinRequestpara = getNetwinRequestParas();
		
		for (Map.Entry<String, Object> netwinField : netwinRequestpara.entrySet()) {
			if (!aharRequestJsonMap.containsKey(netwinField.getKey())
					&& (netwinField.getValue().toString()).equals("Y")) {
				return gtNtResponse.getNtResponse(500,id.toString());

			}
		}
		String vndrResponseStr = callPanVerifyApi(apiUrl, HttpMethod.POST, requestEntity, customerVendorDetailsDto);
		
		return gtResponseService.customerResponseMapping(vndrResponseStr, customerVendorDetailsDto);
	}
	
	private String callPanVerifyApi(String apiUrl, HttpMethod post, HttpEntity<String> requestEntity,
			CustomerVendorDetailsDto customerVendorDetailsDto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
RestTemplate restTemplate = new RestTemplate();
		
		
		// Make the HTTP request using RestTemplate
		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, post, requestEntity,String.class);
		String response = responseEntity.getBody();
String str = "{gadtlAnnualTurnoverFy=2022-2023, gadtlMerUseruid=null,\"+"
+"\"gadtlBusName=UJJIVAN SMALL FINANCE BANK LIMITED, applVersion=null,\"+"
+ "\"gadtlLegalName=UJJIVAN SMALL FINANCE BANK LIMITED,\"+"
+"\"gadtlAadharValidDate=0005-12-04T18:30:00.000+00:00, gstPromotersList=[\"Ittira Poonollil Davis \"],\"+"
+"\"update_time=null, gadtlTaxpayerType=Regular, gadtlMessage=Success,\"+"
+"\"gadtlPercInCashFy=, gadtlDateOfCancellation=0005-12-04T18:30:00.000+00:00,\"+"
+"\"gadtlAadharValid=No, gadtlCenterJurisdiction=State - CBIC,Zone - GUWAHATI,\"+"
+"\"Commissionerate - GUWAHATI,Division - GUWAHATI I DIVISION,Range - I-A RANGE (Jurisdictional Office),\"+"
+"\"gadtlSrno=null, gadtlDateOfReg=0036-03-08T18:30:00.000+00:00,\"+"
+"\"gadtlAnnualTurnover=Slab: Rs. 500 Cr. and above, gadtlPercInCash=NA,\"+"
+"\"gadtlStateJurisdiction=State - Assam,Zone - Guwahati Zone-A,Unit - Guwahati-A,\"+"
+"\"Circle - GUWAHATI - A - 1, gadtlFieldVisitConducted=No, gadtlProcessId=null, \"+"
+"\"gadtlRefProcuuid=null, gadtlPanno=AABCU9603R, entry_time=null, gadtlSuccess=true,\"+"
+"\"gadtlMobileNo=7348801961, \"+"
+"\"gadtlResp={\"data\": {\"client_id\": \"gst_otp_eZtltcThlrBggSPeskwG\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"fallback\": false, \"details\": {\"contact_details\": {\"principal\": {\"address\": \"First Floor,\"+"
+"\"Prithivi Mansion, 3512-DISPUR, G.S. Road, Lachit Nagar, opp. KFC building,\"+"
+"\"Kamrup, Assam, 781007\", \"email\": \"financetax@ujjivan.com\",\"+"
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business,\"+"
+"\"Supplier of Services, Recipient of Goods or Services, Office / Sale Office,\"+"
+"\"Others\"}, \"additional\": [{\"address\": \"Mirza Santipur NH-37, PS-Palashbari,\"+"
+"\"PO Mirza, Dist-Kamrup Rural, Kamrup, Assam, 781125\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Office / Sale Office, Supplier of Services,\"+"
+"\"Recipient of Goods or Services, Retail Business, Others\"},\"+" 
+"\"{\"address\": \"N.A.C.C.I. Foundation, CHAMBER BHAWAN, 3559-Tezpur, Binjraj Road,\"+"
+"\"P. o. and P.S. - Tezpur, Dist., Binjraj Road, P. o. and P.S. - Tezpur,\"+"
+"\"Dist., Sonitpur, Assam, 784154\", \"email\": \"financetax@ujjivan.com\",\"+"
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business,\"+"
+"\"Office / Sale Office, Supplier of Services, Recipient of Goods or Services,\"+"
+"\"Others\"}, {\"address\": \"Ground Floor, House No. 9,, Kalapahar - 3513,\"+"
+"\"Ground Floor, House No. 9,, Lokhra Road -, Kamrup, Assam, 781018\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others, Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"Maligaon Gate No-1, Opp., Maligaon - 3514, Maligaon Gate No-1,\"+"
+"\"Opp.Guwahati, Guwahati, Kamrup, Assam, 781011\", \"email\": \"financetax@ujjivan.com\",\"+"
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business, Others, Recipient of Goods or Services,\"+"
+"\"Supplier of Services\"}, {\"address\": \"Ground Floor ,, Sonapur - 3531,\"+"
+"\"New Market, Sonapur, Kamrup, Assam, 782402\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others,\"+" 
+"\"Supplier of Services, Recipient of Goods or Services\"},\"+" 
+"\"{\"address\": \"Narengi Tinali,, Narengi - 3533, MT Road,, Narengi Tinali,\"+"
+"\"MT Road, Near- EG Nursing home opposite side, Kamrup, Assam, 781026\",\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"Mirza Santipur NH-37,, Azara - 3538, PS - Palashbari,\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"PO - Mirza,, Dist - Kamrup Rural, Assam,, Kamrup, Assam, 781125\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others, \"+"
+"\"Recipient of Goods or Services, Supplier of Services\"}, \"+"
+"\"{\"address\": \"Vill- Hajo, Majursupa near Pakhamela,, Sualkuchi - 3539,\"+"
+"\"PO-Hajo,, Dist-Kamrup Rural, Assam, Kamrup, Assam, 781102\",\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others, Recipient of Goods or Services, \"+"
+"\"Supplier of Services\"}, {\"address\": \"Ground and 1st Floor,, Nagaon - 3560,\"+"
+"\"Opp Haibargaon Boys High School ,, Haibargaon, AT Road, Nagaon,\"+"
+"\"Assam, 782002\", \"email\": \"financetax@ujjivan.com\",\"+" 
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business, Others,\"+" 
+"\"Supplier of Services, Recipient of Goods or Services\"},\"+"
+"\"{\"address\": \"Nalbari Town Road,, Nalbari - 3561, ward No. 5,, Dist. Nalbari,\"+"
+"\"Assam, Nalbari, Assam, 781335\", \"email\": \"financetax@ujjivan.com\",\"+"
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+"
+"\"{\"address\": \"Baihata Chariali, Mangaldoi Road,, Baihati Chairali - 3562,\"+"
+"\"Shanti Market Opp. Durga Mandir,, Dist - Kamrup, Assam, Kamrup, Assam, 781381\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"Hospital Road NR Tokani circle,, Golaghat - 3563, \"+"
+"\"Ward no-4,, Golaghat Assam,, Golaghat, Assam, 785621\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others, Recipient of Goods or Services,\"+"
+"\"Supplier of Services\"}, {\"address\": \"M.G. Road ,, MG Road - Jorhat - 3564,\"+"
+"\"Opp Old Public Bus Stand, Jorhat, Jorhat, Assam, 785001\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others,\"+" 
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"Ground and First Floor,, Kenduguri Jorhat - 3565,\"+"
+"\"Kenduguri Tiniali Junction,, Charigaon, Jorhat, Jorhat, Assam, 785010\",\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others, Supplier of Services,\"+"
+"\"Recipient of Goods or Services\"}, {\"address\": \"Raha Natun Charaili,,\"+" 
+"\"Raha - 3566, Near-Hussain Hardware,, Nagaon, Nagaon, Assam, 782103\",\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"Ward no-5,Titabor Chairali,, Titabor - 3567,\"+" 
+"\"Opp. Titabor Medical Store,, Dist- Jorhat,, Jorhat, Assam,\"+" 
+"\"785630\", \"email\": \"financetax@ujjivan.com\",\"+" 
+"\"\"mobile\": \"7348801961\", \"nature_of_business\": \"Retail Business,\"+"
+"\"Others, Recipient of Goods or Services, Supplier of Services\"},\"+" 
+"\"{\"address\": \"House No -196,, Pathsala - 3568, Station road,Pathsala,\"+"
+"\"NR SBI branch Dist, Barpeta, Barpeta, Assam, 781325\",\"+" 
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+"
+"\"\"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"},\"+"
+"\"{\"address\": \"Baroda Lane, Hospital Road,, Silchar - 3622, ,\"+" 
+"\"Ambicapatty Point, Silchar, Cachar, Assam, 788001\",\"+"
+"\"\"email\": \"financetax@ujjivan.com\", \"mobile\": \"7348801961\",\"+" 
+"\"\"nature_of_business\": \"Retail Business, Others,\"+"
+"\"Recipient of Goods or Services, Supplier of Services\"}]}},\"+" 
+"\"\"promoters\": [\"Ittira Poonollil Davis \"],\"+" 
+"\"\"annual_turnover\": \"Slab: Rs. 500 Cr. and above\",\"+"
+"\"\"annual_turnover_fy\": \"2022-2023\", \"percentage_in_cash_fy\": \"\",\"+" 
+"\"\"percentage_in_cash\": \"NA\", \"aadhaar_validation\": \"No\",\"+" 
+"\"\"aadhaar_validation_date\": \"1800-01-01\", \"address_details\": {},\"+" 
+"\"\"liability_percentage_details\": {}, \"less_info\": false,\"+"
+"\"\"client_id\": null, \"gstin\": \"18AABCU9603R1ZM\",\"+" 
+"\"\"pan_number\": \"AABCU9603R\", \"business_name\": \"UJJIVAN SMALL FINANCE BANK LIMITED\",\"+"
+"\"\"legal_name\": \"UJJIVAN SMALL FINANCE BANK LIMITED\",\"+" 
+"\"\"center_jurisdiction\": \"State - CBIC,Zone - GUWAHATI,\"+"
+"\"Commissionerate - GUWAHATI,Division - GUWAHATI I DIVISION,\"+"
+"\"Range - I-A RANGE (Jurisdictional Office)\",\"+" 
+"\"\"state_jurisdiction\": \"State - Assam,Zone - Guwahati Zone-A,Unit - Guwahati-A,\"+"
+"\"Circle - GUWAHATI - A - 1\", \"date_of_registration\": \"2017-09-30\",\"+" 
+"\"\"constitution_of_business\": \"Public Limited Company\",\"+" 
+"\"\"taxpayer_type\": \"Regular\", \"gstin_status\": \"Active\",\"+" 
+"\"\"date_of_cancellation\": \"1800-01-01\", \"field_visit_conducted\": \"No\",\"+" 
+"\"\"nature_bus_activities\": [\"Retail Business\", \"Supplier of Services\",\"+" 
+"\"\"Recipient of Goods or Services\", \"Office / Sale Office\", \"Others\"],\"+" 
+"\"\"nature_of_core_business_activity_code\": \"SPO\",\"+" 
+"\"\"nature_of_core_business_activity_description\": \"Service Provider and Others\",\"+" 
+"\"\"filing_status\": [], \"address\": null, \"hsn_info\": {\"goods\": [],\"+" 
+"\"\"services\": [{\"description\": \"BUSINESS AUXILIARY SERVICES\",\"+"
+"\"\"hsn\": \"00440225\"}, {\"description\": \"BANKING AND FINANCIAL\",\"+" 
+"\"\"hsn\": \"00440173\"}]}, \"filing_frequency\": []}}\",\"+"
+"\"\"status_code\": 200, \"success\": true, \"message\": \"Success\", \"+"
+"\"\"message_code\": \"success\"}";

		GtVndrResponseDto gtVndrResponseDto = new GtVndrResponseDto();
		gtVndrResponseDto.setUserGstNo(customerVendorDetailsDto.getUserGstNo());
		gtVndrResponseDto.setEntryDate(new Date(System.currentTimeMillis()));
		gtVndrResponseDto.setReqDecrypt(response);
		gtVndrResponseDto.setReqEncrypt(encryptionAndDecryptionData.getEncryptResponse(response));
		gtVndrResponseDto.setGtReqMasSrNo(customerVendorDetailsDto.getGtReqMasSrNo());
		GtVndrResponse gtVndrResponse = mapper.map(gtVndrResponseDto,GtVndrResponse.class);
		gtVndrResponseRepo.save(gtVndrResponse);
	return response;
	
	}
	private Map<String, Object> getNetwinRequestParas() {
		List<Map<String, Object>> netwinFieldResultsMap = null;

		netwinFieldResultsMap = jdbcTemplate.queryForList(QueryUtil.NETWNFIELDQUERY11, "G");
		
	// Process the results using Java Streams
	return netwinFieldResultsMap.stream()
			.collect(Collectors.toMap(vendorField -> (String) vendorField.get("VNDRREQKEYNAME"),
					vendorField -> (String) vendorField.get("VNDRREQKEYREQ")));

	}
	private Map<String, String> jsonStringToMap(GtVndrRequestDto gtVndrRequestDto) {
		String gtRequestDecryptString = gtVndrRequestDto.getReqDecrypt();
		Gson gson = new Gson();
		Type type = new com.google.gson.reflect.TypeToken<Map<String, String>>() {
		}.getType();
		return gson.fromJson(gtRequestDecryptString, type);
	}

}
