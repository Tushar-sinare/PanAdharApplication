package com.netwin.util;

import org.springframework.stereotype.Component;

@Component
public class QueryUtil {
public static final String netwnFieldQuery = "SELECT NETWREQKEYNAME,NETWREQKEYREQ FROM netwreqkeymap WHERE NETWREQKEYPNADH = ? AND NETWREQFLD = ?";
public static final String pnVndrField ="SELECT a.NETWREQKEYNAME, a.VNDRREQKEYNAME FROM vndrreqkeymap a, netwreqkeymap b "
		+  "WHERE a.NETWREQKEYNAME = b.NETWREQKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRREQKEYPNADH= ? and a.VNDRREQKEYREQ= ? and VNDRREQKEYNAME IS NOT NUll";
public static final String netwnWithVndrFieldQuery ="SELECT a.NETWREQKEYNAME, a.VNDRREQKEYNAME,a.VNDRREQKEYREQ FROM vndrreqkeymap a, netwreqkeymap b "
		+  "WHERE a.NETWREQKEYNAME = b.NETWREQKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRREQKEYPNADH= ?  and VNDRREQKEYNAME IS NOT NUll";

public static final String vndrRespFieldQuery="SELECT a.NETWRESKEYNAME, a.VNDRRESKEYNAME FROM vndrreskeymap a, netwreskeymap b "
		+  "WHERE a.NETWRESKEYNAME = b.NETWRESKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRRESKEYPNADH= ? and VNDRRESKEYREQ =? and VNDRRESKEYNAME IS NOT NUll";
public static String netwnResFieldQuery = "SELECT NETWRESKEYNAME FROM netwreskeymap WHERE NETWRESKEYPNADH = ? AND NETWRESKEYREQ = ? AND NETWRESFLD = ?";
public static String errors = "SELECT REJRESDESC FROM rejectres WHERE REJRESCD = ? ";

//final String CUSTDETAILS = "SELECT * FROM netwcustmas where NETWCUSTSRNO=?";
//final String VENDORDETAILS = "SELECT * FROM pnvendormas where PNVNDRSRNO=?";
}
