package dwf.com.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dwf.com.model.StepDTO;
import dwf.com.session.SessionManager;
import dwf.com.model.AccountDTO;

/**
 * Created by Charlie Winnardd on 5/22/2017.
 */

public class Homepage extends AppCompatActivity {

    private static final String TAG = "RunToFreedom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        Log.w(TAG, "testing sessionManager " + sessionManager.getUserData().getUserName() + " " + sessionManager.getUserData().getAccountValue());
    }

    public void viewAccountInfo(View view) {
        Intent i=new Intent(
                Homepage.this,
                AccountInfo.class);
        startActivity(i);
    }

    public void startRun(View view) {
        new Homepage.RunHttpRequestTask().execute();
        Intent i=new Intent(
                Homepage.this,
                Run.class);
        startActivity(i);
    }

    private class RunHttpRequestTask extends AsyncTask<Void, Void, String> {

        private static final String TAG = "RunToFreedom";

        @Override
        protected String doInBackground(Void... params) {
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String username = sessionManager.getUserData().getUserName();
            final String url = "http://192.168.1.19:8080/newRun?username={username}";

            try {
                Log.w(TAG, "attempting beginning of REST call");
                RestTemplate restTemplate = new RestTemplate();
                Log.w(TAG, "adding message converters");
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Log.w(TAG, "making get call with username: " + username);
                String response = restTemplate.getForObject(url, String.class, username);
                Log.w(TAG, "get call completed");
                return response;
            } catch (RestClientException e) {
                Log.w(TAG, "attempted to hit: " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            Log.w(TAG, "onPostExecute");
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            sessionManager.storeNewRun(Integer.valueOf(response));
            Log.w(TAG, "stored new run ID of: " + sessionManager.getUserData().getCurrentRunID());
        }
    }
}
