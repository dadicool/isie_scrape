package org.tnlabs.isie.scrap;

import java.util.SortedMap;
/**
 * 
 * 
 * @author Sami
 *
 */
public class Scraper {
	public static String s_circonscritionPath = "/Ar/rsfinal.php";
	public static String s_DelegationPage = "/Ar/ws_delegation_ajax.php?circonscription=";
	public static String s_VoteCenterPage = "/Ar/ws_centre_vote_ajax.php?circonscription=deadcmeat&delegation=deaddmeat";
	public static String s_OfficePage = "/Ar/ws_bureau_vote_ajax.php?centre_vote=deadvmeat&circonscription=deadcmeat&delegation=deaddmeat";
	//public static String sRawDataPage ="/Ar/rsfinal.php?bureau_vote=01&centre_vote=001&delegation=52&circonscription=111&button=+++%D8%A8%D8%AD%D8%AB++";
	public static String sRawDataPage ="/Ar/rsfinal.php";
	public static String sRawDataParams="bureau_vote=deadbmeat&button=%20%20%20%D8%A8%D8%AD%D8%AB%20%20&centre_vote=deadvmeat&circonscription=deadcmeat&delegation=deaddmeat";
	public static SortedMap<String, String> getAllCirconscriptions(){
		BaseISIEUrlBuilder bUrl=new BaseISIEUrlBuilder(s_circonscritionPath);
		PageProcessor pp=new PageProcessor(bUrl,new CirconsriptionExtractor());
		return pp.getData();
	}
	
	public static SortedMap<String, String> getAllDelegations(String circonscription){
		BaseISIEUrlBuilder bUrl=new BaseISIEUrlBuilder(s_DelegationPage+circonscription);
		PageProcessor pp=new PageProcessor(bUrl,new GenericBaseExtractor());
		return pp.getData();
	}
	
	public static SortedMap<String, String> getAllVoteCenters(String circonscription,String delegation){
		String path=s_VoteCenterPage;
		path=path.replaceFirst("deadcmeat", circonscription);
		path=path.replaceFirst("deaddmeat", delegation);
		BaseISIEUrlBuilder bUrl=new BaseISIEUrlBuilder(path);
		PageProcessor pp=new PageProcessor(bUrl,new GenericBaseExtractor());
		return pp.getData();
	}
	
	public static SortedMap<String, String> getAllVoteOffices(String circonscription,String delegation,String voteCenter){
		String path=s_OfficePage;
		path=path.replaceFirst("deadcmeat", circonscription);
		path=path.replaceFirst("deaddmeat", delegation);
		path=path.replaceFirst("deadvmeat", voteCenter);
		BaseISIEUrlBuilder bUrl=new BaseISIEUrlBuilder(path);
		PageProcessor pp=new PageProcessor(bUrl,new GenericBaseExtractor());
		return pp.getData();
	}
	
	public static OfficeRawData getData(String circonscription,String delegation,String voteCenter,String voteOffice){
		String params=sRawDataParams;
		params=params.replaceFirst("deadcmeat", circonscription);
		params=params.replaceFirst("deaddmeat", delegation);
		params=params.replaceFirst("deadvmeat", voteCenter);
		params=params.replaceFirst("deadbmeat", voteOffice);
		BaseISIEUrlBuilder bUrl=new BaseISIEUrlBuilder(sRawDataPage);
		WebPageLoader pageLoader = new WebPageLoader(bUrl.getHost(),bUrl.getPath(), bUrl.getPort(), bUrl.isSecure());
		String pageText = pageLoader.load(true,params);
		return new RawDataScraper(pageText).getData();
	}

}
