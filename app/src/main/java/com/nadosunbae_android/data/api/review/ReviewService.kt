package com.nadosunbae_android.data.api.review

import com.nadosunbae_android.data.model.request.review.RequestPostReview
import com.nadosunbae_android.data.model.request.review.RequestReviewListData
import com.nadosunbae_android.data.model.response.like.ResponsePostLike
import com.nadosunbae_android.data.model.response.review.*
import retrofit2.Call
import retrofit2.http.*

interface ReviewService {

    @POST("review-post/list")
    fun getReviewList(
        @Query("sort") sort: String = "recent",
        @Body requestBody: RequestReviewListData
    ) : Call<ResponseReviewListData>

    @GET("major/{majorId}")
    fun getMajorInfo(
        @Path("majorId") majorId: Int
    ) : Call<ResponseMajorData>

    @GET("review-post/{postId}")
    fun getReviewDetail(
        @Path("postId") postId: Int
    ) : Call<ResponseReviewDetailData>

    @GET("review-post/background-image/list")
    fun getBackgroundImageList() : Call<ResponseBackgroundImageListData>

    @POST("review-post")
    fun postReview(
        @Body requestBody: RequestPostReview
    ) : Call<ResponsePostReview>

}