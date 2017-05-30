package dwf.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent i=new Intent(
                MainActivity.this,
                Homepage.class);
        startActivity(i);
    }

    public void register(View view) {
        Intent i=new Intent(
                MainActivity.this,
                Registration.class);
        startActivity(i);
    }
}
