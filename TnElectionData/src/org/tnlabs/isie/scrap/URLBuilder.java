package org.tnlabs.isie.scrap;
/**
 * 
 * 
 * @author Sami
 *
 */
public interface URLBuilder {
	public String getHost();
	
	public String getPath();
	
	public int getPort();
	
	public boolean isSecure();

}
