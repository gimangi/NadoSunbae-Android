package com.nadosunbae_android.data.api.sign

import com.nadosunbae_android.data.model.request.sign.RequestSignEmail
import com.nadosunbae_android.data.model.request.sign.RequestSignNickname
import com.nadosunbae_android.data.model.response.sign.ResponseFirstDepartment
import com.nadosunbae_android.data.model.response.sign.ResponseSignEmail
import com.nadosunbae_android.data.model.response.sign.ResponseSignNickname
import retrofit2.Call
import retrofit2.http.*

interface SignService {
    @POST("auth/duplication-check/nickname")
    fun postSignNickname(
        @Body requestSignNickname: RequestSignNickname
    ): Call<ResponseSignNickname>

    @POST("auth/duplication-check/email")
    fun postSignEmail(
        @Body requestSignEmail: RequestSignEmail
    ) : Call<ResponseSignEmail>

    @GET("major/list/{universityId}")
    fun getFirstDepartment(
        @Path("universityId") universityId :Int,
        @Query("filter") filter : String
    ) : Call<ResponseFirstDepartment>
}