package kcc.sorg.schoolbus.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import kcc.sorg.schoolbus.R;

public class CommingRouteInofActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comming_route_info);
        TextView textview = (TextView)findViewById(R.id.register);
        textview.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comming_route, menu);
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
    public void onClick(View view){
        Log.d("Kyle-Debug", "onClick this view");
        switch(view.getId()){
            case R.id.register:
                Log.d("Kyle-Debug","onclick view build alertdialog");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContextF()).setTitle("报名成功").setMessage("集起更多小伙伴,让它早日开通吧").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                finish();
                break;
            case R.id.back_button:
                finish();
                break;
        }
    }
    public Context getContextF(){
        return this;
    }

}
