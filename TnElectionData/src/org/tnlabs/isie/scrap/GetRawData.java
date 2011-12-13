package org.tnlabs.isie.scrap;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class GetRawData {
	static int countNoData=0;
	static int success=0;
	static File file=new File("output.txt");
	static File metadataFolder=null;
	static String outputText="";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String dataDir = "D:/tnacData";
		if (args.length >= 1) {
			dataDir = args[0];
		}
		metadataFolder=new File(dataDir);
		File[] files = metadataFolder.listFiles();
		for (int i=0;i<files.length;i++){
			try {
				handleFile(files[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			if(!outputText.isEmpty())
				FileUtils.writeStringToFile(file, outputText, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("finished with "+success+" scraped bureau and "+countNoData+" where data is missing from site");
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
					success++;
				} catch (Exception e) {
					LabelProvider lbp=new LabelProvider(metadataFolder);
					if (e instanceof NumberFormatException){
						countNoData++;
						outputAdd("------NO DATA FROM ISIE FOR---------------");
						outputAdd(c+"-"+d+"-"+v+"-"+nv[1]);
						outputAdd(lbp.getCircLabel(c));
						outputAdd(lbp.getDelLabel(c, d));
						outputAdd(lbp.getCenterLabel(c, d, v));
						outputAdd((lbp.getOfficeLabel(c, d, v, nv[1])));
						outputAdd("------------------------------------------");
					}
					else{
						System.out.println("------NO DATA FROM ISIE FOR---------------");
						System.out.println(c+"-"+d+"-"+v+"-"+nv[1]);
						System.out.println(lbp.getCircLabel(c));
						System.out.println(lbp.getDelLabel(c, d));
						System.out.println(lbp.getCenterLabel(c, d, v));
						System.out.println((lbp.getOfficeLabel(c, d, v, nv[1])));
						System.out.println("------------------------------------------");
					}
				}
			}
		}
	}

	private static void outputAdd(String string) {
		outputText+=string+System.getProperty("line.separator");
	}

	private static void persist(File file, OfficeRawData rd,String c, String d, String v, String office) {
		try {
			File jsonFolder=new  File(file.getParent(),"json1");
			jsonFolder.mkdirs();
			String name=file.getName();
			name=name.substring(0,name.length()-4)+"-"+office+".json";
			String json = rd.toJson(c, d, v, office,new LabelProvider(metadataFolder));
			FileUtils.writeStringToFile(new File(jsonFolder,name), json,"UTF-8");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
