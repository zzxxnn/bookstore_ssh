package zxn.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class IdGenerator {
	public static String genPrimaryKey(){
		return UUID.randomUUID().toString();
	}
	public static String genOrdersNum(){
		Date noe = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String str = df.format(noe)+System.currentTimeMillis();
		return str;
	}
}
 