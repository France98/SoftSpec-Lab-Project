package com.example.france98.finalproject.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPDataHandler {
    static String stream = "";

    public HTTPDataHandler() {
    }

    public String getHTTPDataHandler(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream input = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(input));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            }

        } catch (Exception ex) {
            return null;
        }
        return stream;
    }
}
