package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpContentRequest extends base_class {
	private static Integer readTimeOut = Constants.readTimeOut;
	private static Integer connectionTimeOut = Constants.connectionTimeOut;
	private static Integer maxConnectTimes = Constants.maxConnectionTime;

	public HttpContentRequest(int token_tag) {
		super(token_tag);
	}

	private HttpClient httpClient = null; 
	protected static Logger logger = LogManager.getLogger(HttpContentRequest.class); 

	public String fetchContent(String url){
		return newFetchContent(url,null);
	}

	// Load proxy from a specific file 
	public Map<String, Integer> load_proxy(String Proxy_file) throws NumberFormatException, IOException{
		Map<String, Integer> proxyhashHashMap = new HashMap<String, Integer>();
		BufferedReader br = null;
		String line = "";
		String[] proxy_info;
		br = new BufferedReader(new FileReader(Proxy_file));
		while ((line = br.readLine()) != null) {
			proxy_info = line.split(":");
			proxyhashHashMap.put(proxy_info[0], Integer.parseInt(proxy_info[1]));
		}
		br.close();
		Map<String, Integer> treeMap = new TreeMap<String, Integer>(proxyhashHashMap);
		return treeMap;
	}

	//Given url and headers to fetch the content. 
	public String fetchContent(String url, Map<String,String> header){
		httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
		GetMethod get = new GetMethod(url);
		try {
			if(header != null){
				Iterator<String> iterator = header.keySet().iterator();
				while(iterator.hasNext()){
					String k = iterator.next();
					String v = header.get(k);
					get.addRequestHeader(k,v);
				}
			}

			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(100000);  
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);
			httpClient.executeMethod(get);

			BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), "utf-8"));  
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = reader.readLine())!=null){  
				stringBuffer.append(str);  
			}  
			reader.close();
			if (stringBuffer.toString().contains("html") && !stringBuffer.toString().startsWith("{")) {
				//					System.out.println("tokenTags:  " + token_tags + "   CRFtokens:   " + CRFtokens);
				//					System.out.println("Html Error");
				logger.info("--------------------------Html Error------------------------"); 
				Thread.sleep(30000);
				return "1";
			}
			return stringBuffer.toString();
		}
		catch (IOException e){ // IO exception. 
			logger.info("Request Pages Error :  " + e.getMessage());
			return "1";
		}
		catch (Exception e) {
			logger.info( "Please check your provided http address:   " + url + "     " + e.getMessage());
			return "1";
		}  finally  {
			try {
				this.finalize();
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				get.releaseConnection();
			} catch (Throwable e) {
				System.out.println(e.getMessage());
			}
		}
	}

	//Given url and headers to fetch the content. 
	public String newFetchContent(String url, Map<String,String> header){
		int n = maxConnectTimes;  
		while (n > 0) {  
			httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
			GetMethod get = new GetMethod(url);
			try {
				if(header != null){
					Iterator<String> iterator = header.keySet().iterator();
					while(iterator.hasNext()){
						String k = iterator.next();
						String v = header.get(k);
						get.addRequestHeader(k,v);
					}
				}
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);  
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeOut);
				httpClient.executeMethod(get);

				BufferedReader reader = new BufferedReader(new InputStreamReader(get.getResponseBodyAsStream(), "utf-8"));  
				StringBuffer stringBuffer = new StringBuffer();  
				String str = "";  
				while((str = reader.readLine())!=null){  
					stringBuffer.append(str);  
				}  
				reader.close();
				if (!stringBuffer.toString().startsWith("{")) {
					n--;
				}else{
					if (n != maxConnectTimes){
						//						System.out.println("successful: @" + n + " " + url);
					}
					return stringBuffer.toString();
				}
			}catch (Exception e) {
				//				logger.info( "Can't connect " + url + " in "  + (maxConnectTimes - n + 1) + " error ms: " + e + " token: " + CRFtokens);
				n--;
			}  
			finally  {
				try {
					this.finalize();
					((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				} catch (Throwable e) {
					System.out.println(e.getMessage());
				}
			}
		}
		logger.info("can't connect url: " + url + " in " + maxConnectTimes + " times");
		return null;
	}

	/*
	 * Get book mark and base url. Jsoup can only parse html 
	 */
	public String nextBookmarks(String content){
		String bookmark  = "";
		JSONObject jo = JSONObject.fromObject(content);
		bookmark = jo.getJSONObject("resource").getJSONObject("options").getJSONArray("bookmarks").getString(0);
		return bookmark;
	}

	/*
	 *  Given a source url, the page content html can be given. 
	 */
	public String crawl_url(String source_url) throws HttpException, IOException{
		HttpClient httpClient = new HttpClient(new SimpleHttpConnectionManager(true));
		GetMethod get = new GetMethod(source_url);
		httpClient.executeMethod(get);
		String content = get.getResponseBodyAsString();
		return content;
	}

	//Based on the regex, find the String for matching. 
	public String getMatchPattern(String regex,String input,int groupIndex){
		Pattern pat = Pattern.compile(regex);
		Matcher matcher = pat.matcher(input);
		if(matcher.find()){
			return matcher.group(groupIndex);
		}
		return "";
	}

}
