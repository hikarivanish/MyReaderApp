package me.s4h.myreaderapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

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
import java.util.List;

import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssChannel;
import me.s4h.myreaderapp.entity.RssItem;

/**
 * Created by LENOVO on 2014/12/8.
 */
public class MyReaderApplication extends Application implements ReaderService {
    private static final String MYREADER_HOST = "reader.hikarivanish.me:8080";
    private static final String PRE_LOGIN = "pre_login";
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public String getSavedUsername() {
        SharedPreferences sh = getSharedPreferences(PRE_LOGIN, Context.MODE_PRIVATE);
        return sh.getString("username", null);
    }

    @Override
    public String getSavedPassword() {
        SharedPreferences sh = getSharedPreferences(PRE_LOGIN, Context.MODE_PRIVATE);
        return sh.getString("password", null);
    }

    @Override
    public void saveLogin(String username, String password) {
        SharedPreferences sh = getSharedPreferences(PRE_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

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
                return urlConnection.getURL().getPath().equals("/reader");
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
    public Page<RssItem> retrieveItem(Long channelId, int page, int pageSize) {
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


    @Override
    public List<RssChannel> retrieveChannels() {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            return mapper.readValue(new URL("http://" + MYREADER_HOST + "/user/channels")
                    , new TypeReference<List<RssChannel>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }
}
