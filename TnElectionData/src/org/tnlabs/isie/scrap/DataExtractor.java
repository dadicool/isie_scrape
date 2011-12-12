package org.tnlabs.isie.scrap;

import org.htmlcleaner.TagNode;
/**
 * 
 * 
 * @author Sami
 *
 */
public interface DataExtractor {
	
	public String fixInput(String input);

	public TagNode[] extractOptions(TagNode root);
	
}
