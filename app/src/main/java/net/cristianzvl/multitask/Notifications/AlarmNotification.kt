package net.cristianzvl.multitask.Notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import net.cristianzvl.multitask.R

class AlarmNotification(): BroadcastReceiver() {
    companion object {
        const val NOTIFICACION_ID = 5
    }

    override fun onReceive(context: Context, intent: Intent?) {
        createNotification(context)
    }

    private fun createNotification(context: Context) {
        val notificacion = NotificationCompat.Builder(context, "CanalTareas")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText("Titulo tarae")
            .setContentText("La tarea NOMBRE caduca a las 17:00")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Descripcion de la tarea")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(NOTIFICACION_ID, notificacion)
    }


}