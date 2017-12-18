package com.example.sangjin_lee.rentplattform;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sangjin-Lee on 2017-12-17.
 */

public class myrentAdapter extends ArrayAdapter<rentInfo> {
    private Activity activity;
    private ArrayList<rentInfo> myRents;
    private static LayoutInflater inflater = null;

    public myrentAdapter (Activity activity, int textViewResourceId,ArrayList<rentInfo> _myRents) {
        super(activity, textViewResourceId, _myRents);
        try {
            this.activity = activity;
            this.myRents = _myRents;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return myRents.size();
    }

    public item getItem(item position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_fee;
        public TextView display_startdate;
        public TextView display_enddate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final myrentAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.adapter_myrents, null);
                holder = new myrentAdapter.ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.rents_display_name);
                holder.display_fee = (TextView) vi.findViewById(R.id.rents_display_fee);
                holder.display_startdate = (TextView) vi.findViewById(R.id.rent_display_startdate);
                holder.display_enddate = (TextView) vi.findViewById(R.id.rent_display_enddate);

                vi.setTag(holder);
            } else {
                holder = (myrentAdapter.ViewHolder) vi.getTag();
            }



            holder.display_name.setText(myRents.get(position).getitemName());
            holder.display_fee.setText(myRents.get(position).getItemFee());
            holder.display_startdate.setText(myRents.get(position).getstartRent());
            holder.display_enddate.setText(myRents.get(position).getendRent());


        } catch (Exception e) {


        }
        return vi;
    }
}
