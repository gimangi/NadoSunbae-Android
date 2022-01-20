package com.nadosunbae_android.presentation.ui.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadosunbae_android.data.model.response.classroom.ResponseSeniorQuestionData
import com.nadosunbae_android.data.model.response.mypage.ResponseMypageQuestionData
import com.nadosunbae_android.data.repository.mypage.MyPageRepository
import com.nadosunbae_android.data.repository.mypage.MyPageRepositoryImpl

class MyPageViewModel : ViewModel() {
    val myPageRepository: MyPageRepository = MyPageRepositoryImpl()


    val personalQuestion = MutableLiveData<ResponseMypageQuestionData>()

    //마이페이지 1:1 질문 메인 조회
    private val _myPageMain = MutableLiveData<ResponseMypageQuestionData>()
    val myPageMain: LiveData<ResponseMypageQuestionData>
        get() = _myPageMain


    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        myPageRepository.getMyPageQuestion(userId, sort,
            onResponse = {
                if (it.isSuccessful) {
                    personalQuestion.value = it.body()
                    Log.d("MyPageQuestion", "서버 통신 성공")
                }},
            onFailure= {
                it.printStackTrace()
                Log.d("MyPageQuestion", "서버 통신 실패")
            }
        )
    }


}

