package com.infinity.infoway.gsfc.HrAppAdapter;import android.content.Context;import android.content.Intent;import android.os.SystemClock;import android.support.annotation.NonNull;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.CompoundButton;import android.widget.LinearLayout;import android.widget.Switch;import com.infinity.infoway.gsfc.CommonCls.CustomBoldTextView;import com.infinity.infoway.gsfc.CommonCls.CustomTextView;import com.infinity.infoway.gsfc.HrAppAPI.URLS;import com.infinity.infoway.gsfc.HrAppActivities.AddLeaveAcivity;import com.infinity.infoway.gsfc.HrAppPojo.Leave_list_pojo;import com.infinity.infoway.gsfc.R;import java.util.List;public class ViewLeaveListingAdapter extends BaseAdapter {    Context ctx;    List<Leave_list_pojo.DataBean> listall;    Leave_list_pojo leave_list_pojo;    onSwitchOn onSwitchOn;    Boolean b;    View view_;    private int selectedPosition = -1;    Boolean is_check;    private long lastClickTime = 0;    public interface onSwitchOn {        public void onSwitchOn(Boolean is_checked);    }    public ViewLeaveListingAdapter(Context ctx, Leave_list_pojo leave_list_pojo, List<Leave_list_pojo.DataBean> listall) {        this.ctx = ctx;        this.leave_list_pojo = leave_list_pojo;        this.listall = listall;        this.onSwitchOn = onSwitchOn;        this.b=b;        this.is_check=is_check;        System.out.println("call ################ ");    }    class ViewHolder {        Switch cb_check;        /**         * View Cancel Leave         */        private CustomBoldTextView mTxtEnrollNo;        /**         * Pending/Approve         */        private CustomBoldTextView mTxtSrNo;        public Switch mCbCheck;        private LinearLayout mLlMainApproveHeadderLeave;        private LinearLayout mLlMainHeder;        /**         * Pending         */        private CustomTextView mTvLeaveStatus;        /**         * 01/08/2018 \n(09:00 AM)         */        private CustomTextView mTvFromDate;        /**         * 01/08/2018 \n(09:00 AM)         */        private CustomTextView mTvToDate;        private LinearLayout mLlPending,main;        /**         * Approved         */        private CustomTextView mTvLS;        /**         * 01/08/2018 \n(09:00 AM)         */        private CustomTextView mTvFromDateTime;        /**         * 01/08/2018 \n(09:00 AM)         */        private CustomTextView mTvToDateTime;        private LinearLayout mLlApprove;    }    ViewHolder viewHolder;    @Override    public int getCount() {        // return college.getTable().size();        return listall.size();    }    @Override    public Object getItem(int i) {        return i;    }    @Override    public long getItemId(int i) {        return i;    }    @Override    public View getView(final int i, View view, ViewGroup viewGroup) {        LayoutInflater mInflater = LayoutInflater.from(ctx);        view_ = view;        if (view_ == null) {            view_ = mInflater.inflate(R.layout.cancel_leave_adapter, viewGroup, false);            viewHolder = new ViewHolder();            initView(view_);            view_.setTag(viewHolder);        } else {            viewHolder = (ViewHolder) view_.getTag();        }        viewHolder.mCbCheck.setTag(i);//        if (i == selectedPosition) {//            viewHolder.mCbCheck.setChecked(true);////        } else {//            viewHolder.mCbCheck.setChecked(false);//        }       /* if (i == 0) {            viewHolder.mLlMainApproveHeadderLeave.setVisibility(View.VISIBLE);            viewHolder.mLlMainHeder.setVisibility(View.VISIBLE);        } else {            viewHolder.mLlMainApproveHeadderLeave.setVisibility(View.GONE);            viewHolder.mLlMainHeder.setVisibility(View.GONE);        }*///        viewHolder.mCbCheck.setChecked(true);        b = true;        viewHolder.mCbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()        {            @Override            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)            {                System.out.println("jkhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");                onSwitchOn.onSwitchOn(isChecked);//                int pos = (Integer) buttonView.getTag();                /*if (buttonView.isShown())                {                    if (!buttonView.isChecked())                    {                        b = false;                        onSwitchOn.onSwitchOn(b);                    }                    else if (buttonView.isChecked())                    {                        b = true;                        onSwitchOn.onSwitchOn(b);                    }                }*/            }        });        if (i%2==0)        {            viewHolder.main.setBackgroundColor(ctx.getResources().getColor(R.color.test));        }        viewHolder.main.setOnLongClickListener(new View.OnLongClickListener() {            @Override            public boolean onLongClick(View view) {                return false;            }        });        viewHolder.mTvLeaveStatus.setText(listall.get(i).getLeave_Status() + "");        viewHolder.mTvFromDate.setText(listall.get(i).getFrom_date() + "");        viewHolder.mTvToDate.setText(listall.get(i).getTo_date() + "");        viewHolder.mLlApprove.setOnClickListener(new View.OnClickListener()        {            @Override            public void onClick(View v) {                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)                {                    return;                }                lastClickTime = SystemClock.elapsedRealtime();//                Intent intent = new Intent(ctx, ViewLeaveActivity.class);                Intent intent = new Intent(ctx, AddLeaveAcivity.class);                intent.putExtra("ID",listall.get(i).getId());                intent.putExtra("status",listall.get(i).getLeave_Status());                intent.putExtra("is_submit",false);                ctx.startActivity(intent);            }        });        viewHolder.mLlPending.setOnClickListener(new View.OnClickListener()        {            @Override            public void onClick(View v) {                if (SystemClock.elapsedRealtime() - lastClickTime <  URLS.TIME_TILL_DISABLE_BTN)                {                    return;                }                lastClickTime = SystemClock.elapsedRealtime();                String status;//                Intent intent = new Intent(ctx, ViewLeaveActivity.class);                Intent intent = new Intent(ctx, AddLeaveAcivity.class);                intent.putExtra("ID",listall.get(i).getId());                intent.putExtra("status",listall.get(i).getLeave_Status());                intent.putExtra("is_submit",false);//                status = listall.get(i).getLeave_Status();////                if (listall.get(i).getLeave_Status().compareToIgnoreCase("Approve")==0)//                {//                    intent.putExtra("status","1");//                }//                else if (listall.get(i).getLeave_Status().compareToIgnoreCase("Partial Approve")==0)//                {//                    intent.putExtra("status","2");//                }//                else//                {//                    intent.putExtra("status","0");//                }                ctx.startActivity(intent);            }        });        return view_;    }    private void initView(@NonNull final View itemView)    {        viewHolder.mTxtEnrollNo = (CustomBoldTextView) itemView.findViewById(R.id.txt_enroll_no);        viewHolder.mTxtSrNo = (CustomBoldTextView) itemView.findViewById(R.id.txt_sr_no);        viewHolder.mCbCheck = (Switch) itemView.findViewById(R.id.cb_check);        viewHolder.mLlMainApproveHeadderLeave = (LinearLayout) itemView.findViewById(R.id.ll_main_approve_headder_leave);        viewHolder.mLlMainHeder = (LinearLayout) itemView.findViewById(R.id.ll_main_heder);        viewHolder.main = (LinearLayout) itemView.findViewById(R.id.main);        viewHolder.mTvLeaveStatus = (CustomTextView) itemView.findViewById(R.id.tv_leave_status);        viewHolder.mTvFromDate = (CustomTextView) itemView.findViewById(R.id.tv_from_date);        viewHolder.mTvToDate = (CustomTextView) itemView.findViewById(R.id.tv_to_date);        viewHolder.mLlPending = (LinearLayout) itemView.findViewById(R.id.ll_pending);        viewHolder.mTvLS = (CustomTextView) itemView.findViewById(R.id.tv_l_s);        viewHolder.mTvFromDateTime = (CustomTextView) itemView.findViewById(R.id.tv_from_date_time);        viewHolder.mTvToDateTime = (CustomTextView) itemView.findViewById(R.id.tv_to_date_time);        viewHolder.mLlApprove = (LinearLayout) itemView.findViewById(R.id.ll_approve);//        if (is_check)//        {//            viewHolder.mCbCheck.setChecked(true);//        }//        else {//            viewHolder.mCbCheck.setChecked(false);//        }    }}