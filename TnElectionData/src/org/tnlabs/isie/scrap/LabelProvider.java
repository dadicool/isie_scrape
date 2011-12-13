package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class LabelProvider {
	static Map<File, Map<String, String>> cache=new HashMap<File, Map<String,String>>();
	
	File metadataFolder;
	public LabelProvider(File metadataFolder){
		this.metadataFolder=metadataFolder;
	}
	
	public String getCircLabel(String circonscription) {
		Map<String,String> map=loadFile(new File(metadataFolder,"raw.csv"));
		String result=map.get(circonscription);
		return result;
	}

	public String getDelLabel(String circonscription,String delegation) {
		Map<String,String> map=loadFile(new File(metadataFolder,"raw-"+circonscription+".csv"));
		String result=map.get(delegation);
		return result;
	}

	public String getCenterLabel(String circonscription,String delegation,String center) {
		Map<String,String> map=loadFile(new File(metadataFolder,"raw-"+circonscription+"-"+delegation+".csv"));
		String result=map.get(center);
		return result;
	}

	public String getOfficeLabel(String circonscription,String delegation,String center,String bureau) {
		Map<String,String> map=loadFile(new File(metadataFolder,"raw-"+circonscription+"-"+delegation+"-"+center+".csv"));
		String result=map.get(bureau);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, String> loadFile(File file) {
		Map<String, String> map = cache.get(file);
		if (map==null){
			try {
				map=new HashMap<String, String>();
				List<String> lines=(List<String>)FileUtils.readLines(file,"UTF-8");
				Iterator<String> iter=lines.iterator();
				while (iter.hasNext()){
					String line=iter.next();
					String[] parts=line.split(",");
					map.put(parts[1].trim(),parts[0].trim());
				}
				cache.put(file,map);
				return map;			
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return map;
	}


}
