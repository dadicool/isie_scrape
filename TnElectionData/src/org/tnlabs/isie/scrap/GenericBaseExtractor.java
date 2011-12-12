package org.tnlabs.isie.scrap;

import org.htmlcleaner.TagNode;
/**
 * 
 * 
 * @author Sami
 *
 */
public class GenericBaseExtractor implements DataExtractor {

	@Override
	public TagNode[] extractOptions(TagNode root) {
		return  root.getAllElements(true);
	}

	@Override
	public String fixInput(String input) {
		return "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head>"+
		"<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\"></head><body><form><select>"
		+input+"</select></form></body></html>";
	}

}
