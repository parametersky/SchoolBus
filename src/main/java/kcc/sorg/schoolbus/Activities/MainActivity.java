package kcc.sorg.schoolbus.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import kcc.sorg.schoolbus.R;
import kcc.sorg.schoolbus.adapters.LoggedUserAdapter;

public class MainActivity extends Activity implements View.OnClickListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private String TAG = "MainActivity";
    private ImageButton mUserButton = null;

    private TextView mRouteComming = null;
    private TextView mRouteAvailable = null;
    private TextView mNewRoute = null;

    // ui components in drawer layout

    private ImageView mUserProfile = null;
    private ListView  mUserInfo = null;
    private DrawerLayout mDrawerLayout = null;
    private ArrayAdapter<String> mLoggedUserInfoList = null;
    private ArrayAdapter<String> mUnloggedUserInfoList = null;
    private int[] mLoggedUserList = {R.string.settings,R.string.info_center,R.string.child_info,R.string.booked_schoolbus,R.string.my_order};//,R.string.more,R.string.log_out};
    private int[] mUnloggedUserList = {R.string.log_in};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mRouteAvailable = (TextView)findViewById(R.id.route_available);
        mRouteComming = (TextView)findViewById(R.id.route_coming);
        mNewRoute = (TextView)findViewById(R.id.new_route);

        mRouteComming.setOnClickListener(this);
        mRouteAvailable.setOnClickListener(this);
        mNewRoute.setOnClickListener(this);

        initDrawer();


    }
    private void initDrawer(){
        mUserProfile = (ImageView)findViewById(R.id.user_profile);
        mUserInfo = (ListView)findViewById(R.id.userinfo);

        if(isUserLogedIn()){
            mUserInfo.setAdapter(new LoggedUserAdapter(this));
            mUserInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "onItemClick: " + position);
                    switch (position){
                        case 1:
                            startActivity(MessageCenterActivity.class);
                            break;
                        default:
                            Toast.makeText(getApplicationContext(),"Comming soon",Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

    }

    public void onImageClick(View view){
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }
    private boolean isUserLogedIn(){
        // TO DO
        return true;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.route_available:
                startActivity(AvailableRouteActivity.class);
                break;
            case R.id.new_route:
                startActivity(NewRoute.class);
                break;
            case R.id.route_coming:
                startActivity(ComingRouteActivity.class);
                break;
            case R.id.map:
                Intent intent = new Intent();
                intent.setClass(this, MapActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
        }
    }

    public void startActivity(Class  activity){
        Intent intent = new Intent();
        intent.setClass(this, activity);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
//            ((MainActivity) context).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
