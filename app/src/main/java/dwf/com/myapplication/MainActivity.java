package dwf.com.myapplication;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dwf.com.model.AccountDTO;
import dwf.com.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RunToFreedom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        new LoginHttpRequestTask().execute();
    }

    public void register(View view) {
        Intent i=new Intent(
                MainActivity.this,
                Registration.class);
        startActivity(i);
    }

    private class LoginHttpRequestTask extends AsyncTask<Void, Void, AccountDTO> {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        @Override
        protected AccountDTO doInBackground(Void... params) {
            final String url = "http://192.168.1.19:8080/login?username={username}&password={password}";

            try {
                Log.w(TAG, "attempting beginning of REST call");
                RestTemplate restTemplate = new RestTemplate();
                Log.w(TAG, "adding message converters");
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Log.w(TAG, "calling via resttemplate");
                AccountDTO response = restTemplate.getForObject(url, AccountDTO.class, username, password);
                Log.w(TAG, "post call completed");
                return response;
            } catch (RestClientException e) {
                Log.w(TAG, "attempted to hit: " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(AccountDTO dto) {
            Log.w(TAG, "onPostExecute");
            if (dto.getUserName().isEmpty()) {
                Log.w(TAG, "login all messed up");
            } else {
                Log.w(TAG, "********* creating session manager");
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.storeUserData(dto);
                Log.w(TAG, "testing shared preferences " + sessionManager.getUserData().getUserName() + " " + sessionManager.getUserData().getFirstName() + " " + sessionManager.getUserData().getMonthlySavings());
                Intent i=new Intent(
                        MainActivity.this,
                        Homepage.class);
                startActivity(i);
            }
        }
    }
}
