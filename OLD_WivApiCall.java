package biztrip.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.X509TrustManager;

import jex.log.JexLogFactory;
import jex.log.JexLogger;
import jex.sys.JexSystemConfig;

public class WivApiCall {

	private static final JexLogger LOG = JexLogFactory.getLogger(WivApiCall.class);
	
	String MAIN_URL		= (String)JexSystemConfig.get("WIV_API","apiHost");
//	String MAIN_URL		= "http://dev-api-channeling.withstatic.com/v1";
	String AUTH_KEY		= (String)JexSystemConfig.get("WIV_API","authToken");
	String USER_AGENT	= (String)JexSystemConfig.get("system_config","project");
	
	public static class ServerTrustManager implements X509TrustManager{
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException { }
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			arg0[0].checkValidity();
			arg0[0].getIssuerUniqueID();
			arg0[0].getSubjectDN();
		}
		public X509Certificate[] getAcceptedIssuers() { return null; }
	}
	
	// HTTP request
	public Map<String, String> sendWivApi(String targetUrl, String method, String parameters) throws Exception {
		Map<String, String> result = new HashMap();
		URL url = new URL(MAIN_URL + targetUrl);
		String logInfo = "";
		
		LOG.info("======== WivApiCall START ========");
		LOG.info("##### WivApiCall method       : " +method+ " || url: " +url);
		
		if(targetUrl.indexOf("create") > -1 || targetUrl.indexOf("cancel") > -1) {
			LOG.info("##### WivApiCall parameters   : " +parameters);
		}
		
//		SSLContext sslContext = SSLContext.getInstance("SSL");
//		ServerTrustManager serverTrustManager = new ServerTrustManager();
//		sslContext.init(null, new TrustManager[] { serverTrustManager }, new java.security.SecureRandom());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
//		con.setSSLSocketFactory(sslContext.getSocketFactory());
		con.setRequestMethod(method);
		con.setRequestProperty("Accept",		"*/*");
		con.setRequestProperty("Authorization", "Bearer "+AUTH_KEY);
//		con.setRequestProperty("Cache-Control", "no-cache");
//		con.setRequestProperty("Connection",	"keep-alive");
		
		if(method.equals("POST")) {
			con.setRequestProperty("Accept-Encoding",	"gzip");
			con.setRequestProperty("Content-Type",		"application/json");
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(parameters.getBytes("UTF-8"));
			os.flush();
			os.close();
		}
		
		int responseCode = con.getResponseCode();
		String responseMessage = (String)con.getResponseMessage();
		Map<String,List<String>> headerObject = con.getHeaderFields();
		
		if(targetUrl.indexOf("create") > -1 || targetUrl.indexOf("cancel") > -1) {
			LOG.info("##### WivApiCall responseCode   : " +responseCode);
			LOG.info("##### WivApiCall responseMessage: " +responseMessage);
			LOG.info("##### WivApiCall headerObject   : " +headerObject);
		}
		
		StringBuffer response = new StringBuffer();
		BufferedReader in = null;
		String inputLine;
		String cEncode	= con.getHeaderField("Content-Encoding");
		
		try{
			if (cEncode != null && ( cEncode.toLowerCase().indexOf("gzip") > -1 
									 || cEncode.toLowerCase().indexOf("deflate") > -1
									 || cEncode.toLowerCase().indexOf("compress") > -1)) {
				in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getInputStream())));
			} else {
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
		}catch(Exception e){
			if (cEncode != null && ( cEncode.toLowerCase().indexOf("gzip") > -1 
				 || cEncode.toLowerCase().indexOf("deflate") > -1
				 || cEncode.toLowerCase().indexOf("compress") > -1)){
				in = new BufferedReader(new InputStreamReader(new GZIPInputStream(con.getErrorStream())));
			}else{
				in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
		}
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		con.disconnect();
		
		if(targetUrl.indexOf("create") > -1 || targetUrl.indexOf("cancel") > -1) {
			LOG.info("##### WivApiCall result: "    +response.toString());
		}
		LOG.info("======== WivApiCall END ========");
		
		result.put("URL",         url.toString());
		result.put("RESULT_CODE", responseCode+"");
		result.put("RESULT_MSG",  response.toString());
		return result;
	}
}
