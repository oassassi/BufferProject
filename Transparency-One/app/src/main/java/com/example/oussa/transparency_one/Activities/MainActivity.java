package com.example.oussa.transparency_one.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.oussa.transparency_one.DTOs.Notification;
import com.example.oussa.transparency_one.DTOs.ReceivedNotificationAdapter;
import com.example.oussa.transparency_one.DTOs.SentNotificationAdapter;
import com.example.oussa.transparency_one.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("My requests");

        tabHost = (TabHost) findViewById(R.id.tabhost);
        //Important
        tabHost.setVisibility(TabHost.VISIBLE);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("received");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("sent");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("updates");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected

        tab1.setIndicator("Received");
        tab1.setContent(R.id.i_layout_1);

        tab2.setIndicator("Sent");
        tab2.setContent(R.id.i_layout_2);

        tab3.setIndicator("Updates");
        tab3.setContent(R.id.i_layout_3);

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(0);
        loadDataInTab("received");


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            @Override
            public void onTabChanged(String tabId) {
                loadDataInTab(tabId);
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void loadDataInTab(String tabId)
    {
        ListView listView = (ListView) tabHost.getCurrentView().findViewById(R.id.listView);
        List<Notification> notifications = getMockedNotifications(tabId);
        switch (tabId)
        {
            case "received":
                listView.setAdapter(new ReceivedNotificationAdapter(MainActivity.this, notifications));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(MainActivity.this, RequestActivity.class);
                        Notification notification = (Notification) parent.getItemAtPosition(position);
                        myIntent.putExtra("requestedProductName", notification.getProductName());
                        myIntent.putExtra("companyName", notification.getSupplierName());
                        myIntent.putExtra("date", notification.getCreationDate());

                        MainActivity.this.startActivity(myIntent);
                    }
                });
                break;
            case "sent":
                listView.setAdapter(new SentNotificationAdapter(MainActivity.this, notifications));
                break;
        }
    }

    private View createTabIndicator(String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_indicator_label);
        textView.setText(text);
        return view;
    }

    private List<Notification> getMockedNotifications(String notificationType){
        if(notificationType == "received")
        {
            List<Notification> notifications = new ArrayList<Notification>();
            notifications.add(new Notification("Paella", "Jumpy Fishes Ltd", "2 hours ago", R.drawable.hands_icon));
            notifications.add(new Notification("Marshmallows", "Candies for us", "4 hours ago", R.drawable.eye_icon));
            notifications.add(new Notification("Yogurt Cherry", "Milk & co", "1 day ago", R.drawable.hands_icon));
            notifications.add(new Notification("Mayan Drink", "Energy inc.", "1 week ago", R.drawable.hands_icon));
            notifications.add(new Notification("Lakewood Drink", "Drink corp", "2 months ago", R.drawable.eye_icon));
            return notifications;

        }
        else if(notificationType == "sent"){

            List<Notification> notifications = new ArrayList<Notification>();

            notifications.add(new Notification("Source 1", "Supplier 1", "1 hour ago", 1));
            notifications.add(new Notification("Source 2", "Supplier 2", "3 hours ago", 1));
            notifications.add(new Notification("Source 3", "Supplier 3", "4 days ago", 1));
            notifications.add(new Notification("Source 4", "Supplier 4", "1 week ago", 1));
            notifications.add(new Notification("Source 5", "Supplier 5", "1 month ago", 1));
            return notifications;
        }
        else{
            return null;
        }
    }
}