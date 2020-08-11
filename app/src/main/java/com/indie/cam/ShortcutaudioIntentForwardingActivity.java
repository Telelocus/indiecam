package com.indie.cam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

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
public class ShortcutaudioIntentForwardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        String text = UUID.randomUUID().toString().replace("-", "");
        // Build options object for joining the conference. The SDK will merge the default
        // one we set earlier and this one when joining.
        URL serveraudioURL;
        try {
            serveraudioURL = new URL("https://go.indie.cam/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
        JitsiMeetConferenceOptions options
                = new JitsiMeetConferenceOptions.Builder()
                .setServerURL(serveraudioURL)
                .setRoom(text)
                .setVideoMuted(true)
                .setWelcomePageEnabled(false)
                .setFeatureFlag("invite.enabled", true)
                .setFeatureFlag("meeting-password.enabled", true)
                .setFeatureFlag("live-streaming.enabled", false)
                .setFeatureFlag("calendar.enabled", false)
                .setFeatureFlag("add-people.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .setFeatureFlag("pip.enabled", true)
                .setFeatureFlag("recording.enabled", false)
                .setFeatureFlag("close-captions.enabled", false)
                .setFeatureFlag("meeting-name.enabled", false)
                .build();
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(this, options);
        Vibrator y = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        assert y != null;
        y.vibrate(500);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    private void handleIntent(@NonNull Intent intent) {
        intent.setClass(this, JitsiMeetActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),
                "Creating audio only room..",
                Toast.LENGTH_LONG)
                .show();
        finish();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }
}