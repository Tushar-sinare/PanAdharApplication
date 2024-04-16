package com.netwin.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.netwin.entiry.Result1;

@Component
public class NtResponse {

	private final JdbcTemplate jdbcTemplate;

	private final QueryUtil queryUtil;

	@Autowired
	public NtResponse(JdbcTemplate jdbcTemplate, QueryUtil queryUtil) {
		this.jdbcTemplate = jdbcTemplate;
		this.queryUtil = queryUtil;
	}

	public Result1<Map<String, Object>> getNtResponse(int msgCode) {
		List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(queryUtil.netwnResFieldQuery, String.class,
				"P", "Y", "V");
		String error = jdbcTemplate.queryForObject(queryUtil.errors, String.class, msgCode);

		Map<String, Object> netResponse = new HashMap<>();
		Map<String, Object> resultVo = new HashMap<>();

		for (String res : netwinResFieldResults1) {
			if (res.equals("success")) {
				resultVo.put(res, "FALSE");
			} else if (res.equals("procRefUuid")) {
				resultVo.put(res, "null");
			} else if (res.equals("error")) {
				resultVo.put(res, "TRUE");
			} else if (res.equals("msgCode")) {
				resultVo.put(res, msgCode);
			} else if (res.equals("msgDescr")) {
				resultVo.put(res, error);
			} else if (res.equals("ntmsgcode")) {
				resultVo.put(res, "null");
			} else {
				netResponse.put(res, "null");
			}
		}
		netResponse.put("ResultVO", resultVo);

		return new Result1<Map<String, Object>>(netResponse);
	}

}
