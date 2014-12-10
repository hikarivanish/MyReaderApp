package me.s4h.myreaderapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.s4h.myreaderapp.entity.RssChannel;


public class MainActivity extends FragmentActivity implements ItemDetailFragment.OnItemDetailFragmentInteractionListener, ItemListFragment.OnItemListFragmentInteractionListener {

    ItemListFragment itemListFragment;
    ItemDetailFragment itemDetailFragment;

    @InjectView(R.id.pager)
    ViewPager pager;

    @InjectView(R.id.left_drawer)
    ListView drawerList;

    ChannelAdapter channelAdapter;
    ReaderService readerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);


        channelAdapter = new ChannelAdapter(this);
        drawerList.setAdapter(channelAdapter);

        itemListFragment = new ItemListFragment();
        itemDetailFragment = new ItemDetailFragment();

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public android.support.v4.app.Fragment getItem(int i) {
                switch (i) {
                    case 0:
                        return itemListFragment;
                    default:
                        return itemDetailFragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });


        readerService = (ReaderService) getApplication();


        new LoadChannelTask().execute();
    }


    class LoadChannelTask extends AsyncTask<Void, Void, List<RssChannel>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("loading channels");
            progressDialog.show();
        }

        @Override
        protected List<RssChannel> doInBackground(Void... voids) {
            return readerService.retrieveChannels();
        }

        @Override
        protected void onPostExecute(List<RssChannel> rssChannels) {
            progressDialog.dismiss();
            if (rssChannels == null) {
                Toast.makeText(MainActivity.this, "load failed", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.this.channelAdapter.addAll(rssChannels);

            }
        }
    }


    static class ChannelAdapter extends ArrayAdapter<RssChannel> {

        public ChannelAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }


    }


}
