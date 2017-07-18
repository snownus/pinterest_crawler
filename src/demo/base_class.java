package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class base_class {
	public String CRFtokens = "HZQYI9skKjeR4wOpd3c7zplB4SYVGEOD";
	protected int token_tag = 0;
	protected static Logger logger =  LogManager.getLogger(base_class.class);
	protected static List<String> crftokenArrayList = new ArrayList<String>();

	public base_class(int token_tag){
		this.token_tag = token_tag;
	}

	public void setToken(int token_tag){
		this.token_tag = token_tag;
	}
	
	public int getToken(){
		return this.token_tag;
	}

	public Map<String, String> Fetch_headers (){
		Map<String,String> header = new HashMap<String,String>();
		header.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:35.0) Gecko/20100101 Firefox/35.0");
		header.put("Content-Type","application/json; charset=utf-8");
		header.put("Connection", "close");
		header.put("X-CSRFToken", CRFtokens);
		header.put("X-NEW-APP", "1");
		header.put("X-Requested-With", "XMLHttpRequest");
		return header;
	}
}

