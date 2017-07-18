package demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EachSearch extends HttpContentRequest{
	public EachSearch(int token_tag) {
		super(token_tag);
	}
	
	String searchFeedResourceUrl = "https://www.pinterest.com/resource/SearchResource/get/?source_url={0}&data={1}";
	public String fetch_search_page(String searchQ, String bookmarks){
		JSONObject options = new JSONObject();
		options.put("query", searchQ);
		options.put("scope", "pins");
		if (!StringUtils.isEmpty(bookmarks)) {
			options.put("bookmarks", new String[]{bookmarks});
		}
		JSONObject data = new JSONObject();
		data.put("options", options);
		String dataEncode = "";
		String searchPage = "";
		String source_url = "/search/pins/?q=" + searchQ;
		try {
			dataEncode = URLEncoder.encode(data.toString(), "UTF-8");
			source_url = URLEncoder.encode(source_url.toString(), "UTF-8");
			String url = MessageFormat.format(searchFeedResourceUrl, source_url, dataEncode);
			Map<String, String> header = Fetch_headers();
			searchPage = newFetchContent(url, header);
		} catch (Exception e) {
			logger.error("search error. search Q:    " + e.getMessage());
		}
    	return searchPage;
	}
	
	public Map<String, String> executeEachSearch(String q, EachSearch eachSearch, int num_samples){
		String bookmarks = null; 
		String searchPage = ""; 
		Map<String, String> im_urls_id = new HashMap<String, String>();
		while (true) {
			try {
				searchPage = eachSearch.fetch_search_page(q, bookmarks);
				if (searchPage == null) {
					return im_urls_id;
				}
				bookmarks = nextBookmarks(searchPage);  //Bookmarks error. 
				JSONObject searchPageJson = JSONObject.fromObject(searchPage);
				JSONArray data = searchPageJson.getJSONObject("resource_response").getJSONArray("data");
				
				// parse to get image url. 
				for (Object o : data) {
					JSONObject rec = (JSONObject)o;
					JSONObject im = rec.getJSONObject("images");
					String im_url = im.getJSONObject("736x").getString("url");
					String pin_id = rec.getString("id");
					im_urls_id.put(pin_id, im_url);
					if (im_urls_id.size() > num_samples)
						return im_urls_id;
			    }
				if (bookmarks.equals("-end-")) { //search end. 
					break;
				}
			} catch (Exception e) {
				logger.error("ExcutorBoard.executeEachBoard---each Board Error :  " +  q + "    " + e);
				return im_urls_id;
			}
		}
		return im_urls_id;
	}
	
	public boolean getImages(Map<String, String> im_urls_id, String dest_folder){
		for(Map.Entry<String, String> each_entry: im_urls_id.entrySet() ){
			String id = each_entry.getKey();
			String im_url = each_entry.getValue();
			try{
			URL url = new URL(im_url);
			BufferedImage image = ImageIO.read(url);
			ImageIO.write(image, "jpg",new File(dest_folder + '/' + id + ".jpg"));
			}catch(Exception e){
			 System.out.println("error info" + e.getMessage());
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		String q = "women fashion";
		int numSamples = 10;
		String im_folder = "/home/gx/" + q;
		File folder = new File(im_folder);
		folder.mkdir();
		
		EachSearch eachSearch = new EachSearch(0);
		Map<String, String> im_urls_id = eachSearch.executeEachSearch(q, eachSearch, numSamples);
		System.out.println((im_urls_id.size()));
		
		eachSearch.getImages(im_urls_id, im_folder);
	}
}
