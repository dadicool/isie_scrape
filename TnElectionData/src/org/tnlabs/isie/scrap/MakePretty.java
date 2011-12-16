package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MakePretty {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dataDir = "D:/git/isie_scrape/TnElectionData/tnacVotesRaw";
		if (args.length >= 1) {
			dataDir = args[0];
		}
		 File dir = new File(dataDir);
		 String[] files = dir.list( new SuffixFileFilter(".json") );
		 for (int i = 0; i < files.length; i++) {
		    try {
				String s=FileUtils.readFileToString(new File(dir , files[i]), "UTF-8");
				System.out.println(files[i]);
				String sPertty=makePretty(s);
				FileUtils.writeStringToFile(new File(dir , files[i]), sPertty, "UTF-8");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

	}
	
	
	private static String makePretty(String uglyJSONString) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJSONString);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}

}
