package dwf.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Charlie Winnardd on 5/22/2017.
 */

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
    }

    public void viewAccountInfo(View view) {
        Intent i=new Intent(
                Homepage.this,
                AccountInfo.class);
        startActivity(i);
    }

    public void startRun(View view) {
        Intent i=new Intent(
                Homepage.this,
                Run.class);
        startActivity(i);
    }
}
