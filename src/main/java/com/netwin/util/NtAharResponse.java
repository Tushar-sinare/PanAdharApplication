package com.netwin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NtAharResponse {
	

		private JdbcTemplate jdbcTemplate;


		@Autowired
		public NtAharResponse(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		public String getNtResponse(int msgCode,String userUuid) {
			List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(QueryUtil.NETWRESPFIELDQUERY, String.class,
					"AV", "Y");
			
			String error = jdbcTemplate.queryForObject(QueryUtil.ERRORS, String.class, msgCode);

			Map<String, Object> netResponse = new HashMap<>();
			Map<String, Object> resultVO = new HashMap<>();
			for (String res : netwinResFieldResults1) {
				 if (res.equals("statusCode")) {
					 netResponse.put(res, 999);
				} else if (res.equals("message")) {
					resultVO.put("msgDescr", error);
				}else if (res.equals("success")){
					resultVO.put(res, "FALSE");
				}else if (res.equals("userReqSrNo")){
					resultVO.put(res, userUuid);
				}else {
					netResponse.put(res, "null");
					resultVO.put("msgCode", msgCode);
					netResponse.put("resultVO", resultVO);
				}
			}
			
			

			return netResponse.toString();
		}

		public String getNtResponses(int msgCode, String message) {
			List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(QueryUtil.NETWRESPFIELDQUERY, String.class,
					"AV", "Y");
			
			//String error = jdbcTemplate.queryForObject(QueryUtil.ERRORS, String.class, msgCode);

			Map<String, Object> netResponse = new HashMap<>();

			for (String res : netwinResFieldResults1) {
				 if (res.equals("status_code")) {
					 netResponse.put(res, msgCode);
				} else if (res.equals("message")) {
					netResponse.put(res, message);
				}else if (res.equals("success")){
					netResponse.put(res, "0");
				}else {
					netResponse.put(res, "null");
				}
			}
			
			

			return netResponse.toString();
		}

	
	}

	

