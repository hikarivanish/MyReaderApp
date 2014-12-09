package me.s4h.myreaderapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssItem;

/**
 * Created by LENOVO on 2014/12/8.
 */
public class MyReaderApplication extends Application implements ReaderService {
    private static final String MYREADER_HOST = "reader.hikarivanish.me";
    private static final String SHARED_COOKIE = "cookie";
    private static final HttpHost HTTP_HOST = new HttpHost(MYREADER_HOST, 8080);

    DefaultHttpClient client = new DefaultHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean login(String username, String password) {
        HttpPost httpPost = new HttpPost("/login");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = client.execute(HTTP_HOST, httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                List<Cookie> cookies = client.getCookieStore().getCookies();
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_COOKIE, Context.MODE_PRIVATE).edit();
                for (Cookie cookie : cookies) {
                    editor.putString(cookie.getName(), cookie.getValue());
                }
                editor.commit();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    @Override
    public Page<RssItem> retreiveItem(Long channelId, int page, int pageSize) {
        HttpGet httpGet = new HttpGet("/rssChannel/" + channelId + "/items");
        HttpParams params = new BasicHttpParams();
        params.setParameter("page", page);
        params.setParameter("size", pageSize);
        params.setParameter("sort", "publishedDate,desc");
        httpGet.setParams(params);

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(HTTP_HOST, httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                httpResponse.getEntity().getContent();
                Page<RssItem> pageItem = mapper.readValue(httpResponse.getEntity().getContent(), Page.class);

                return pageItem;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }


}
