package kcc.sorg.schoolbus.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import kcc.sorg.schoolbus.R;
/**
 * Created by ford-pro2 on 15/10/20.
 */
public class LoggedUserAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private int[] mLoggedUserList = {R.string.settings,R.string.info_center,R.string.child_info,R.string.booked_schoolbus,R.string.my_order};//,R.string.more,R.string.log_out};
    private int mMsgCount = 1;
    public LoggedUserAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void setMsgCount(int num){
        mMsgCount = num;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mLoggedUserList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("Kyle", "position is " + position);
        if( convertView == null){
            Log.d("Kyle", "build convert view");
            if(position != 1) {
                convertView = mInflater.inflate(R.layout.listview_logged_item, null);
                ViewHolder mViewhodler = new ViewHolder();
                mViewhodler.mMainContent = (TextView) convertView.findViewById(R.id.list_item_title);
                mViewhodler.mMainContent.setText(mLoggedUserList[position]);
                convertView.setTag(mViewhodler);
            }else {
                convertView = mInflater.inflate(R.layout.listview_logged_item_info, null);
                ViewHolder mViewholder = new ViewHolder();
                mViewholder.mMainContent = (TextView) convertView.findViewById(R.id.list_item_title);
                mViewholder.mMainContent.setText(mLoggedUserList[position]);
                mViewholder.mMsgCount = (TextView) convertView.findViewById(R.id.message_count);
                if(mMsgCount > 0) {
                    mViewholder.mMsgCount.setVisibility(View.VISIBLE);
                    mViewholder.mMsgCount.setText(mMsgCount+"");
                }
                convertView.setTag(mViewholder);
            }
        } else {

            Log.d("Kyle", "reset convert view");
            ViewHolder holder = (ViewHolder)convertView.getTag();
            holder.mMainContent.setText(mLoggedUserList[position]);
            if(holder.mMsgCount == null){
                return convertView;
            }else if(position == 1 ){
                if(holder.mMsgCount == null)return convertView;
                if(mMsgCount > 0) {
                    if(holder.mMsgCount.getVisibility() == View.GONE) {
                        holder.mMsgCount.setVisibility(View.VISIBLE);
                        holder.mMsgCount.setText(mMsgCount + "");
                    }
                } else {
                    if(holder.mMsgCount.getVisibility() == View.VISIBLE) {
                        holder.mMsgCount.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.mMsgCount.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
    private static class ViewHolder{
        public TextView mMainContent;
        public TextView mMsgCount;
    }
}
