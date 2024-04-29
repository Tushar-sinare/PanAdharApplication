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

		public String getNtResponse(int msgCode) {
			List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(QueryUtil.NETWRESPFIELDQUERY, String.class,
					"AV", "Y");
			
			String error = jdbcTemplate.queryForObject(QueryUtil.ERRORS, String.class, msgCode);

			Map<String, Object> netResponse = new HashMap<>();

			for (String res : netwinResFieldResults1) {
				 if (res.equals("status_code")) {
					 netResponse.put(res, msgCode);
				} else if (res.equals("message")) {
					netResponse.put(res, error);
				}else if (res.equals("success")){
					netResponse.put(res, "0");
				}else {
					netResponse.put(res, "null");
				}
			}
			
			

			return netResponse.toString();
		}

	}

	

