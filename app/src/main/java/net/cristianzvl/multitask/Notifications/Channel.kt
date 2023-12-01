package net.cristianzvl.multitask.Notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

fun createChannelNotification(idCanal: String, context: Context) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val nombre = "CanalTareas"
        val descripcion = "Canal de notificaciones de tareas"
        val importancia = NotificationManager.IMPORTANCE_DEFAULT
        val canal = NotificationChannel(idCanal, nombre, importancia)
            .apply {
                description = descripcion
            }

        val notificationManager: NotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(canal)
    }
}