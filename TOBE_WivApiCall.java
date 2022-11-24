package biztrip.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.X509TrustManager;

import jex.log.JexLogFactory;
import jex.log.JexLogger;
import jex.sys.JexSystemConfig;

public class WivApiCall {

	public static final JexLogger LOG = JexLogFactory.getLogger(WivApiCall.class);
	public static final String UNDEFINE_ERR_CODE = "9999";
	public static final String UNDEFINE_ERR_MSG  = "처리 중 오류가 발생하였습니다. 잠시 후 이용하시기 바랍니다.";
	public static final String PRINT_SHARP       = "################################## ";
	
//	public static final String MAIN_URL   = "http://dev-api-channeling.withstatic.com/v1";
	public static final String MAIN_URL   = (String)JexSystemConfig.get("WIV_API","apiHost");
	public static final String AUTH_KEY   = (String)JexSystemConfig.get("WIV_API","authToken");
	public static final String USER_AGENT = (String)JexSystemConfig.get("system_config","project");
	
	
	public static class ServerTrustManager implements X509TrustManager{
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			arg0[0].checkValidity();
			arg0[0].getIssuerUniqueID();
			arg0[0].getSubjectDN();
		}
		public X509Certificate[] getAcceptedIssuers() { return null; }
	}
	
	/**
	 * 여기어때 Api 호출 공통
	 * @param urlPath
	 * @param method
	 * @param parametersObj
	 * @return result 응답 오브젝트
	 */
	public Map<String, String> callApi(String urlPath, String method, String parametersObj) {
		Map<String,String> result = new HashMap();
		HttpURLConnection conn   = null;
		HttpsURLConnection conns = null;
		OutputStream os          = null;
		BufferedReader in        = null;
		String responseCode      = "";
		String responseMsg       = "";
		
		try {
			String fullURL = (urlPath == null ? MAIN_URL : MAIN_URL + urlPath);
			parametersObj  = URLEncoder.encode(parametersObj, "UTF-8");
			
			LOG.info((new StringBuilder("======== WivApiCall START ========\n")
					.append(PRINT_SHARP+ "[REQUEST_URL: " +fullURL+      "]\n")
					.append(PRINT_SHARP+ "[METHOD     : " +method+       "]")
					));
			
			if(urlPath.indexOf("create") > -1 || urlPath.indexOf("cancel") > -1) {
				LOG.info("[PARAMETER  : " +parametersObj+ "]");
			}
			
			if(MAIN_URL.startsWith("https://")) {
				URL connUrl = new URL(fullURL);
				conns       = (HttpsURLConnection)connUrl.openConnection();
				
//				SSLContext sslContext = SSLContext.getInstance("SSL");
//				ServerTrustManager serverTrustManager = new ServerTrustManager();
//				sslContext.init(null, new TrustManager[] { serverTrustManager }, new java.security.SecureRandom());
//				conns.setSSLSocketFactory(sslContext.getSocketFactory());
				
				conns.setRequestMethod(method);
				conns.setRequestProperty("Accept"          , "*/*");
				conns.setRequestProperty("Authorization"   , "Bearer "+AUTH_KEY);
				conns.setRequestProperty("Accept-Encoding" , "gzip");
				conns.setRequestProperty("Content-Type"    , "application/json");
				conns.setDoOutput(true);
				
				if("POST".equals(method)) {
					os = conns.getOutputStream();
					os.write(parametersObj.getBytes("UTF-8"));
					os.flush();
				}
				
				responseCode = conns.getResponseCode() + "";
				String responseMessage = (String)conns.getResponseMessage();
				Map<String,List<String>> headerObject = conns.getHeaderFields();
				
				LOG.info("[RESPONSE_CODE  : " +responseCode+       "]");
				if(urlPath.indexOf("create") > -1 || urlPath.indexOf("cancel") > -1) {
					LOG.info((new StringBuilder( "[RESPONSE_MSG   : " +responseMessage+ "]\n")
							.append(PRINT_SHARP+ "[RESPONSE_HEADER: " +headerObject+    "]")
							));
				}
				
				String cEncode   = conns.getHeaderField("Content-Encoding");
				String inputLine = "";
				
				if (cEncode != null && ( cEncode.toLowerCase().indexOf("gzip") > -1 
						 || cEncode.toLowerCase().indexOf("deflate") > -1
						 || cEncode.toLowerCase().indexOf("compress") > -1)) {
					in = new BufferedReader(new InputStreamReader(new GZIPInputStream(conns.getInputStream())));
				} else {
					in = new BufferedReader(new InputStreamReader(conns.getInputStream()));
				}
				
				while ((inputLine = in.readLine()) != null) {
					responseMsg += inputLine;
				}
				
			} else if(MAIN_URL.startsWith("http://")) {
				URL connUrl = new URL(fullURL);
				conn        = (HttpURLConnection)connUrl.openConnection();
				
//				SSLContext sslContext = SSLContext.getInstance("SSL");
//				ServerTrustManager serverTrustManager = new ServerTrustManager();
//				sslContext.init(null, new TrustManager[] { serverTrustManager }, new java.security.SecureRandom());
//				conn.setSSLSocketFactory(sslContext.getSocketFactory());
				
				conn.setRequestMethod(method);
				conn.setRequestProperty("Accept"          , "*/*");
				conn.setRequestProperty("Authorization"   , "Bearer "+AUTH_KEY);
				conn.setRequestProperty("Accept-Encoding" , "gzip");
				conn.setRequestProperty("Content-Type"    , "application/json");
				conn.setDoOutput(true);
				
				if("POST".equals(method)) {
					os = conn.getOutputStream();
					os.write(parametersObj.getBytes("UTF-8"));
					os.flush();
				}
				
				responseCode = conn.getResponseCode() + "";
				String responseMessage = (String)conn.getResponseMessage();
				Map<String,List<String>> headerObject = conn.getHeaderFields();
				
				LOG.info("[RESPONSE_CODE  : " +responseCode+       "]");
				if(urlPath.indexOf("create") > -1 || urlPath.indexOf("cancel") > -1) {
					LOG.info((new StringBuilder( "[RESPONSE_MSG   : " +responseMessage+ "]\n")
							.append(PRINT_SHARP+ "[RESPONSE_HEADER: " +headerObject+    "]")
							));
				}
				
				String cEncode   = conn.getHeaderField("Content-Encoding");
				String inputLine = "";
				
				if (cEncode != null && ( cEncode.toLowerCase().indexOf("gzip") > -1 
					 || cEncode.toLowerCase().indexOf("deflate") > -1
					 || cEncode.toLowerCase().indexOf("compress") > -1)){
					in = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getErrorStream())));
				}else{
					in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				
				while ((inputLine = in.readLine()) != null) {
					responseMsg += inputLine;
				}
			}
			
			result.put("URL", fullURL);
		} catch (Exception err) {
			responseCode = UNDEFINE_ERR_CODE;
			responseMsg  = UNDEFINE_ERR_MSG;
			err.printStackTrace();
		} finally {
			if(os != null) {
				try { os.close(); }catch (Exception e) { e.printStackTrace(); }
			}
			if(in != null) {
				try { in.close(); }catch (Exception e) { e.printStackTrace(); }
			}
			if(conn != null) {
				conn.disconnect();
			}
			if(conns != null) {
				conns.disconnect();
			}
		}
		
		result.put("RESULT_CODE", responseCode);
		result.put("RESULT_MSG" , responseMsg);
		
		LOG.info((new StringBuilder("[RESPONSE DATA: " +result+ "]")
				.append("")
				));
		LOG.info("======== WivApiCall END ========");
		
		return result;
	}
}
