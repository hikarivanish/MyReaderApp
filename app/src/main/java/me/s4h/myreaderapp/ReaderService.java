package me.s4h.myreaderapp;

import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssItem;

/**
 * Created by LENOVO on 2014/12/9.
 */
public interface ReaderService {

    Page<RssItem> retreiveItem(Long channelId, int page, int pageSize);

    boolean login(String username, String password) ;
}
