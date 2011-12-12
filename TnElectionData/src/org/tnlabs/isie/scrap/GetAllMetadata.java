package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
/**
 * 
 * @author Sami
 *
 */
public class GetAllMetadata {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			File folder=new File("D:/tnacData");//TODO make it an argument
			folder.mkdir();
			String filePrefix="raw";
			SortedMap<String, String> mapCirconscription=Scraper.getAllCirconscriptions();
			persist(mapCirconscription, new File(folder,filePrefix+".csv"));
			for (Iterator<String> iterCirconsriptions=mapCirconscription.keySet().iterator();iterCirconsriptions.hasNext();){
				String circonscription=iterCirconsriptions.next();
				SortedMap<String, String> mapDdelgation=Scraper.getAllDelegations(circonscription);
				persist(mapDdelgation, new File(folder,filePrefix+"-"+circonscription+".csv"));
				for (Iterator<String> iterDelegation=mapDdelgation.keySet().iterator();iterDelegation.hasNext();){
					String delegation=iterDelegation.next();
					persistAllVoteCentersAndOffices(folder,filePrefix,circonscription,delegation);
				}
			}
			
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void persistAllVoteCentersAndOffices(File folder, String filePrefix,String circonscription,String delegation) throws IOException {
		SortedMap<String, String> mapVoteCenter=Scraper.getAllVoteCenters(circonscription, delegation);
		persist(mapVoteCenter, new File(folder,filePrefix+"-"+circonscription+"-"+delegation+".csv"));
		for (Iterator<String> iterVoteCenters=mapVoteCenter.keySet().iterator();iterVoteCenters.hasNext();){
			String voteCenter=iterVoteCenters.next();
			SortedMap<String, String> mapOffices=Scraper.getAllVoteOffices(circonscription, delegation,voteCenter);
			persist(mapOffices, new File(folder,filePrefix+"-"+circonscription+"-"+delegation+"-"+voteCenter+".csv"));
			for (Iterator<String> iterOffices=mapOffices.keySet().iterator();iterOffices.hasNext();){
				String office=iterOffices.next();
				persistData(folder,filePrefix,circonscription,delegation,voteCenter,office);
			}
		}
	}

	private static void persistData(File folder, String filePrefix,
			String circonscription, String delegation, String voteCenter,
			String office) {
		
	}

	private static void persist(SortedMap<String, String> input, File path) throws IOException{
		String output="";
		Iterator<Entry<String, String>> iter= input.entrySet().iterator();
		while (iter.hasNext()){
			Entry<String, String> e=iter.next();
			output+=e.getValue()+","+e.getKey()+System.getProperty("line.separator");
		}
		FileUtils.writeStringToFile(path, output, "UTF-8");
	}
}
