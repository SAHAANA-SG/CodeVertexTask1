package com.example.codevertextask1

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.codevertextask1.ui.theme.CodeVertexTask1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeVertexTask1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Pass the context to AlarmScreen
                    AlarmScreen(
                        modifier = Modifier.padding(innerPadding),
                        onSetAlarm = { seconds -> setAlarm(seconds) },
                        context = this@MainActivity // Passing context
                    )
                }
            }
        }
    }

    private fun setAlarm(seconds: Int) {
        val intent = Intent(applicationContext, myBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            111,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + (seconds * 1000),
            pendingIntent
        )

        Toast.makeText(
            applicationContext,
            "Alarm is set for $seconds seconds",
            Toast.LENGTH_LONG
        ).show()
    }
}

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    onSetAlarm: (Int) -> Unit,
    context: Context // Add context for MediaPlayer
) {
    val timeInput = remember { mutableStateOf("") }
    val errorText = remember { mutableStateOf("") }
    val isAlarmSet = remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.victory) } // Load the sound file

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = timeInput.value,
            onValueChange = { timeInput.value = it },
            label = { Text("Enter time in seconds") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = errorText.value.isNotEmpty(),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (errorText.value.isNotEmpty()) {
            Text(text = errorText.value, color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(
            onClick = {
                val seconds = timeInput.value.toIntOrNull()
                if (seconds != null && seconds > 0) {
                    onSetAlarm(seconds)
                    errorText.value = ""
                    isAlarmSet.value = true

                    // Set a delay to trigger the alarm after specified time
                    Handler(Looper.getMainLooper()).postDelayed({
                        mediaPlayer.start() // Play the alarm sound
                        isAlarmSet.value = false // Reset the alarm state after playing
                    }, seconds * 1000L)
                } else {
                    errorText.value = "Please enter a valid number greater than 0"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Set Alarm")
        }

        if (isAlarmSet.value) {
            Text(text = "Alarm is set for ${timeInput.value} seconds", color = androidx.compose.ui.graphics.Color.Green)
        }
    }
}
