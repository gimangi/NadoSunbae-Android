package com.nadosunbae_android.model.response.sign

data class ResponseMajorData(
    val data: List<Data>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val isFirstMajor: Boolean,
        val isSecondMajor: Boolean,
        val majorId: Int,
        val majorName: String
    )
}