package com.nadosunbae_android.data.model.response.sign

data class ResponseSignIn(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val accessToken: String,
        val user: User
    ) {
        data class User(
            val email: String,
            val firstMajorId: Int,
            val firstMajorName: String,
            val isReviewed: Boolean,
            val secondMajorId: Int,
            val secondMajorName: String,
            val universityId: Int,
            val userId: Int
        )
    }
}