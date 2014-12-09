package me.s4h.myreaderapp;

import android.app.Application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;

import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssItem;

/**
 * Created by LENOVO on 2014/12/8.
 */
public class MyReaderApplication extends Application implements ReaderService {
    private static final String MYREADER_HOST = "reader.hikarivanish.me:8080";
    private static final String SHARED_COOKIE = "cookie";

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public boolean login(String username, String password) {
        // First set the default cookie manager.
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        URL url = null;
        try {
            url = new URL("http://" + MYREADER_HOST + "/login");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(("username=" + username + "&password=" + password).getBytes());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }


    @Override
    public Page<RssItem> retreiveItem(Long channelId, int page, int pageSize) {
/*        URL url = null;
        try {
            url = new URL("http://" + MYREADER_HOST + "/rssChannel/" + channelId
                    + "/items?sort=publishedDate,desc&size=" + pageSize + "&page=" + page);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                return mapper.readValue(in, new TypeReference<Page<RssItem>>() {
                });
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }*/

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            return mapper.readValue(new URL("http://" + MYREADER_HOST + "/rssChannel/" + channelId
                    + "/items?sort=publishedDate,desc&size=" + pageSize + "&page=" + page)
                    , new TypeReference<Page<RssItem>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }


}
