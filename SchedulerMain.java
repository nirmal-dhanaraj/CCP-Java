
import java.io.IOException;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.net.URLEncoder;


public class SchedulerMain {
	private static final String UTF_ENCODE = "UTF-8";
	public static void main(String args[])
			throws InterruptedException, ClientProtocolException, IOException,Exception {
		HttpClient client = new DefaultHttpClient();
		try
		{
		  String appID=URLEncoder.encode("Backup-APP",UTF_ENCODE);
		  String safe=URLEncoder.encode("Backup",UTF_ENCODE); 	
		  String account=URLEncoder.encode("svc_backup",UTF_ENCODE);
	      String queryURL="https://compsrv01.corpad.loc/AIMWebService/api/Accounts?AppID="+
				  			appID+"&Safe="+safe+"&UserName="+account;
	      HttpGet getRequest = new HttpGet(queryURL);
	      System.out.println("Executing request to " + getRequest.getURI());
	      client = WebClientDevWrapper.wrapClient(client);
	      System.out.println("----------------------------------------"); 
	      HttpResponse httpResponse = client.execute(getRequest);
	      System.out.println("----------------------------------------");
	      HttpEntity entity = httpResponse.getEntity();
		  System.out.println(httpResponse.getStatusLine());
	      Header[] headers = httpResponse.getAllHeaders();
	      	for (int i = 0; i < headers.length; i++)
	        	System.out.println(headers[i]);
	      	System.out.println("----------------------------------------");
	      	if (entity != null)
	          System.out.println(EntityUtils.toString(entity));
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	    	client.getConnectionManager().shutdown();
	    }
	}
}