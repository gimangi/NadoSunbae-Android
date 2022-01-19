package com.nadosunbae_android.data.repository.notification

import com.nadosunbae_android.data.model.response.notification.ResponseNotificationListData
import retrofit2.Response

interface NotificationRepository {

    // 전체 알림 리스트 조회
    fun getNotification(
        receiverId : Int,
        onResponse: (Response<ResponseNotificationListData>) -> Unit,
        onFailure : (Throwable) -> Unit)
}