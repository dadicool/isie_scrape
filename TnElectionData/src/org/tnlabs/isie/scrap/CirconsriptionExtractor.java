package org.tnlabs.isie.scrap;

import org.htmlcleaner.TagNode;
/**
 * 
 * @author Sami
 *
 */
public class CirconsriptionExtractor implements DataExtractor {
	public static String s_circonsriptionSelectIdName = "id";
	public static String s_circonsriptionSelectIdValue = "circonscription";


	@Override
	public TagNode[] extractOptions(TagNode root) {
		TagNode selectNode = root
		.findElementByAttValue(s_circonsriptionSelectIdName,
				s_circonsriptionSelectIdValue,
				true/* recursive */, true/* isCaseSensitive */);
		return selectNode.getAllElements(false/*recursive*/);
	}

	@Override
	public String fixInput(String input) {
		return input;
	}

}
