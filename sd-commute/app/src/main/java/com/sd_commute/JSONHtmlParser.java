package com.sd_commute;

import android.util.Log;
import org.json.JSONObject;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
 * http://stackoverflow.com/questions/13196234/simple-parse-json-from-url-on-android-and-display-in-listview
 */
public class JSONHtmlParser
{

    private String formatJSON(StringBuilder sb)
    {
        for (int i = 0; i < sb.length(); i++)
        {
            if (sb.charAt(i) == '[')
            {
                sb.deleteCharAt(i);
                sb.insert(i, '{');
            }
            else if (sb.charAt(i) == ']')
            {
                sb.deleteCharAt(i);
                sb.insert(i, '}');
            }
        }

        int index = 0;
        int[] numbers = new int[64];
        boolean dec = false;
        for (int i = 0; i < 64; i ++)
        {
            numbers[i] = 1;
        }
        for (int i = 1; i < sb.length()-2; i++)
        {
            if (sb.charAt(i) == '{' && sb.charAt(i-1) != ':')
            {
                String prefix = new Integer(numbers[index]++).toString();
                sb.insert(i, "\"" + prefix + "\":");
                i+=4;
                index++;
                dec = true;
            }
            else if (sb.charAt(i) == '}' && dec)
            {
                dec = false;
                index--;
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