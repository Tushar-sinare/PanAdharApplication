package com.netwin.service;

import com.netwin.entiry.PnRequest;
import com.netwin.entiry.PnVndrRequest;
import com.netwin.entiry.Result1;

public interface PnVndrResponseService {

	Result1 fetchPanApiResponse(PnVndrRequest pnVndrRequest2, PnRequest pnRequest2);

}
