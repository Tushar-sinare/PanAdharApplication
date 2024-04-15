package com.netwin.service;

import java.util.Map;

import com.netwin.entiry.PnRequest;
import com.netwin.entiry.Result;

public interface PnVndrRequestService {

	Result<String> fetchPnVndrRequest(PnRequest pnRequest2,Map<String, String> pnRequestDecrypt);


}
