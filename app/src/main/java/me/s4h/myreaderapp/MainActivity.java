package me.s4h.myreaderapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends FragmentActivity implements ItemDetailFragment.OnItemDetailFragmentInteractionListener,ItemListFragment.OnItemListFragmentInteractionListener {

    ItemListFragment itemListFragment ;
    ItemDetailFragment itemDetailFragment;

    @InjectView(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);


        itemListFragment = new ItemListFragment();
        itemDetailFragment = new ItemDetailFragment();

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public android.support.v4.app.Fragment getItem(int i) {
                switch (i){
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

    }





}
