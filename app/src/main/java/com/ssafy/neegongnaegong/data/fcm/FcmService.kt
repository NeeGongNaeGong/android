package com.ssafy.neegongnaegong.data.fcm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ssafy.neegongnaegong.R
import com.ssafy.neegongnaegong.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FcmService : FirebaseMessagingService() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                userRepository.updateFcmToken(token)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this@FcmService,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.w("FcmService", "알림 권한이 없어 알림을 표시하지 않음.")
                return
            }
        }

        createNotificationChannel()

        val title = remoteMessage.data["title"] ?: remoteMessage.notification?.title ?: "알림 제목 없음"
        val body = remoteMessage.data["body"] ?: remoteMessage.notification?.body ?: "알림 내용 없음"
        val imgUrl = remoteMessage.data["profileImg"] ?: ""
        val studyGroupId = remoteMessage.data["studyGroupId"] ?: "-1"
        val id = remoteMessage.hashCode()

        val intent =
            Intent(Intent.ACTION_VIEW, remoteMessage.data["deepLink"]?.toUri()).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        CoroutineScope(Dispatchers.IO).launch {
            val bitmap: Bitmap =
                try {
                    Glide.with(this@FcmService)
                        .asBitmap() // 비트맵으로 받도록 설정
                        .load(imgUrl) // 이미지 URL 로드
                        .override(ICON_SIZE, ICON_SIZE) // ✅ Downsampling! 이미지 크기 재조정
                        .submit().get()
                } catch (e: Exception) {
                    Glide.with(this@FcmService)
                        .asBitmap() // 비트맵으로 받도록 설정
                        .load(R.drawable.img_main_character) // 이미지 URL 로드
                        .override(ICON_SIZE, ICON_SIZE) // ✅ Downsampling! 이미지 크기 재조정
                        .submit().get()
                }

            // 아이콘 확장 눌렀을 때 나올 아이콘
            // 정확히는 addMessage로 Message 넣을 때 누가 작성한건지 Person객체로 넣을 때 사용할 것
            val sender =
                Person.Builder()
                    // setName의 이름을 ""로 공백으로 비웠을 때는 별다른 내용이 뜨지 않음
                    // ShortCut의 label 설정이 필수이기 때문에 Person에 Name에 값을 주면 Shortcut의 Label과 Name 둘 다 굵게 표시되서 미관상 안좋음
                    .setName("")
                    .setIcon(IconCompat.createWithBitmap(bitmap))
                    .build()

            /**
             * 들어오는 메세지에서 나와 상대를 구분하기 위해 MessagingStyle에서 Person 객체를 생성자로 넣음
             * key를 우선으로 구분하기 때문에 addMessage에 넣는 Person객체에는 setKey를 넣지 않았다면 null이기 때문에
             * setKey를 비교할 때 나는 Key 값을 가지고 있는데, addMessage의 객체는 key가 없기 때문에 절대 나로 판단하지 않음
             */
            val default =
                Person.Builder()
                    .setKey(System.currentTimeMillis().toString())
                    // 알림 뜰 때 Bold 처리돼서 보일 부분
                    .setName(title)
                    .build()

            // 메시지 내용을 정의
            val message =
                NotificationCompat.MessagingStyle(default).also {
//                GroupConversation을 true로 설정하는 순간 확장 버튼을 눌렀을 때 좌측의 아이콘이 조그만해지고,
//                message의 sender의 아이콘이 body의 왼쪽에 아이콘으로 내려옴
//                shorcut에 Icon이 있을 경우에는 sender의 아이콘은 나오지 않고, ShortCut의 Icon으로 설정됨
//                디스코드처럼 채널별로 확장했을 때 나열하고 싶으면 설정해야 함
//                this.setGroupConversation(true)
                    it.setGroupConversation(false)
                    it.addMessage(
                        NotificationCompat.MessagingStyle.Message(
                            body,
                            System.currentTimeMillis(), // 메시지 수신 시간
                            sender, // 메시지를 보낸 사람
                        ),
                    )
                }

            val shortcut =
                ShortcutInfoCompat.Builder(this@FcmService, studyGroupId)
                    // 알림창에서 Bold로 Title로 나오는 내용
                    .setShortLabel(title)
                    .setIcon(IconCompat.createWithAdaptiveBitmap(bitmap))
                    // 앱을 길게 눌러서 바로가기를 눌렀을 때 실행되는 것
                    .setIntent(intent)
                    .setLongLived(true)
                    .build()
            ShortcutManagerCompat.pushDynamicShortcut(this@FcmService, shortcut)

            val notification =
                NotificationCompat.Builder(this@FcmService, CHANNEL_ID)
                    .setSmallIcon(R.drawable.img_main_character)
                    .setLargeIcon(bitmap)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    // 알림 창에 나타난 알림을 클릭했을 때 행동을 설정하는 곳
                    // shorcut에 intent를 넣은 건 앱 아이콘을 길게 눌러서 바로가기를 눌렀을 때 실행하는 것
                    // 알림 창에 보여지는 이름과 이미지는 shortcut의 label과 icon이지만 알림창을 눌렀을 때 동작은 여기에 넣어야 한다.
                    .setContentIntent(pendingIntent)
                    .setStyle(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setShortcutId(studyGroupId)
                    .setAutoCancel(true)
                    .build()

            withContext(Dispatchers.Main.immediate) {
                NotificationManagerCompat.from(this@FcmService).notify(id, notification)
            }
        }
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val CHANNEL_ID = "com.ssafy.NeeGongNaeGong"
        private const val CHANNEL_NAME = "NeeGongNaeGong"

        // 안드로이드의 알림 아이콘의 최대 크기가 xxxhdpi에서 96픽셀로 알려져 있기 때문에 오는 이미지를 96픽젤로 다운스케일링 하기 위한 값
        private const val ICON_SIZE = 96
    }
}
