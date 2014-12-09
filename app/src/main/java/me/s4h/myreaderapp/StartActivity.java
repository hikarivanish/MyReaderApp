package me.s4h.myreaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssItem;


public class StartActivity extends Activity {

    @InjectView(R.id.button)
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.inject(this);
    }


    @OnClick(R.id.button)
    void login() {
//        this.startActivity(new Intent(this,MainActivity.class));

        new LoginTask((ReaderService) getApplication(),this).execute();
    }


    static class LoginTask extends AsyncTask<String, Integer, Void> {

        private Context cxt;
        private ReaderService readerService;

        public LoginTask(ReaderService readerService, Context cxt) {
            this.readerService = readerService;
            this.cxt = cxt;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                this.readerService.login("cc@cc.com", "cc");
                Page<RssItem> rssItemPage = this.readerService.retreiveItem(1L, 0, 10);
                Log.i("fds", rssItemPage.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
