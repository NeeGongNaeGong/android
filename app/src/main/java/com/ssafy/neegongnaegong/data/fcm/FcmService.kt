package com.ssafy.neegongnaegong.data.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.neegongnaegong.R

class FcmService : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "default_channel_id"
        private const val CHANNEL_NAME = "기본 알림 채널"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // TODO : 서버에 토큰 전송
        Log.d("FcmService", "New token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val title = remoteMessage.notification?.title ?: "알림 제목 없음"
        val body = remoteMessage.notification?.body ?: "알림 내용 없음"

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.img_main_character)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                Log.w("FcmService", "알림 권한이 없어 알림을 표시하지 않음.")
                return
            }
        }

        NotificationManagerCompat.from(this).notify(0, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
