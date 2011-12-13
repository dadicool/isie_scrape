package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class LabelProvider {

	public String getCircLabel(String circonscription) {
		File folder=new File("D:/tnacData");//TODO make it an argument
		Map<String,String> map=loadFile(new File(folder,"raw.csv"));
		String result=map.get(circonscription);
		return result;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> loadFile(File file) {
		Map<String, String> map=new HashMap<String, String>();
		try {
			List<String> lines=(List<String>)FileUtils.readLines(file,"UTF-8");
			Iterator<String> iter=lines.iterator();
			while (iter.hasNext()){
				String line=iter.next();
				String[] parts=line.split(",");
				map.put(parts[1].trim(),parts[0].trim());
			}
			return map;			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getDelLabel(String circonscription,String delegation) {
		File folder=new File("D:/tnacData");//TODO make it an argument
		Map<String,String> map=loadFile(new File(folder,"raw-"+circonscription+".csv"));
		String result=map.get(delegation);
		return result;
	}

	public String getCenterLabel(String circonscription,String delegation,String center) {
		File folder=new File("D:/tnacData");//TODO make it an argument
		Map<String,String> map=loadFile(new File(folder,"raw-"+circonscription+"-"+delegation+".csv"));
		String result=map.get(center);
		return result;
	}

	public String getOfficeLabel(String circonscription,String delegation,String center,String bureau) {
		File folder=new File("D:/tnacData");//TODO make it an argument
		Map<String,String> map=loadFile(new File(folder,"raw-"+circonscription+"-"+delegation+"-"+center+".csv"));
		String result=map.get(bureau);
		return result;
	}

}
