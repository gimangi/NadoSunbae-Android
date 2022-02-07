package com.nadosunbae_android.presentation.ui.sign.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.model.request.sign.RequestSignEmail
import com.nadosunbae_android.model.request.sign.RequestSignIn
import com.nadosunbae_android.model.request.sign.RequestSignNickname
import com.nadosunbae_android.model.request.sign.RequestSignUp
import com.nadosunbae_android.model.response.sign.ResponseFirstDepartment
import com.nadosunbae_android.model.response.sign.ResponseSignIn
import com.nadosunbae_android.model.response.sign.ResponseSignUp
import com.nadosunbae_android.model.sign.EmailDuplicationData
import com.nadosunbae_android.model.sign.NicknameDuplicationData
import com.nadosunbae_android.model.sign.SignInData
import com.nadosunbae_android.repositoryimpl.sign.SignRepository
import com.nadosunbae_android.usecase.classroom.*
import kotlinx.coroutines.launch

class SignUpBasicInfoViewModel(
    val getFirstDepartmentUseCase: GetFirstDepartmentUseCase,
    val postSignEmailUseCase: PostSignEmailUseCase,
    val postSignInUseCase: PostSignInUseCase,
    val postSignNicknameUseCase: PostSignNicknameUseCase,
    val postSignUpUseCase: PostSignUpUseCase

) : ViewModel() {
//    val signRepository: SignRepository = SignRepositoryImpl()


    //닉네임 중복 체크 변수
    var nickNameDuplication = MutableLiveData<Boolean>()

    //이메일 중복 체크 변수
    var emailDuplication = MutableLiveData<Boolean>()

    //회원가입
    var signUp = MutableLiveData<ResponseSignUp>()

    //회원가입 request
    val requestSignUp = RequestSignUp("", "", "", 0, 0, "", 0, "")

    //로그인시 필요한 값 -> email, password, deviceToken
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var deviceToken = MutableLiveData<String>()

    //로그인
    val signIn: MutableLiveData<ResponseSignIn> = MutableLiveData()


    //닉네임
    var nickName = MutableLiveData<String>()

    //제 1전공
    val firstDepartment = MutableLiveData<ResponseFirstDepartment>()

    //제 2전공
    val secondDepartment = MutableLiveData<ResponseFirstDepartment>()


    //닉네임 중복 체크
    fun nickNameDuplication(nicknameDuplicationData: NicknameDuplicationData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignNicknameUseCase(nicknameDuplicationData) }
                .onSuccess {
                    nickName.value = it.toString()
                    Log.d("nickNameDuplication", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("nickNameDuplication", "서버 통신 실패")
                }
        }
    }


    //이메일 중복 체크
    fun emailDuplication(emailDuplicationData: EmailDuplicationData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignEmailUseCase(emailDuplicationData) }
                .onSuccess {
                    email.value = it.toString()
                    Log.d("emailDuplication", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("emailDuplication", "서버 통신 실패")
                }
        }
    }

    //로그인
    fun signIn(signInData: SignInData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignInUseCase(signInData) }
                .onSuccess {
                    //받아오는 부분 -> activity에서 email, pw, devicetoken 선언

                    Log.d("SignIn", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("SignIn", "서버 통신 실패")
                }
        }
    }

    //회원가입
    fun signUp(signUpData: SignInData) {
        viewModelScope.launch {
            kotlin.runCatching { postSignUpUseCase(signUpData) }
                .onSuccess {
                    signUp.value =
                    Log.d("SignUp", "서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Log.d("SignUp", "서버 통신 실패")
                }
        }
    }


//    //본 전공 선택
//    fun getFirstDepartment(universityId: Int, filter: String) {
//        signRepository.getFirstDepartment(universityId, filter, {
//            //onResponse
//            if (it.isSuccessful) {
//                firstDepartment.value = it.body()
//                Log.d("firstDepartment", "서버 통신 성공")
//            }
//        }) {
//            //onFailure
//            it.printStackTrace()
//            Log.d("firstDepartment", "서버 통신 실패")
//        }
//    }
//
//    // 제 2전공 선택
//    fun getSecondDepartment(universityId: Int, filter: String) {
//        signRepository.getFirstDepartment(universityId, filter, {
//            //onResponse
//            if (it.isSuccessful) {
//                secondDepartment.value = it.body()
//                Log.d("secondDepartment", "서버 통신 성공")
//            }
//        }) {
//            //onFailure
//            it.printStackTrace()
//            Log.d("secondDepartment", "서버 통신 실패")
//        }
//    }
}