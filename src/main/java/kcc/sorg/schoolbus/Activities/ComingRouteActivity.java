package kcc.sorg.schoolbus.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import kcc.sorg.schoolbus.R;

public class ComingRouteActivity extends AppCompatActivity {
    private ImageButton mBack;
    private ListView mRouteList;

    private ArrayList<CommingRouteInfo> mRoutes;

    private boolean mStartSearch = false;

    private String TAG = "AvailableRouteActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_route);
        mBack = (ImageButton)findViewById(R.id.back_button);
        mRouteList = (ListView)findViewById(R.id.available_bus_list);


        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buildRoutes();

        mRouteList.setAdapter(new RouteAdapter(this, mRoutes));
        mRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CommingRouteInofActivity.class,position);
            }
        });

    }
    public void startActivity(Class  activity, int position){
        Intent intent = new Intent();
        intent.setClass(this, activity);
        intent.putExtra("INDEX",position);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void buildRoutes(){
        mRoutes = new ArrayList<CommingRouteInfo>();
        CommingRouteInfo route = new CommingRouteInfo("7:00","第一站","17:00","末站",new String[]{"第一站","第二站","第三站","第四站","第五站","第六站","第七站","末站"},20);
        CommingRouteInfo route2 = new CommingRouteInfo("7:00","博山路苗圃路","17:00","灵山路桃林路",new String[]{"博山路苗圃路","昌邑路桃林路","浦城路","浦电路东方路","灵山路桃林路"},10);
        mRoutes.add(route2);
        mRoutes.add(route);
        mRoutes.add(route);
        mRoutes.add(route);
        mRoutes.add(route);
    }
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_available_route, menu);
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



    private class RouteAdapter extends BaseAdapter {
        private LayoutInflater mInflator;
        private ArrayList<CommingRouteInfo> mRoute;
        public RouteAdapter(Context context, ArrayList<CommingRouteInfo> route){
            mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mRoute = new ArrayList<CommingRouteInfo>();
            mRoute.addAll(route);
        }

        @Override
        public int getCount() {
            return mRoute.size();
        }

        @Override
        public Object getItem(int position) {
            return mRoutes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if( convertView == null){
                convertView = mInflator.inflate(R.layout.listview_route_coming,null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mViewSTime = (TextView)convertView.findViewById(R.id.start_time);
                viewHolder.mViewSStop = (TextView)convertView.findViewById(R.id.start_stop);
                viewHolder.mViewETime = (TextView)convertView.findViewById(R.id.end_time);
                viewHolder.mViewEStop = (TextView)convertView.findViewById(R.id.end_stop);
                viewHolder.mViewDetail = (TextView)convertView.findViewById(R.id.route_detail);
                viewHolder.mViewCount = (TextView)convertView.findViewById(R.id.join_number);
                CommingRouteInfo info = mRoute.get(position);

                viewHolder.mViewSTime.setText("起 "+info.routeInfo.mStartTime);
                viewHolder.mViewSStop.setText(info.routeInfo.mStartStop);
                viewHolder.mViewETime.setText("终 "+info.routeInfo.mEndTime);
                viewHolder.mViewEStop.setText(info.routeInfo.mEndStop);
                viewHolder.mViewCount.setText(info.mCount+"人报名");
                String stops = info.routeInfo.mStartStop;
                for (int i = 1; i < info.routeInfo.mStopList.size(); i++){
                    stops = stops +" -> " +info.routeInfo.mStopList.get(i);
                }
                viewHolder.mViewDetail.setText(stops);
                convertView.setTag(viewHolder);
            } else {
                CommingRouteInfo info = mRoute.get(position);
                ViewHolder viewHolder = (ViewHolder)convertView.getTag();
                viewHolder.mViewSTime.setText("起 "+info.routeInfo.mStartTime);
                viewHolder.mViewSStop.setText(info.routeInfo.mStartStop);
                viewHolder.mViewETime.setText("终 "+info.routeInfo.mEndTime);
                viewHolder.mViewEStop.setText(info.routeInfo.mEndStop);
                viewHolder.mViewCount.setText(info.mCount+"人报名");
                String stops = info.routeInfo.mStartStop;
                for (int i = 1; i < info.routeInfo.mStopList.size(); i++){
                    stops = stops +" -> " +info.routeInfo.mStopList.get(i);
                }
                viewHolder.mViewDetail.setText(stops);
            }
            return convertView;
        }
        private  class ViewHolder{
            public TextView mViewSTime;
            public TextView mViewSStop;
            public TextView mViewETime;
            public TextView mViewEStop;
            public TextView mViewDetail;
            public TextView mViewCount;
        }
    }

    private class CommingRouteInfo{
        public RouteInfo routeInfo;
        public int mCount = 0;
        public CommingRouteInfo(String stime, String sstop, String etime, String estop, String[] stoplist){
            routeInfo = new RouteInfo(stime,sstop,etime,estop,stoplist);
            mCount = 0;
        }
        public CommingRouteInfo(String stime, String sstop, String etime, String estop, String[] stoplist, int count){
            routeInfo = new RouteInfo(stime,sstop,etime,estop,stoplist);
            mCount = count;
        }
    }
    private class RouteInfo{
        public String mStartTime;
        public String mStartStop;
        public String mEndTime;
        public String mEndStop;
        public ArrayList<String> mStopList;

        public RouteInfo(String stime, String sstop, String etime, String estop, String[] stoplist){
            mStartTime = stime;
            mStartStop = sstop;
            mEndTime = etime;
            mEndStop = estop;
            mStopList = new ArrayList<String>();
            mStopList.addAll(Arrays.asList(stoplist));
        }
    }
}
