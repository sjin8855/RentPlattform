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

public class AllItemAdapter extends ArrayAdapter<item> {
    private Activity activity;
    private ArrayList<item> allItems;
    private static LayoutInflater inflater = null;

    public AllItemAdapter(Activity activity, int textViewResourceId, ArrayList<item> _allItems) {
        super(activity, textViewResourceId, _allItems);
        try {
            this.activity = activity;
            this.allItems = _allItems;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return allItems.size();
    }

    public item getItem(item position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView all_display_name;
        public TextView all_display_type;
        public TextView all_display_availability;
        public TextView all_display_key;
        public TextView all_display_Fee;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final AllItemAdapter.ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.adapter_allitem, null);
                holder = new AllItemAdapter.ViewHolder();

                holder.all_display_name = (TextView) vi.findViewById(R.id.all_display_name);
                holder.all_display_type = (TextView) vi.findViewById(R.id.all_display_type);
                holder.all_display_availability = (TextView) vi.findViewById(R.id.all_display_availability);
                holder.all_display_key = (TextView) vi.findViewById(R.id.all_display_key);
                holder.all_display_Fee = (TextView) vi.findViewById(R.id.all_display_fee);
                vi.setTag(holder);
            } else {
                holder = (AllItemAdapter.ViewHolder) vi.getTag();
            }


            holder.all_display_name.setText(allItems.get(position).getitemName());
            holder.all_display_type.setText(allItems.get(position).getitemType());
            if (allItems.get(position).getitemRentalAvailability() == 0)
                holder.all_display_availability.setText("대여가능");
            else if (allItems.get(position).getitemRentalAvailability() == 1)
                holder.all_display_availability.setText("대여중");
            else
                holder.all_display_availability.setText("대여불가");
            holder.all_display_key.setText(allItems.get(position).getitemKey());
            holder.all_display_Fee.setText(allItems.get(position).getitemFee());



        } catch (Exception e) {


        }
        return vi;
    }
}