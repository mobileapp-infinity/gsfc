package com.infinity.infoway.gsfc.HrAppAdapter;import android.content.Context;import android.content.Intent;import android.os.SystemClock;import android.support.annotation.NonNull;import android.support.v7.widget.CardView;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.LinearLayout;import android.widget.TextView;import com.infinity.infoway.gsfc.HrAppAPI.URLS;import com.infinity.infoway.gsfc.HrAppActivities.ViewAllLeavePunchInOut;import com.infinity.infoway.gsfc.HrAppPojo.LastInOutPojo;import com.infinity.infoway.gsfc.R;public class LastInOutAdapter extends BaseAdapter {    Context ctx;    private long lastClickTime = 0;    LastInOutPojo lastInOutPojo;    public LastInOutAdapter(Context ctx, LastInOutPojo lastInOutPojo) {        this.ctx = ctx;        this.lastInOutPojo = lastInOutPojo;    }    class ViewHolder {        CardView card_root;        TextView tv_last_in_date, tv_last_in_time, tv_last_out_date, last_out_time;        LinearLayout ll_view_all;    }    ViewHolder viewHolder;    @Override    public int getCount() {        // return college.getTable().size();        return lastInOutPojo.getData().size();    }    @Override    public Object getItem(int i) {        return i;    }    @Override    public long getItemId(int i) {        return i;    }    @Override    public View getView(final int i, View view, ViewGroup viewGroup) {        LayoutInflater mInflater = LayoutInflater.from(ctx);        if (view == null) {            view = mInflater.inflate(R.layout.adapter_last_in_last_out, viewGroup, false);            viewHolder = new ViewHolder();            initView(view);            view.setTag(viewHolder);        } else {            viewHolder = (ViewHolder) view.getTag();        }//        viewHolder.tv_last_in_date.setText(lastInOutPojo.getData().get(i).getLastDate()+"");//        viewHolder.tv_last_out_date.setText(lastInOutPojo.getData().get(i).getLastDate()+"");        viewHolder.last_out_time.setText(lastInOutPojo.getData().get(i).getLast_Out() + "");        viewHolder.tv_last_in_time.setText(lastInOutPojo.getData().get(i).getLast_in() + "");        viewHolder.ll_view_all.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                if (SystemClock.elapsedRealtime() - lastClickTime < URLS.TIME_TILL_DISABLE_BTN) {                    return;                }                lastClickTime = SystemClock.elapsedRealtime();                Intent intent = new Intent(ctx, ViewAllLeavePunchInOut.class);                ctx.startActivity(intent);            }        });        return view;    }    private void initView(@NonNull final View itemView) {        viewHolder.card_root = (CardView) itemView.findViewById(R.id.card_root);//        viewHolder.tv_last_in_date = (TextView) itemView.findViewById(R.id.tv_last_in_date);        viewHolder.tv_last_in_time = (TextView) itemView.findViewById(R.id.tv_last_in_time);//        viewHolder.tv_last_out_date = (TextView) itemView.findViewById(R.id.tv_last_out_date);        viewHolder.last_out_time = (TextView) itemView.findViewById(R.id.last_out_time);        viewHolder.ll_view_all = (LinearLayout) itemView.findViewById(R.id.ll_view_all);    }}