package com.nadosunbae_android.model.sign

//회원가입 ResponseData
data class SignUpItem(
    val success : Boolean,
    val userId: Int,
    val accesstoken: String
)