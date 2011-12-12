package org.tnlabs.isie.scrap;

import java.io.IOException;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.tnlabs.isie.scrap.OfficeRawData.CandidateList;
/**
 * 
 * 
 * @author Sami
 *
 */
public class RawDataScraper {
	String html;
	public RawDataScraper(String pagetText){
		html= pagetText;
	}

	public OfficeRawData getData() {
		final HtmlCleaner cleaner = new HtmlCleaner();
		try {
			TagNode root = cleaner.clean(html);
			TagNode[] nodes=getNodes(root);
			if (nodes!=null && nodes.length>0){
				return extractData(nodes);
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private TagNode[] getNodes(TagNode root) {
		TagNode outerDiv=root.findElementByAttValue("class", "rsvote", true, true);
		TagNode[] children=outerDiv.getAllElements(false);
		TagNode innderDiv=children[1].getChildTags()[0].getChildTags()[0].getChildTags()[0].getChildTags()[0];
		TagNode[] childrenInnerDiv=innderDiv.getAllElements(false);
		
		return new TagNode[]{childrenInnerDiv[3],childrenInnerDiv[5],childrenInnerDiv[7]};
	}

	private OfficeRawData extractData(TagNode[] nodes) {
		if (nodes!=null && nodes.length==3){
			OfficeRawData rawData=new OfficeRawData();
			addFirstTableData(nodes[0],rawData);
			addSecondTableData(nodes[1],rawData);
			addThirdTableData(nodes[2],rawData);
			return rawData;
	}
		return null;
	}


	private void addFirstTableData(TagNode tagNode, OfficeRawData rawData) {
		TagNode row=getRow(tagNode,0);
		int nbRegistred=Integer.parseInt(getCell(row,0).findElementByName("strong", false).getText().toString().trim());
		int nbVoters=Integer.parseInt(getCell(row,2).findElementByName("strong", false).getText().toString().trim());
		rawData.setNbRegistred(nbRegistred);
		rawData.setNbVoters(nbVoters);
	}

	private void addSecondTableData(TagNode tagNode, OfficeRawData rawData) {
		TagNode row=getRow(tagNode,0);
		int nbUsedPaper=Integer.parseInt(getCell(row,0).findElementByName("strong", false).getText().toString().trim());
		int nbDelivredPaper=Integer.parseInt(getCell(row,2).findElementByName("strong", false).getText().toString().trim());
		row=getRow(tagNode,1);
		int nbWhiteVotes=Integer.parseInt(getCell(row,0).findElementByName("strong", false).getText().toString().trim());
		int nbUnusedPaper=Integer.parseInt(getCell(row,2).findElementByName("strong", false).getText().toString().trim());
		row=getRow(tagNode,2);
		int nbCanceledVotes=Integer.parseInt(getCell(row,0).findElementByName("strong", false).getText().toString().trim());
		int nbLostPaper=Integer.parseInt(getCell(row,2).findElementByName("strong", false).getText().toString().trim());
		row=getRow(tagNode,3);
		int nbCountedVotes=Integer.parseInt(getCell(row,0).findElementByName("strong", false).getText().toString().trim());
		rawData.setNbUsedPaper(nbUsedPaper);
		rawData.setNbDelivredPaper(nbDelivredPaper);
		rawData.setNbWhiteVotes(nbWhiteVotes);
		rawData.setNbUnusedPaper(nbUnusedPaper);
		rawData.setNbCanceledVotes(nbCanceledVotes);
		rawData.setNbLostPaper(nbLostPaper);
		rawData.setNbCountedVotes(nbCountedVotes);
	}

	private void addThirdTableData(TagNode tagNode, OfficeRawData rawData) {
		TagNode tbody=tagNode.findElementByName("tbody", true);
		TagNode[] rows = tbody.getElementsByName("tr", false);
		for(int i=0;i<rows.length;i++){
			TagNode row=rows[i];
			addtoCandidateList(rawData,row);
		}
	}



	private void addtoCandidateList(OfficeRawData rawData, TagNode row) {
		TagNode[] cells = row.getElementsByName("td", false);
		addList(cells[0],cells[1],cells[2],cells[3],rawData);
		addList(cells[5],cells[6],cells[7],cells[8],rawData);
	}

	private void addList(TagNode tagNode, TagNode tagNode2, TagNode tagNode3,
			TagNode tagNode4, OfficeRawData rawData) {
		if (tagNode.getText().toString().trim().isEmpty())
			return;
		float percent=Float.parseFloat(tagNode.findElementByName("strong", false).getText().toString().trim());
		int nVotes=Integer.parseInt(tagNode2.findElementByName("strong", false).getText().toString().trim());
		String name = tagNode3.findElementByName("strong", false).getText().toString().trim();
		int id =Integer.parseInt( tagNode4.findElementByName("strong", false).getText().toString().trim());
		CandidateList list=new CandidateList(id,name,nVotes,percent);
		rawData.addList(list);
	}

	private TagNode getRow(TagNode tableNode,int index) {
		TagNode tbody=tableNode.findElementByName("tbody", true);
		return tbody.getElementsByName("tr", false)[index];
	}

	private TagNode getCell(TagNode trNode,int index) {
		return trNode.getElementsByName("td", false)[index];
	}

}
