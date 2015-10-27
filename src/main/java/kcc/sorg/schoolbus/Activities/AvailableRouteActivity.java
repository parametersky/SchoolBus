package kcc.sorg.schoolbus.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

import java.util.ArrayList;
import java.util.Arrays;

import kcc.sorg.schoolbus.R;

public class AvailableRouteActivity extends AppCompatActivity implements OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    private ImageButton mBack;
    private AutoCompleteTextView mSearch;
    private ListView    mRouteList;

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;

    private ArrayList<RouteInfo> mRoutes;

    private boolean mStartSearch = false;

    private String TAG = "AvailableRouteActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_route);
        mBack = (ImageButton)findViewById(R.id.back_button);
        mSearch = (AutoCompleteTextView)findViewById(R.id.searchkey);
        mRouteList = (ListView)findViewById(R.id.available_bus_list);


        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        mSearch.setAdapter(sugAdapter);
        mSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                Log.d(TAG, "onTextChanged:" + cs.toString());
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                // to do: use gps to update city not only for 上海
//                if(mStartSearch) {
//                    Log.d(TAG, "start search");
//                    mPoiSearch.searchInCity((new PoiCitySearchOption())
//                            .city("上海")
//                            .keyword(cs.toString())
//                            .pageNum(load_Index));
//                }
                //there is a bug here that the dropdown will appear in the first time
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city("上海"));
            }
        });
        mSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mStartSearch) {
                        mStartSearch = true;
                    }
                } else {
                    mStartSearch = false;
                }
            }
        });
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
                startActivity(RouteDetailActivity.class,position);
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
        mRoutes = new ArrayList<RouteInfo>();
        RouteInfo route = new RouteInfo("7:00","第一站","17:00","末站",new String[]{"第一站","第二站","第三站","第四站","第五站","第六站","第七站","末站"});
        RouteInfo route2 = new RouteInfo("7:00","万科翡翠滨江","17:00","海桐小学",new String[]{"万科翡翠滨江","昌邑路小学","仁恒滨江","东园三村","海桐小学"});
        mRoutes.add(route2);
        mRoutes.add(route);
        mRoutes.add(route);
        mRoutes.add(route);
        mRoutes.add(route);
    }
    public void onDestroy(){
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
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

    public void onGetPoiResult(PoiResult result) {

        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(AvailableRouteActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(AvailableRouteActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AvailableRouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(AvailableRouteActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        Log.d(TAG,"getresult "+res.toString());
        if (res == null || res.getAllSuggestions() == null) {
            Log.d(TAG,"result getAllSuggestions == null return");
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                Log.d(TAG,"keys are:"+info.key);
                sugAdapter.add(info.key);
            }
        }
        mSearch.showDropDown();
        sugAdapter.notifyDataSetChanged();
    }


    private class RouteAdapter extends BaseAdapter{
        private LayoutInflater mInflator;
        private ArrayList<RouteInfo> mRoute;
        public RouteAdapter(Context context, ArrayList<RouteInfo> route){
            mInflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mRoute = new ArrayList<RouteInfo>();
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
                convertView = mInflator.inflate(R.layout.listview_route_available,null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mViewSTime = (TextView)convertView.findViewById(R.id.start_time);
                viewHolder.mViewSStop = (TextView)convertView.findViewById(R.id.start_stop);
                viewHolder.mViewETime = (TextView)convertView.findViewById(R.id.end_time);
                viewHolder.mViewEStop = (TextView)convertView.findViewById(R.id.end_stop);
                viewHolder.mViewDetail = (TextView)convertView.findViewById(R.id.route_detail);

                RouteInfo info = mRoute.get(position);

                viewHolder.mViewSTime.setText("起 "+info.mStartTime);
                viewHolder.mViewSStop.setText(info.mStartStop);
                viewHolder.mViewETime.setText("终 "+info.mEndTime);
                viewHolder.mViewEStop.setText(info.mEndStop);
                String stops = info.mStartStop;
                for (int i = 1; i < info.mStopList.size(); i++){
                    stops = stops +" -> " +info.mStopList.get(i);
                }
                viewHolder.mViewDetail.setText(stops);
                convertView.setTag(viewHolder);
            } else {
                RouteInfo info = mRoute.get(position);
                ViewHolder viewHolder = (ViewHolder)convertView.getTag();
                viewHolder.mViewSTime.setText("起 "+info.mStartTime);
                viewHolder.mViewSStop.setText(info.mStartStop);
                viewHolder.mViewETime.setText("终 "+info.mEndTime);
                viewHolder.mViewEStop.setText(info.mEndStop);
                String stops = info.mStartStop;
                for (int i = 1; i < info.mStopList.size(); i++){
                    stops = stops +" -> " +info.mStopList.get(i);
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
