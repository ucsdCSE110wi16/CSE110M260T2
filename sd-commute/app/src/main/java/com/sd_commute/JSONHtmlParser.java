package com.sd_commute;

import android.util.Log;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
 * http://stackoverflow.com/questions/13196234/simple-parse-json-from-url-on-android-and-display-in-listview
 */
public class JSONHtmlParser
{

    private String formatJSON(StringBuilder sb)
    {
        if (sb.charAt(0) == '[')
        {
            sb.deleteCharAt(0);
            sb.insert(0, '{');
        }
        if (sb.charAt(sb.length()-2) == ']')
        {
            sb.insert(sb.length()-2, '}');
            sb.deleteCharAt(sb.length()-2);
        }
        if (sb.charAt(1) == '[')
        {
            sb.deleteCharAt(1);
        }
        if (sb.charAt(sb.length()-3) == ']')
        {
            sb.deleteCharAt(sb.length()-3);
        }

        int number = 1;
        for (int i = 1; i < sb.length()-2; i++)
        {
            if (sb.charAt(i) == '{' && sb.charAt(i-1) != ':')
            {
                String prefix = new Integer(number++).toString();
                sb.insert(i, "\"" + prefix + "\":");
                i+=4;
            }
        }
        return sb.toString();
    }

    public JSONHtmlParser()
    {}

    public String getJSON(String url)
    {
        int timeout = 20000;
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    return formatJSON(sb);
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}