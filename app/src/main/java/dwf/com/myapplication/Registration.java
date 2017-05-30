package dwf.com.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.DialogInterface;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dwf.com.model.AccountDTO;

/**
 * Created by Charlie Winnardd on 5/29/2017.
 */

public class Registration extends AppCompatActivity {

    private static final String TAG = "RunToFreedom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
    }

    protected void registerUser(View view) {
        Log.w(TAG, "registerUser called");
        new Registration.RegistrationHttpRequestTask().execute();
    }

    private class RegistrationHttpRequestTask extends AsyncTask<Void, Void, AccountDTO> {

        private static final String TAG = "RunToFreedom";

        @Override
        protected AccountDTO doInBackground(Void... params) {
            AccountDTO request = buildRequest();
            final String url = "http://192.168.2.6:8080/addUser";

            try {
                Log.d(TAG, "attempting beginning of REST call");
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                AccountDTO response = restTemplate.postForObject(url, request, AccountDTO.class);
                Log.d(TAG, "post call completed");
                return response;
            } catch (RestClientException e) {
                Log.e(TAG, "attempted to hit: " + url, e);
            }
            return null;
        }

        private AccountDTO buildRequest() {
            AccountDTO request = new AccountDTO();
            EditText userName = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            EditText firstName = (EditText) findViewById(R.id.first_name);
            EditText accountValue = (EditText) findViewById(R.id.account_value);
            EditText monthlySavings = (EditText) findViewById(R.id.monthly_savings);
            request.setUserName(userName.getText().toString());
            request.setPassword(password.getText().toString());
            request.setFirstName(firstName.getText().toString());
            request.setAccountValue(Double.valueOf(accountValue.getText().toString()));
            request.setMonthlySavings(Double.valueOf(monthlySavings.getText().toString()));
            return request;
        }

        @Override
        protected void onPostExecute(AccountDTO dto) {
            Log.d(TAG, "onPostExecute");
            openDialog();
        }

        protected void openDialog() {
            AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
            alertDialog.setTitle("Registration Complete!");
            alertDialog.setMessage("You have successfully started your run to freedom");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent i=new Intent(
                                    Registration.this,
                                    Homepage.class);
                            startActivity(i);
                        }
                    });
            alertDialog.show();
        }
    }
}
