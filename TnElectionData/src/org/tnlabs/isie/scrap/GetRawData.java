package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GetRawData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File folder=new File("D:/tnacData");//TODO make it an argument
		File[] files = folder.listFiles();
		for (int i=0;i<files.length;i++){
			try {
				handleFile(files[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void handleFile(File file) throws IOException {
		String[] parts=file.getName().split("-");
		if (parts!=null && parts.length==4){
			String c=parts[1];
			String d=parts[2];
			String v=parts[3].substring(0,3);
			List<String> l=(List<String>)FileUtils.readLines(file);
			Iterator<String> iter=l.iterator();
			while (iter.hasNext()){
				String line=iter.next();
				String[] nv=line.split(",");
				try {
					OfficeRawData rd = Scraper.getData(c, d, v, nv[1]);
					persist(file, rd, c, d, v, nv[1]);
				} catch (Exception e) {
					System.out.println(c+"-"+d+"-"+v+"-"+nv[1]);
					e.printStackTrace();
				}
			}
		}
		
	}

	private static void persist(File file, OfficeRawData rd,String c, String d, String v, String office) {
		try {
			File jsonFolder=new  File(file.getParent(),"json1");
			jsonFolder.mkdirs();
			String name=file.getName();
			name=name.substring(0,name.length()-4)+"-"+office+".json";
			String json = rd.toJson(c, d, v, office,new LabelProvider());
			FileUtils.writeStringToFile(new File(jsonFolder,name), json,"UTF-8");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
