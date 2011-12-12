package org.tnlabs.isie.scrap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
/**
 * 
 * 
 * @author Sami
 *
 */
public class WebPageLoader {
	private String host;
	private String path;
	private int port;
	private boolean isSecure;

	public WebPageLoader(String host,String path, int port, boolean isSecure){
		this.host=host;
		this.path=path;
		this.port=port;
		this.isSecure=isSecure;
}
	
	public String load(boolean post, String postParams){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpHost target = new HttpHost(host, port, (isSecure)?"https":"http");
            HttpRequestBase req =(post)?new HttpPost(path): new HttpGet(path);
            req.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.11) Gecko/2009060215 Firefox/3.0.11 (.NET CLR 3.5.30729)");
            req.setHeader("Accept","text/html, image/gif, image/jpeg");
            req.setHeader("Accept-Language","en-us,en;q=0.5");
            req.setHeader("Accept-Language","en-us,en;q=0.5");
            req.setHeader("Accept-Charset","utf-8;iso-8859-1;q=0.7,*;q=0.5");
            req.setHeader("Keep-Alive","300");
            req.setHeader("Connection","keep-alive");
            if (post){
            	StringEntity reqEntity = new StringEntity(postParams);
                reqEntity.setContentType( "application/x-www-form-urlencoded");
                ((HttpPost)req).setEntity(reqEntity);
            }

         //   System.out.println("executing request to " + path+"?"+postParams);

            HttpResponse rsp = httpclient.execute(target, req);
            HttpEntity entity = rsp.getEntity();

//            System.out.println("----------------------------------------");
//            System.out.println(rsp.getStatusLine());
//            Header[] headers = rsp.getAllHeaders();
//            for (int i = 0; i < headers.length; i++) {
//                System.out.println(headers[i]);
//            }
//            System.out.println("----------------------------------------");

            if (entity != null) {
                return EntityUtils.toString(entity);
            }
        }
        catch(Throwable t){
        	t.printStackTrace();
        }
       finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return null;
	}
}
