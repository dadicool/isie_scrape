package org.tnlabs.isie.scrap;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
/**
 * 
 * 
 * @author Sami
 *
 */
public class PageProcessor {
	URLBuilder urlBuilder;
	DataExtractor dataExtractor;

	public PageProcessor(URLBuilder urlBuilder,DataExtractor dataExtractor){
		this.urlBuilder=urlBuilder;
		this.dataExtractor=dataExtractor;
	}
	
	public SortedMap<String, String> getData(){
		WebPageLoader pageLoader = new WebPageLoader(urlBuilder.getHost(),urlBuilder.getPath(), urlBuilder.getPort(), urlBuilder.isSecure());
		String pageText = pageLoader.load(false,null);
		if (pageText != null && !pageText.isEmpty()) {
			pageText=dataExtractor.fixInput(pageText);
			return extractElements(pageText);
		}
		return null;

	}
	
	private  SortedMap<String, String>  extractElements(String pageText) {
		final HtmlCleaner cleaner = new HtmlCleaner();
		try {
			TagNode root = cleaner.clean(pageText);
			TagNode[] nodes=dataExtractor.extractOptions(root);
			if (nodes!=null && nodes.length>0){
				return extractOptions(nodes);
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private  SortedMap<String, String> extractOptions(TagNode[] options) {
		SortedMap<String, String> result=new TreeMap<String, String>();
		if (options!=null && options.length>0){
			for (TagNode o : options){
				String name=o.getText().toString();
				if ("option".equals(name))
					continue;
				String value=o.getAttributeByName("value");
				if (value==null || value.isEmpty())
					continue;
				System.out.println(name.trim()+"="+value);
				result.put(value,name.trim());
			}
		}
		return result;
	}


}
