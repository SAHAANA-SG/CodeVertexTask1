package com.example.codevertextask1.ui.theme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.codevertextask1.R

class MyBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, pl: Intent?) {
        val mp = MediaPlayer.create(p0, R.raw.victory)
        mp.start()
    }
}