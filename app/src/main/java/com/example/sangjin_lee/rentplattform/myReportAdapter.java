package com.example.sangjin_lee.rentplattform;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sangjin-Lee on 2017-12-17.
 */

public class myReportAdapter extends ArrayAdapter<report> {
    private Activity activity;
    private ArrayList<report> myReports;
    private static LayoutInflater inflater = null;

    public myReportAdapter (Activity activity, int textViewResourceId,ArrayList<report> _myReports) {
        super(activity, textViewResourceId, _myReports);
        try {
            this.activity = activity;
            this.myReports = _myReports;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return myReports.size();
    }

    public item getItem(item position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_title;
        public TextView display_confirm;
        public TextView display_date;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final myReportAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.adapter_checkreport, null);
                holder = new myReportAdapter.ViewHolder();

                holder.display_title = (TextView) vi.findViewById(R.id.check_display_title);
                holder.display_confirm = (TextView) vi.findViewById(R.id.check_display_confirm);
                holder.display_date = (TextView) vi.findViewById(R.id.check_display_date);


                vi.setTag(holder);
            } else {
                holder = (myReportAdapter.ViewHolder) vi.getTag();
            }



            holder.display_title.setText(myReports.get(position).getreportTitle());
            if(myReports.get(position).getcheckReport() == true)
                holder.display_confirm.setText("처리완료");
            else
                holder.display_confirm.setText("처리중");
            holder.display_date.setText(myReports.get(position).getDate());


        } catch (Exception e) {


        }
        return vi;
    }
}
