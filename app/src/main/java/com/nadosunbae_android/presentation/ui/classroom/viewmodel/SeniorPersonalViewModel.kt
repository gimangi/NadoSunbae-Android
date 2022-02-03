package com.nadosunbae_android.presentation.ui.classroom.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.model.classroom.SeniorPersonalData
import com.nadosunbae_android.model.response.classroom.ResponseSeniorPersonalData
import com.nadosunbae_android.model.response.classroom.ResponseSeniorQuestionData
import com.nadosunbae_android.repository.classroom.ClassRoomRepository
import com.nadosunbae_android.repositoryimpl.classroom.ClassRoomRepository
import com.nadosunbae_android.usecase.classroom.GetSeniorPersonalDataUseCase
import kotlinx.coroutines.launch

class SeniorPersonalViewModel(
    val getSeniorPersonalDataUseCase: GetSeniorPersonalDataUseCase
) : ViewModel() {

    //선배 개인페이지
    private val _seniorPersonal = MutableLiveData<SeniorPersonalData>()
    val seniorPersonal : LiveData<SeniorPersonalData>
        get() = _seniorPersonal

    //선배 1:1 질문
    private val _seniorQuestion = MutableLiveData<ResponseSeniorQuestionData>()
    val seniorQuestion : LiveData<ResponseSeniorQuestionData>
        get() = _seniorQuestion

    //선배 userId
    var userId = MutableLiveData<Int>()

    //선배 개인페이지 정보 서버통신
    fun getSeniorPersonal(userId : Int){
        viewModelScope.launch {
            runCatching { getSeniorPersonalDataUseCase(userId) }
                .onSuccess {
                    _seniorPersonal.value = it
                    Log.d("seniorPersonal", "선배 개인페이지 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("seniorPersonal", "선배 개인페이지 서버 통신 실패")
                }
        }
    }

    //선배 1:1 질문 리스트
    fun getSeniorQuestionList(userId : Int, sort : String){
        classRoomRepository.getSeniorQuestionList(userId,sort,
        onResponse = {
            if(it.isSuccessful){
                _seniorQuestion.value = it.body()
                Log.d("seniorQuestion", "선배 1:1질문 서버 통신 완료")
            }
        },
        onFailure = {
            it.printStackTrace()
            Log.d("seniorQuestion", "선배 1:1질문 서버 통신 실패")
        })

    }
}