package dwf.com.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import java.util.List;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import dwf.com.model.SessionDTO;
import dwf.com.model.StepDTO;
import dwf.com.session.SessionManager;
import dwf.com.model.RunDTO;

/**
 * Created by Charlie Winnardd on 5/22/2017.
 */

public class AccountInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info);
        retrieveAndPopulateUserData();
        retrieveAndPopulateSavedRunsData();
    }

    private void retrieveAndPopulateUserData() {
        SessionManager sessionManager = new SessionManager(this);
        SessionDTO dto = sessionManager.getUserData();

        EditText firstName = (EditText) findViewById(R.id.first_name);
        EditText accountValue = (EditText) findViewById(R.id.account_value);
        EditText monthlySavings = (EditText) findViewById(R.id.monthly_savings);

        firstName.setText(dto.getFirstName());
        accountValue.setText(String.valueOf(dto.getAccountValue()));
        monthlySavings.setText(String.valueOf(dto.getMonthlySavings()));
    }

    public void retrieveAndPopulateSavedRunsData() {
        SessionManager sessionManager = new SessionManager(this);
        int[] savedRuns = sessionManager.getUserData().getSavedRunIDs();
    }

    private class RunsHttpRequestTask extends AsyncTask<Void, Void, List<RunDTO>> {

        private static final String TAG = "RunToFreedom";

        @Override
        protected List<RunDTO> doInBackground(Void... params) {
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            String username = sessionManager.getUserData().getUserName();
            int[] runIds = sessionManager.getUserData().getSavedRunIDs();
            final String url = "http://192.168.1.19:8080/savedRuns?username={username}&runIds={runIds}";

            try {
                Log.w(TAG, "attempting beginning of REST call");
                RestTemplate restTemplate = new RestTemplate();
                Log.w(TAG, "adding message converters");
                restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
                Log.w(TAG, "calling via resttemplate");
                List<RunDTO> response = restTemplate.getForObject(url, List.class, username, runIds);
                Log.w(TAG, "post call completed");
                return response;
            } catch (RestClientException e) {
                Log.w(TAG, "attempted to hit: " + url, e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RunDTO> savedRuns) {
            Log.w(TAG, "onPostExecute");
            int runNumCounter = 1;
            for(RunDTO run : savedRuns) {
                EditText startValue = (EditText) findViewById(R.id.run_run1);
            }



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
