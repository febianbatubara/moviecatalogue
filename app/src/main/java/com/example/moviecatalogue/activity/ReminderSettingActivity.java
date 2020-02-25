package com.example.moviecatalogue.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.reminder.ReminderReceiver;
import com.google.firebase.messaging.FirebaseMessaging;

public class ReminderSettingActivity extends AppCompatActivity {
    private static final String TAG = "ReminderSettingActivity";

    private ReminderReceiver reminderReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_setting);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.color_primary_gradient));
            actionBar.setElevation(0);
        }

        reminderReceiver = new ReminderReceiver(this);

        final Switch switchRelease = findViewById(R.id.btn_release_reminder);
        SharedPreferences sharedPreferences = getSharedPreferences("saveRelease", MODE_PRIVATE);
        switchRelease.setChecked(sharedPreferences.getBoolean("value1", false));

        switchRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchRelease.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("saveRelease", MODE_PRIVATE).edit();
                    editor.putBoolean("value1", true);
                    editor.apply();
                    switchRelease.setChecked(true);

                    reminderReceiver.setRepeatingAlarm();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("saveRelease", MODE_PRIVATE).edit();
                    editor.putBoolean("value1", false);
                    editor.apply();
                    switchRelease.setChecked(false);

                    reminderReceiver.cancelReminder(getApplicationContext());
                }
            }
        });

        final Switch switchDaily = findViewById(R.id.btn_daily_reminder);
        SharedPreferences sharedPreferences1 = getSharedPreferences("saveDaily", MODE_PRIVATE);
        switchDaily.setChecked(sharedPreferences1.getBoolean("value", true));

        switchDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchDaily.isChecked()){
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    switchDaily.setChecked(true);

                    FirebaseMessaging.getInstance().subscribeToTopic("daily_reminder");
                    String msg = getString(R.string.daily_subscribe);
                    Log.d(TAG, msg);
                    Toast.makeText(ReminderSettingActivity.this, msg, Toast.LENGTH_SHORT).show();

                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    switchDaily.setChecked(false);

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("daily_reminder");
                    String msg = getString(R.string.daily_unsubscribe);
                    Log.d(TAG, msg);
                    Toast.makeText(ReminderSettingActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
