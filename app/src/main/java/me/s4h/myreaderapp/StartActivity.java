package me.s4h.myreaderapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.s4h.myreaderapp.entity.Page;
import me.s4h.myreaderapp.entity.RssItem;


public class StartActivity extends Activity {

    @InjectView(R.id.btn_login)
    Button btnLogin;

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;

    ReaderService readerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        readerService = (ReaderService) getApplication();

        String username = readerService.getSavedUsername();
        String password = readerService.getSavedPassword();
        if (username != null && password != null) {
            new LoginTask(readerService, this).execute(username, password);
        }

        ButterKnife.inject(this);
    }


    @OnClick(R.id.btn_login)
    void OnBtnLoginClicked() {
        new LoginTask((ReaderService) getApplication(), this)
                .execute(etUsername.getText().toString(), etPassword.getText().toString());
    }


    static class LoginTask extends AsyncTask<String, Void, Boolean> {

        private Context cxt;
        private ReaderService readerService;
        private ProgressDialog progressDialog;

        private String username;
        private String password;

        public LoginTask(ReaderService readerService, Context cxt) {
            this.readerService = readerService;
            this.cxt = cxt;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(cxt);
            progressDialog.setTitle("Logining");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            username = strings[0];
            password = strings[1];
            return this.readerService.login(username, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                readerService.saveLogin(username,password);
                cxt.startActivity(new Intent(cxt, MainActivity.class));
            } else {
                Toast.makeText(cxt, "login failed", Toast.LENGTH_SHORT).show();
            }
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
