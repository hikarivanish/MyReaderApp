package me.s4h.myreaderapp;

import java.util.List;

import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssChannel;
import me.s4h.myreaderapp.entity.RssItem;

/**
 * Created by LENOVO on 2014/12/9.
 */
public interface ReaderService {

    Page<RssItem> retrieveItem(Long channelId, int page, int pageSize);

    boolean login(String username, String password) ;

    String getSavedUsername();
    String getSavedPassword();
    void saveLogin(String username,String password);

    List<RssChannel> retrieveChannels();
}
