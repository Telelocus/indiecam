package com.indie.cam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.jitsi.meet.sdk.JitsiMeetActivity;

/**
 * The function of this activity is to act as a singular task root for the
 * deep-links to the application.
 * It should always be declared in the manifest with
 * launchMode=singleTask.  This activity allows deep links to be
 * handled in a separate task from the app that hosts the link, without
 * forcing the LaunchActivity to be singleTask,
 * which would destroy its activity stack on launch.
 *
 * It also allows us to easily clear the activity stack when exiting
 * the app or restarting.
 */
public class IntentForwardingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(@NonNull Intent intent) {
        intent.setClass(this, JitsiMeetActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        Vibrator y = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        assert y != null;
        y.vibrate(500);
        Toast.makeText(getApplicationContext(),
                "Entering room..",
                Toast.LENGTH_LONG)
                .show();
        finish();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }
}