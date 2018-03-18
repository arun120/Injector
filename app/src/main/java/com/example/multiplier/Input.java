package com.example.multiplier;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Input extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //Toast.makeText(getApplicationContext(),"ohhh",Toast.LENGTH_SHORT).show();
        Log.i("sent", "created");

        Button abc=(Button) findViewById(R.id.abc);


        abc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Instrumentation inst=new Instrumentation();
                inst.sendCharacterSync(KeyEvent.KEYCODE_A);
                Log.i("sent","a");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
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
