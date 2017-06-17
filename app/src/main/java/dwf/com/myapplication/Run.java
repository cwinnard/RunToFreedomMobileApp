package dwf.com.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import 	android.util.Log;
import android.view.View;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dwf.com.model.StepDTO;
import dwf.com.session.SessionManager;

/**
 * Created by Charlie Winnardd on 5/25/2017.
 */

public class Run extends AppCompatActivity {

    private static final String TAG = "RunToFreedom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_main);
    }

    protected void executeStep(View view) {
        Log.w(TAG, "executeStep called");
        new StepHttpRequestTask().execute();
    }

    private class StepHttpRequestTask extends AsyncTask<Void, Void, StepDTO> {

        private static final String TAG = "RunToFreedom";

        @Override
        protected StepDTO doInBackground(Void... params) {
            StepDTO request = buildRequest();
            final String url = "http://192.168.1.19:8080/step";

            try {
                Log.w(TAG, "attempting beginning of REST call");
                RestTemplate restTemplate = new RestTemplate();
                Log.w(TAG, "adding message converters");
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Log.w(TAG, "calling via resttemplate");
                StepDTO response = restTemplate.postForObject(url, request, StepDTO.class);
                Log.w(TAG, "post call completed");
                return response;
            } catch (RestClientException e) {
                Log.w(TAG, "attempted to hit: " + url, e);
            }
            return null;
        }

        private StepDTO buildRequest() {
            StepDTO request = new StepDTO();
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            EditText accountValue = (EditText) findViewById(R.id.accountValue);
            EditText monthlySavings = (EditText) findViewById(R.id.monthlySavings);
            request.setAccountValue(Double.valueOf(accountValue.getText().toString()));
            request.setMonthlySavings(Double.valueOf(monthlySavings.getText().toString()));
            request.setRunId(sessionManager.getUserData().getCurrentRunID());
            return request;
        }

        @Override
        protected void onPostExecute(StepDTO dto) {
            Log.w(TAG, "onPostExecute");
            EditText accountValue = (EditText) findViewById(R.id.accountValue);
            EditText monthlySavings = (EditText) findViewById(R.id.monthlySavings);
            EditText year = (EditText) findViewById(R.id.year);
            String newAccountValue = String.valueOf(dto.getAccountValue());
            String newMonthlySavings = String.valueOf(dto.getMonthlySavings());
            String newYear = String.valueOf(Integer.valueOf(year.getText().toString()) + 5);
            accountValue.setText(newAccountValue);
            monthlySavings.setText(newMonthlySavings);
            year.setText(newYear);
        }
    }
}
