package com.indie.cam;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

public class ShareBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        String url = intent.getDataString();
        if (url != null) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.chromeextra) + url);

            Intent chooserIntent = Intent.createChooser(shareIntent, "Share url");
            chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooserIntent);
        }
    }
}