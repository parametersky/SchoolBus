package kcc.sorg.schoolbus.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kcc.sorg.schoolbus.R;

public class MessageCenterActivity extends AppCompatActivity {

    private TextView mTitle;
    private ListView mMessagesList;
    private ImageButton mBack;
    private ArrayList<MessageInfo> mMessages;
    private String TAG = "MessageCenterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        mTitle = (TextView)findViewById(R.id.headtitle);
        mTitle.setText(R.string.Message_Center_Title);
        mBack = (ImageButton)findViewById(R.id.backbutton);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buildMessages();
        mMessagesList = (ListView)findViewById(R.id.msg_list);
        MessageAdapter messageAdapter = new MessageAdapter(this, mMessages);
        mMessagesList.setAdapter(messageAdapter);
    }

    public void buildMessages(){
        mMessages = new ArrayList<MessageInfo>();
        mMessages.add(new MessageInfo("7:45","A地址","您的宝贝已下车"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_center, menu);
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

    private class MessageAdapter extends BaseAdapter{
        private LayoutInflater mInflator = null;
        private ArrayList<MessageInfo> mContent = null;
        public MessageAdapter(Context context, ArrayList<MessageInfo> list){
            mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mContent = new ArrayList<MessageInfo>();
            mContent.addAll(list);
        }
        public void addMessage(MessageInfo msg){
            mContent.add(msg);
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mContent.size();
        }

        @Override
        public Object getItem(int position) {
            return mContent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView for " + position);
            if(convertView == null){
                convertView = mInflator.inflate(R.layout.listview_msg,null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mViewTime = (TextView)convertView.findViewById(R.id.time);
                viewHolder.mViewAddr = (TextView)convertView.findViewById(R.id.address);
                viewHolder.mViewContent = (TextView)convertView.findViewById(R.id.content);

                viewHolder.mViewTime.setText(mContent.get(position).mTime);
                viewHolder.mViewAddr.setText(mContent.get(position).mAddress);
                viewHolder.mViewContent.setText(mContent.get(position).mContent);

                convertView.setTag(viewHolder);
            } else {
                ViewHolder viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.mViewTime.setText(mContent.get(position).mTime);
                viewHolder.mViewAddr.setText(mContent.get(position).mAddress);
                viewHolder.mViewContent.setText(mContent.get(position).mContent);
            }
            return convertView;
        }
        private class ViewHolder{
            TextView mViewTime;
            TextView mViewAddr;
            TextView mViewContent;
        }
    }
    private class MessageInfo{
        public String mTime;
        public String mAddress;
        public String mContent;
        public MessageInfo(String time, String addr, String content){
            mTime = time;
            mAddress = addr;
            mContent = content;
        }
    }
}
