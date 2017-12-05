package com.example.sangjin_lee.rentplattform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ConfirmItemActivity extends AppCompatActivity {

    static final String[] LIST_MENU = {"LIST1", "LIST2", "LIST3"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_item);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.content_confirm_item) ;
        listview.setAdapter(adapter) ;
    }
}
