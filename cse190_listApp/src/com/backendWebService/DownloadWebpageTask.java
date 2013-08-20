package com.backendWebService;


import java.io.IOException;
import java.util.List;
import org.apache.http.NameValuePair;
import android.os.AsyncTask;

	public class DownloadWebpageTask extends AsyncTask<String, Void, String> {
	public AsyncResponse delegate=null;
	private List<NameValuePair> params;
	public void setParams(List<NameValuePair> params){
		this.params = params;
	}
	
	@Override
    protected String doInBackground(String... urls) {

          try {
			return DataRequest.postData(urls[0], params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          return "request failed";
    }
    // Posts the return string data to the activity calling it
    @Override
    protected void onPostExecute(String result) {
    	 delegate.processFinish(result);
    	
   }
}