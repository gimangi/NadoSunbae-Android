package com.nadosunbae_android.app.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadosunbae_android.app.presentation.base.LoadableViewModel
import com.nadosunbae_android.app.util.ResultWrapper
import com.nadosunbae_android.app.util.safeApiCall
import com.nadosunbae_android.domain.model.mypage.*
import com.nadosunbae_android.domain.model.sign.SignInData
import com.nadosunbae_android.domain.usecase.mypage.*
import com.nadosunbae_android.domain.usecase.review.GetMajorInfoDataUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MyPageViewModel(
    val getMyPageMyInfoUseCase: GetMyPageMyInfoUseCase,
    val getMyPageQuestionUseCase: GetMyPageQuestionUseCase,
    val putMyPageModifyUseCase: PutMyPageModifyUseCase,
    val getMyPagePostUseCase: GetMyPagePostUseCase,
    val getMyPageReplyUseCase: GetMyPageReplyUseCase,
    val getMyPageVersionUseCase: GetMyPageVersionUseCase,
    val postMyPageLogOutUseCase : PostMyPageLogOutUseCase,
    val getMyPageLikeQuestionUseCase: GetMyPageLikeQuestionUseCase,
    val getMyPageLikeReviewUseCase: GetMyPageLikeReviewUseCase,
    val getMyPageReviewUseCase: GetMyPageReviewUseCase,
    val getMyPageBlockUseCase: GetMyPageBlockUseCase,
    val postMyPageBlockUpdateUseCase: PostMyPageBlockUpdateUseCase,
    val postMyPageResetPasswordUseCase: PostMyPageResetPasswordUseCase,
    val deleteMyPageQuitUseCase: DeleteMyPageQuitUseCase,
    val getMajorInfoDataUseCase: GetMajorInfoDataUseCase

    ) : ViewModel(), LoadableViewModel {

    // 로그인 response 데이터
    private val _signData = MutableLiveData<SignInData.User>()
    val signData: LiveData<SignInData.User>
        get() = _signData

    override val onLoadingEnd = MutableLiveData<Boolean>(false)


    //로그인 status 체크
    var myPagePostStatus = MutableLiveData<Int>()

    //유저 아이디
    var userId = MutableLiveData<Int>()

    val personalQuestion = MutableLiveData<MyPageQuestionData>()
    val personalInfo = MutableLiveData<MyPageMyInfo>()
    val modifyInfo = MutableLiveData<MyPageModifyData>()
    val postByMe = MutableLiveData<MyPagePostData>()
    val replyByMe = MutableLiveData<MyPageReplyData>()
    val versionInfo = MutableLiveData<MyPageVersionData>()
    val logOut: MutableLiveData<MyPageLogOutData> = MutableLiveData()
    val likeQuestion = MutableLiveData<MyPageLikeQuestionData>()
    val likeReview = MutableLiveData<MyPageLikeReviewData>()
    val reviewList = MutableLiveData<MyPageReviewData>()
    val blockList = MutableLiveData<MyPageBlockData>()
    val blockUpdate = MutableLiveData<MyPageBlockUpdateData>()
    val resetPassword : MutableLiveData<MyPageResetPasswordData> = MutableLiveData()
   //val quitInfo : MutableLiveData<MyPageQuitData> = MutableLiveData()


    //아이템 position
    var itemPosition = MutableLiveData<Int>()

    private var _myPagePersonal = MutableLiveData<MyPageMyInfo>()
    val myPagePersonal : LiveData<MyPageMyInfo>
    get() = _myPagePersonal

    private var _status = MutableLiveData<Int?>()
    val status: LiveData<Int?> = _status

    private var _quitInfo = MutableLiveData<MyPageQuitData?>()
    val quitInfo : LiveData<MyPageQuitData?>
    get() = _quitInfo


    private var _questionPostId = MutableLiveData<MyPageLikeQuestionData.Data.LikePost>()
    val questionPostId : LiveData<MyPageLikeQuestionData.Data.LikePost>
    get() = _questionPostId

    private val _firstMajorName = MutableLiveData<String>()
    val firstMajorName: LiveData<String>
        get() = _firstMajorName

    private val _secondMajorName = MutableLiveData<String>()
    val secondMajorName: LiveData<String>
        get() = _secondMajorName

    private val _editFinish = MutableLiveData<Boolean>()
    val editFinish: LiveData<Boolean>
        get() = _editFinish

    //토스트
    var reportStatusInfo = MutableLiveData<Int>()

    //마이페이지 버전정보
    fun getMyPageVersion() {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageVersionUseCase() }
                .onSuccess {
                    versionInfo.value = it
                    Timber.d("mypageVersion : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypageVersion : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    //마이페이지 1:1 질문
    fun getMyPageQuestion(userId: Int, sort: String = "recent") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageQuestionUseCase(userId, sort) }
                .onSuccess {
                    personalQuestion.value = it
                    Timber.d("mypageQuestion : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypageQuestion : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //마이페이지 내가 쓴 학과 후기글
    fun getMyPageReview(userId: Int) {
        viewModelScope.launch {
            when (val postSignIn = safeApiCall(Dispatchers.IO) { getMyPageReviewUseCase(userId) }) {
                is ResultWrapper.Success -> {
                    reviewList.value = postSignIn.data!!
                    myPagePostStatus.value = 200
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("SignIn : 네트워크 실패")
                    myPagePostStatus.value = 500
                }
                is ResultWrapper.GenericError -> {
                    myPagePostStatus.value = postSignIn.code ?: 204
                }
            }
                .also {
                    onLoadingEnd.value = true
                }
            Timber.d("signInStatus: ${myPagePostStatus.value.toString()}")
        }
    }

    //마이페이지 좋아요 리스트 (Review)
    fun getMyPageLikeReview(type: String = "review") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageLikeReviewUseCase(type) }
                .onSuccess {
                    likeReview.value = it
                    Timber.d("mypageLikeReview : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypageLikeReview : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 좋아요 리스트 (Question)
    fun getMyPageLikeQuestion(type: String = "question") {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageLikeQuestionUseCase(type) }
                .onSuccess {
                    likeQuestion.value = it
                    Timber.d("mypageLikeQuestion : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypageLikeQuestion : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 내가 쓴 글
    fun getMyPagePost(type: String) {
        viewModelScope.launch {
            kotlin.runCatching { getMyPagePostUseCase(type) }
                .onSuccess {
                    postByMe.value = it
                    Timber.d("mypagePost : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypagePost : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //마이페이지 내가 쓴 답글
    fun getMyPageReply(postTypeId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageReplyUseCase(postTypeId) }
                .onSuccess {
                    replyByMe.value = it
                    Timber.d("mypageReply : 서버 통신 성공")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("mypageReply : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    //마이페이지 개인 정보 서버통신
    fun getPersonalInfo(userId: Int){
        viewModelScope.launch {
            kotlin.runCatching { getMyPageMyInfoUseCase(userId) }
                .onSuccess {
                    personalInfo.value = it
                    Timber.d("myPageInfo : 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("myPageInfo : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 내 정보 수정 서버통신
    fun putMyPageModify(myPageModifyItem: MyPageModifyItem) {
        viewModelScope.launch {
            kotlin.runCatching { putMyPageModifyUseCase(myPageModifyItem) }
                .onSuccess {
                    modifyInfo.value = it
                    Timber.d("MyPageModify : 서버 통신 완료")
               }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("MyPageModify : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 차단 & 차단 해제
    fun postMyPageBlockUpdate(myPageBlockUpdateItem: MyPageBlockUpdateItem) {
        viewModelScope.launch {
            kotlin.runCatching { postMyPageBlockUpdateUseCase(myPageBlockUpdateItem) }
                .onSuccess {
                    blockUpdate.value = it
                    Timber.d("MyPageBlockUpdate : 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("MyPageBlockUpdate : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 로그아웃
    fun postMyPageLogOut() {
        viewModelScope.launch {
            kotlin.runCatching { postMyPageLogOutUseCase() }
                .onSuccess {
                    logOut.value = it
                    Timber.d("MyPageLogOut : 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("MyPageLogOut : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }


    //마이페이지 비밀번호 재설정
    fun postMyPageRestPassword(myPageResetPasswordItem: MyPageResetPasswordItem) {
        viewModelScope.launch {

            when (safeApiCall(Dispatchers.IO) {postMyPageResetPasswordUseCase(myPageResetPasswordItem)}) {
                is ResultWrapper.Success -> resetPassword.value =
                    MyPageResetPasswordData("",200, true)
                is ResultWrapper.NetworkError -> {
                    Timber.d("MyPageResetPw : 네트워크 실패")
                    resetPassword.value = MyPageResetPasswordData("", 500, false)
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("MyPageResetPw : 존재하지 않는 이메일")
                    resetPassword.value = MyPageResetPasswordData("", 400, false)
                }
            }
        }
    }

    //마이페이지 차단된 사용자 목록 조회
    fun getMyPageBlock() {
        viewModelScope.launch {
            kotlin.runCatching { getMyPageBlockUseCase() }
                .onSuccess {
                    blockList.value = it
                    Timber.d("MyPageBlock : 서버 통신 완료")
                }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("MyPageBlock : 서버 통신 실패")
                }
                .also {
                    onLoadingEnd.value = true
                }
        }
    }

    //마이페이지 탈퇴
    fun deleteMyPageQuit(myPageQuitItem: MyPageQuitItem) {
        viewModelScope.launch {
            when(val quitData = safeApiCall(Dispatchers.IO){ deleteMyPageQuitUseCase(myPageQuitItem) }) {
                is ResultWrapper.Success -> {
                    _quitInfo.value = quitInfo.value?.let { MyPageQuitData(it.data, 200, true) }
                    reportStatusInfo.value = 200
                }
                is ResultWrapper.NetworkError -> {
                    Timber.d("MyPageQuit : 네트워크 실패")
                }
                is ResultWrapper.GenericError -> {
                    Timber.d("MyPageResetPw : 존재하지 않는 비밀번호")
                    reportStatusInfo.value = quitData.code ?: 0
                }
            }
                .also {
                    onLoadingEnd.value = true
                }

        }
    }

    // 학과 이름
    fun getMajorName(isFirstMajor: Boolean, majorId: Int) {
        viewModelScope.launch {

            runBlocking {
                kotlin.runCatching {


                    runCatching { getMajorInfoDataUseCase(majorId) }
                        .onSuccess {
                            if (isFirstMajor)
                                _firstMajorName.value = it.majorName
                            else
                                _secondMajorName.value = it.majorName
                            Timber.d("MyPageGetMajor : 서버 통신 성공")
                        }
                        .onFailure {
                            Timber.d("MyPageGetMajor : 서버 통신 실패")
                            it.printStackTrace()
                        }
                }
            }
        }

    }

    // 저장 완료
    fun editFinish() {
        _editFinish.value = true

    }
}

