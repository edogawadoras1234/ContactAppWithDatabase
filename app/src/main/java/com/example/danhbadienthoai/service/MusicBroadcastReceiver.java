package com.example.danhbadienthoai.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class MusicBroadcastReceiver extends BroadcastReceiver {
    String TAG = "MusicBreadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.equals(intent.getAction(), Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            switch (state) {
                case 0:
                    //Toast.makeText(context, "Headphone is unplugg", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Headset is unplugged");
                    break;
                case 1:
                    Toast.makeText(context, "Headphone is plugged", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Headset is plugged");
                    break;
                default:
                    Toast.makeText(context, "Headphone", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "I have no idea what the Headphone state is");
            }
        }
    }
}
