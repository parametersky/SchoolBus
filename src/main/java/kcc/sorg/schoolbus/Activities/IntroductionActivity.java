package kcc.sorg.schoolbus.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

import java.util.ArrayList;

import kcc.sorg.schoolbus.R;

public class IntroductionActivity extends AppCompatActivity {

    private ViewPager mPager = null;
    private ArrayList<View> mPages = null;
    private Scroller mScroller = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mPager = (ViewPager)findViewById(R.id.pager);

        mPages = new ArrayList<View>();
        View view2 = getLayoutInflater().inflate(R.layout.image2, null);
        mPages.add(getLayoutInflater().inflate(R.layout.image1,null));
        mPages.add(view2);
        Button button = (Button)view2.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(),MainActivity.class);
                startActivity(intent);
                SharedPreferences setting = getSharedPreferences("first_install", Context.MODE_MULTI_PROCESS);
//                boolean user_first = setting.getBoolean("FIRST",true);
                setting.edit().putBoolean("FIRST",false).commit();
                finish();
            }
        });
        PagerAdapter mAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return mPages.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return (view == object);
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position){
//                View view =  container.getChildAt(position);
//                if( view == null){
                View view = mPages.get(position);
                ((ViewPager)container).addView(view);
                return view;
            }

            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeViewAt(position);
            }

        };

        mPager.setAdapter(mAdapter);
        mPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_introduction, menu);
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
