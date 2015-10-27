package kcc.sorg.schoolbus.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import kcc.sorg.schoolbus.R;

public class MapActivity extends AppCompatActivity {
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        textview = (TextView)findViewById(R.id.headtitle);
        textview.setText("实时动态");
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.backbutton:
                finish();
                break;
            case R.id.realtime:
                startActivity(RealtimeActivity.class);
                break;
        }
    }
    public void startActivity(Class  activity){
        Intent intent = new Intent();
        intent.setClass(this, activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
