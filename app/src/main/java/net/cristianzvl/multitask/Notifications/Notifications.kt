package net.cristianzvl.multitask.Notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import net.cristianzvl.multitask.Notifications.AlarmNotification.Companion.NOTIFICACION_ID
import java.util.Calendar

@SuppressLint("ScheduleExactAlarm")
fun workAlarm(
    context: Context,
    title: String,
    longDesc: String,
    time: Long = 10000
){
    val intent = Intent(context, AlarmNotification::class.java)


    val pendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        time,
        pendingIntent
    )
}