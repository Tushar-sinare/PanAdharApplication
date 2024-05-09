package com.netwin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class GtNtResponse {


		private JdbcTemplate jdbcTemplate;


		@Autowired
		public GtNtResponse(JdbcTemplate jdbcTemplate) {
			this.jdbcTemplate = jdbcTemplate;
		}

		public String getNtResponse(int msgCode,String userUuid) {
			List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(QueryUtil.NETWRESPFIELDQUERY, String.class,
					"G", "Y");
			String error = jdbcTemplate.queryForObject(QueryUtil.ERRORS, String.class, msgCode);

			Map<String, Object> netResponse = new HashMap<>();
			Map<String, Object> resultVo = new HashMap<>();

			for (String res : netwinResFieldResults1) {
				if (res.equals("success")) {
					resultVo.put(res, "FALSE");
				} else if (res.equals("msgCode")) {
					resultVo.put(res, msgCode);
				} else if (res.equals("msgDescr")) {
					resultVo.put(res, error);
				}else if (res.equals("userReqSrNo")) {
					resultVo.put(res, userUuid);
				}else if (res.equals("statusCode")) {
					netResponse.put(res, "999");
				} else {
					netResponse.put(res, "null");
				}
			}
			netResponse.put("ResultVO", resultVo);

			return netResponse.toString();
		}
		public String getNtResponses(int msgCode,String getMessage) {
			List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(QueryUtil.NETWRESPFIELDQUERY, String.class,
					"G", "Y");
			//String error = jdbcTemplate.queryForObject(QueryUtil.ERRORS, String.class, msgCode);

			Map<String, Object> netResponse = new HashMap<>();
			Map<String, Object> resultVo = new HashMap<>();

			for (String res : netwinResFieldResults1) {
				if (res.equals("success")) {
					resultVo.put(res, "FALSE");
				}  else if (res.equals("error")) {
					resultVo.put(res, "TRUE");
				} else if (res.equals("msgCode")) {
					resultVo.put(res, msgCode);
				} else if (res.equals("msgDescr")) {
					resultVo.put(res, getMessage);
				} else {
					netResponse.put(res, "null");
				}
			}
			netResponse.put("ResultVO", resultVo);
		

			return netResponse.toString();
		}

		
	

}
