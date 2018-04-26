package zxn.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HQTT {

	public static void main(String[] args) {
		System.out.println(getWeather());
	}
	
	public static String  getWeather() {
		return hehe("http://www.weather.com.cn/weather1d/101180101.shtml"
				,"value="
				," />");
	}

	private static String  hehe(String url,String begin,String end) {
		String result = get(url, "utf-8");
		String res1 = mid(result, "tabDays", "<p");
		String res = mid(res1, begin, end);
		return res;
		
	}
	public static String get(String url, String charset) {
		try {
			URL __url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) __url.openConnection();
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = bis.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			bis.close();
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	public static String mid(String value, String left, String right) {
		try {
			int i = value.indexOf(left) + left.length();
			return value.substring(i, value.indexOf(right, i));
		} catch (Exception e) {
			return null;
		}
	}

}
