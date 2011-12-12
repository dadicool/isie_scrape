package org.tnlabs.isie.scrap;
/**
 * 
 * 
 * @author Sami
 *
 */
public  class BaseISIEUrlBuilder implements URLBuilder {
	String path;
	
	public BaseISIEUrlBuilder(String path){
		this.path=path;
	}
	
	@Override
	public String getHost() {
		return  "www.isie.tn";
	}

	@Override
	public  String getPath(){
		return path;
	}

	@Override
	public int getPort() {
		return 80;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

}
