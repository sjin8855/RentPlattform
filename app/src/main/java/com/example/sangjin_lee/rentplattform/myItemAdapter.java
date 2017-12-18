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

public class myItemAdapter extends ArrayAdapter<item> {
    private Activity activity;
    private ArrayList<item> myItems;
    private static LayoutInflater inflater = null;

    public myItemAdapter(Activity activity, int textViewResourceId, ArrayList<item> _myItems) {
        super(activity, textViewResourceId, _myItems);
        try {
            this.activity = activity;
            this.myItems = _myItems;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return myItems.size();
    }

    public item getItem(item position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_type;
        public TextView display_availability;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.adapter_withdraw, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                holder.display_type = (TextView) vi.findViewById(R.id.display_type);
                holder.display_availability = (TextView) vi.findViewById(R.id.display_availability);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }


            holder.display_name.setText(myItems.get(position).getitemName());
            holder.display_type.setText(myItems.get(position).getitemType());
            if (myItems.get(position).getitemRentalAvailability() == 0)
                holder.display_availability.setText("대여가능");
            else if (myItems.get(position).getitemRentalAvailability() == 1)
                holder.display_availability.setText("대여중");
            else
                holder.display_availability.setText("대여불가");


        } catch (Exception e) {


        }
        return vi;
    }
}