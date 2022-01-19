package com.nadosunbae_android.data.datasource.remote.review

import com.nadosunbae_android.data.api.ApiService
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.review.ResponseMajorData
import com.nadosunbae_android.data.model.response.review.ResponseReviewDetailData
import com.nadosunbae_android.data.model.response.review.ResponseReviewListData
import com.nadosunbae_android.util.enqueueUtil
import retrofit2.Response

class ReviewDataSourceImpl() : ReviewDataSource {

    override fun getReviewList(
        sort: String,
        body: RequestReviewListData,
        onResponse: (Response<ResponseReviewListData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getReviewList(sort, body).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getMajorInfo(
        majorId: Int,
        onResponse: (Response<ResponseMajorData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getMajorInfo(majorId).enqueueUtil(
            onResponse, onFailure
        )
    }

    override fun getReviewDetail(
        postId: Int,
        onResponse: (Response<ResponseReviewDetailData>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        ApiService.reviewService.getReviewDetail(postId).enqueueUtil(
            onResponse, onFailure
        )
    }

}