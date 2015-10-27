package kcc.sorg.schoolbus.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import kcc.sorg.schoolbus.R;

public class NewRoute extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mBack;
    private TextView mAddTake;
    private TextView mAddLeave;
    private TextView mAddTime;
    private TextView mRaise;
    private boolean mTake = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        mBack = (ImageButton) findViewById(R.id.back_button);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddLeave = (TextView) findViewById(R.id.add_leave);
        mAddTake = (TextView) findViewById(R.id.add_take);
        mAddTime = (TextView) findViewById(R.id.add_time);
        mRaise = (TextView)findViewById(R.id.raise);
        mAddTake.setOnClickListener(this);
        mAddTime.setOnClickListener(this);
        mAddLeave.setOnClickListener(this);
        mRaise.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_route, menu);
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add_take:
                Log.d("Kyle-Debug", "onClick this is the item1");
                Intent intent = new Intent();
                mTake = true;
                intent.setClass(this, ChoosePoiActivity.class);
                startActivityForResult(intent, 103);
                break;
            case R.id.add_leave:
                Log.d("Kyle-Debug", "onClick this is the item2");
                Intent intent1 = new Intent();
                intent1.setClass(this, ChoosePoiActivity.class);
                startActivityForResult(intent1, 103);
                break;
            case R.id.add_time:
                Log.d("Kyle-Debug", "onClick this is the item3");
                break;
            case R.id.raise:
                Log.d("Kyle-Debug", "onClick this is the item4");
                AlertDialog.Builder builder = new AlertDialog.Builder(getContextF()).setMessage("您的需求已经加入我们的数据库，一旦路线形成，我们会通知你").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create().show();
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null)return;
        Log.d("Kyle-Debug","result is "+data.getStringExtra("address"));
        if(mTake){
           mAddTake.setText(data.getStringExtra("address"));
            mTake = false;
        } else {
            mAddLeave.setText(data.getStringExtra("address"));
        }
    }
    public Context getContextF(){
        return this;
    }
}
