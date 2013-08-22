package com.backendWebService;

import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.apache.http.NameValuePair;
import android.util.Log;

public class DataRequest {
	private static final String _debugTag = "HttpAccessExample";
	
	
		// This method sends and retrieves data from the webservers
		public static String postData(String myurl, List<NameValuePair> params) throws IOException {
		    InputStream is = null;
		    	        
		    try {
		        URL url = new URL(myurl);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setReadTimeout(10000 /* milliseconds */);
		        conn.setConnectTimeout(15000 /* milliseconds */);
		        conn.setRequestMethod("POST");
		        conn.setDoInput(true);
		        conn.setDoOutput(true);

		        //Creates the App output stream for sending to the server
		        OutputStream os = conn.getOutputStream();
		        BufferedWriter writer = new BufferedWriter(
		                new OutputStreamWriter(os, "UTF-8"));
		        writer.write(getQuery(params));
		        writer.close();
		        os.close();
		        
		        // Starts the query
		        conn.connect();
		        int response = conn.getResponseCode();
		        Log.d(_debugTag, "The response is: " + response);
		        is = conn.getInputStream();

		        // Convert the InputStream into a string
		        String contentAsString = readIt(is);
		        return contentAsString;
		        
		    // Makes sure that the InputStream is closed after the app is
		    // finished using it.
		    } finally {
		        if (is != null) {
		            is.close();
		        } 
		    }
		}
		
		// Reads an InputStream and converts it to a String.
		private static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
			BufferedReader in
			   = new BufferedReader(new InputStreamReader(stream));
			
		   return in.readLine();
		}
		// This method is used to encode List<NameValuePairs> parameters for use with POST method
		private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
		{
		    StringBuilder result = new StringBuilder();
		    boolean first = true;

		    for (NameValuePair pair : params)
		    {
		        if (first)
		            first = false;
		        else
		            result.append("&");

		        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
		        result.append("=");
		        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		    }

		    return result.toString();
		}
	
}
