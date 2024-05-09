package com.netwin.util;

import org.springframework.stereotype.Component;

@Component
public class QueryUtil {
public static final String NETWNFIELDQUERY = "SELECT NETWREQKEYNAME,NETWREQKEYREQ FROM netwreqkeymap WHERE NETWREQKEYPNADH = ? AND NETWREQFLD = ?";
public static final String PNVNDRFIELD ="SELECT a.NETWREQKEYNAME, a.VNDRREQKEYNAME FROM vndrreqkeymap a, netwreqkeymap b "
		+  "WHERE a.NETWREQKEYNAME = b.NETWREQKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRREQKEYPNADH= ? and a.VNDRREQKEYREQ= ? and VNDRREQKEYNAME IS NOT NUll";
public static final String NETWNWITHVNDRFIELDQUERY ="SELECT a.NETWREQKEYNAME, a.VNDRREQKEYNAME,a.VNDRREQKEYREQ FROM vndrreqkeymap a, netwreqkeymap b "
		+  "WHERE a.NETWREQKEYNAME = b.NETWREQKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRREQKEYPNADH= ? and NETWREQFLD= ?  and VNDRREQKEYNAME IS NOT NUll";

public static final String VNDRRESPFIELDQUERY="SELECT a.NETWRESKEYNAME, a.VNDRRESKEYNAME FROM vndrreskeymap a, netwreskeymap b "
		+  "WHERE a.NETWRESKEYNAME = b.NETWRESKEYNAME AND a.PNADHVNDRSRNO= ? and a.VNDRRESKEYPNADH= ? and VNDRRESKEYREQ =? and VNDRRESKEYNAME IS NOT NUll";
public static final String NETWRESPFIELDQUERY = "SELECT NETWRESKEYNAME FROM netwreskeymap WHERE VNDRRESKEYPNADH = ? AND VNDRRESKEYREQ = ? ";
public static final String ERRORS = "SELECT REJRESDESC FROM rejectres WHERE REJRESCD = ? ";

public static final String NETWNWITHVNDRRESPQUERY ="SELECT b.NETWRESKEYNAME, b.VNDRRESKEYNAME,b.VNDRRESKEYREQ FROM netwreskeymap b "
		+  "WHERE  b.PNADHVNDRSRNO= ? and b.VNDRRESKEYPNADH= ? and b.VNDRRESKEYREQ = ?";

public static final String VERIFYOTP = "SELECT a.REQSTR FROM ADHRESMAS a WHERE a.adhremassrno= ?";
public static final String NETWNFIELDQUERY1 = "SELECT VNDRREQKEYNAME,VNDRREQKEYREQ FROM vndrreqkeymap WHERE VNDRREQKEYPNADH = ? AND (VNDRREQFIELD !=? OR VNDRREQFIELD IS NULL OR VNDRREQFIELD = '')";
public static final String NETWNFIELDQUERY11 = "SELECT VNDRREQKEYNAME,VNDRREQKEYREQ FROM vndrreqkeymap WHERE VNDRREQKEYPNADH = ?";

private QueryUtil() {
	
	
}

}
