package com.example.myapplication.data.remote;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


public class ServiceParser {

    private String charset = "UTF-8";
    private HttpURLConnection conn;
    private DataOutputStream wr;
    private StringBuilder result;
    private URL urlObj;
    private StringBuilder sbParams;
    private String paramsString;

    private int timeOut = 30000;

    public String makeHttpRequest(String url, String method,
                                  HashMap<String, String> params, String dataString, String soapAction) {

        if (params != null) {
            sbParams = new StringBuilder();
            int i = 0;
            for (String key : params.keySet()) {
                try {
                    if (i != 0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=")
                            .append(URLEncoder.encode(params.get(key), charset));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;
            }
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
                conn.setRequestProperty("Content-Length", "" + dataString.length());
                conn.setRequestProperty("SOAPAction", soapAction);

                conn.setReadTimeout(timeOut);
                conn.setConnectTimeout(timeOut);

                conn.connect();

                if (sbParams == null) {
                    paramsString = dataString;
                } else {
                    paramsString = sbParams.toString();
                }

                Log.d("paramsString", paramsString);
                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("GET")) {
            // request method is GET

            if (sbParams != null && sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);
                conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

                conn.setConnectTimeout(timeOut);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Log.d("Status Code", "" + conn.getResponseCode());
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("ServiceParser", "result: " + result.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        conn.disconnect();

        return result.toString();
    }
}
