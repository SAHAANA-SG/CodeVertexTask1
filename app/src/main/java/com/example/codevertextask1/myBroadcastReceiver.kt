package com.example.codevertextask1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Display the message when the alarm goes off
        val message = intent.getStringExtra("ALARM_MESSAGE") ?: "It's time!"

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

        // Play the alarm sound
        val mediaPlayer = MediaPlayer.create(context, R.raw.victory) // Ensure you have this sound file
        mediaPlayer.start()
    }
}

class myBroadcastReceiver {

}
