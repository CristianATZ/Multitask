package net.cristianzvl.multitask.Notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import net.cristianzvl.multitask.Notifications.AlarmNotification.Companion.NOTIFICACION_ID
import net.cristianzvl.multitask.Room.WorksData
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("ScheduleExactAlarm")
fun workAlarm(
    context: Context,
    expiration: LocalTime,
    item: WorksData
){
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val exact = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, expiration.hour)
        set(Calendar.MINUTE, expiration.minute)
    }

    val exactIntent = Intent(context, AlarmNotification::class.java)
        .putExtra("title", item.titlework)
        .putExtra("desc", item.descwork)
        .putExtra("time", expiration.format(DateTimeFormatter.ofPattern("HH:mm")))


    val exactPendingIntent = PendingIntent.getBroadcast(
        context,
        NOTIFICACION_ID,
        exactIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        exact.timeInMillis,
        1000 * 60,
        exactPendingIntent
    )
}