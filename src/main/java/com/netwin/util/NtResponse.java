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
@Autowired 
private JdbcTemplate jdbcTemplate;
@Autowired
private QueryUtil queryUtil;
	public Result1<Map<String,Object>> getNtResponse(int msgCode) {
		List<String> netwinResFieldResults1 = jdbcTemplate.queryForList(queryUtil.NETWINRESFIELDQUERY, String.class, "P","Y",
				 "V");
		String error = jdbcTemplate.queryForObject(queryUtil.ERRORRES, String.class, msgCode);

		Map<String,Object> netResponse = new HashMap<>();
		Map<String,Object> ResultVo = new HashMap<>();
		
		for(String res :netwinResFieldResults1) {
			if(res.equals("success")){
				ResultVo.put(res, "FALSE");
			}else if(res.equals("procRefUuid")) {
				ResultVo.put(res, "null");
			}else if(res.equals("error")) {
				ResultVo.put(res, "TRUE");
			}else if(res.equals("msgCode")){
				ResultVo.put(res, msgCode);
			}else if(res.equals("msgDescr")) {
				ResultVo.put(res, error);
			}else if(res.equals("ntmsgcode")) {
				ResultVo.put(res, "null");
			}else {
				netResponse.put(res, "null");
			}
		}
		netResponse.put("ResultVO",ResultVo);
		
		return new Result1<Map<String,Object>> (netResponse);
	}

}
