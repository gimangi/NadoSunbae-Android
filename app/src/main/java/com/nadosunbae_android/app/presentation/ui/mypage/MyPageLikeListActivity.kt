package com.nadosunbae_android.app.presentation.ui.mypage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityMyPageLikeListBinding
import com.nadosunbae_android.app.di.NadoSunBaeApplication
import com.nadosunbae_android.app.di.NadoSunBaeApplication.Companion.context
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeQuestionAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPageLikeReviewAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.adapter.MyPagePostAdapter
import com.nadosunbae_android.app.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.nadosunbae_android.app.presentation.ui.review.ReviewDetailActivity
import com.nadosunbae_android.app.presentation.ui.review.ReviewGlobals
import com.nadosunbae_android.app.presentation.ui.review.adapter.ReviewListAdapter
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.mypage.MyPageLikeQuestionData
import com.nadosunbae_android.domain.model.mypage.MyPageLikeReviewData
import com.nadosunbae_android.domain.model.mypage.MyPagePostData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageLikeListActivity :
    BaseActivity<ActivityMyPageLikeListBinding>(R.layout.activity_my_page_like_list) {
    private val myPageViewModel: MyPageViewModel by viewModel()
    private lateinit var myPageLikeQuestionAdapter: MyPageLikeQuestionAdapter
    private lateinit var myPageLikeReviewAdapter: MyPageLikeReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeLoadingEnd()
        backBtn()
        initBtn()
        selectOption()
        setClickListener()
    }

    private fun observeLoadingEnd() {
        myPageViewModel.onLoadingEnd.observe(this) {
            dismissLoading()
        }
    }

    private fun backBtn() {
        binding.imgMypageLikeTitle.setOnClickListener {
            finish()
        }
    }

    private fun initBtn() {
        initReviewListAdapter()
        binding.textMypageLikeReview.isSelected = true
        binding.textMypageLikeQuestion.isSelected = false
        binding.textMypageLikeInfo.isSelected = false

        //폰트 설정
        binding.textMypageLikeReview.typeface = ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
        binding.textMypageLikeQuestion.typeface = ResourcesCompat.getFont(context(), R.font.pretendard_regular)
        binding.textMypageLikeInfo.typeface = ResourcesCompat.getFont(context(), R.font.pretendard_regular)
    }

    private fun selectOption() {
        binding.apply {
            textMypageLikeReview.setOnClickListener {
                showLoading()
                initReviewListAdapter()
                textMypageLikeReview.isSelected = true
                textMypageLikeQuestion.isSelected = false
                textMypageLikeInfo.isSelected = false

                //폰트 설정
                textMypageLikeReview.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
                textMypageLikeQuestion.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypageLikeInfo.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
            }

            textMypageLikeQuestion.setOnClickListener {
                showLoading()
                questionPosting()
                textMypageLikeReview.isSelected = false
                textMypageLikeQuestion.isSelected = true
                textMypageLikeInfo.isSelected = false

                //폰트 설정
                textMypageLikeReview.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypageLikeQuestion.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
                textMypageLikeInfo.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
            }

            textMypageLikeInfo.setOnClickListener {
                showLoading()
                infoPosting()
                textMypageLikeReview.isSelected = false
                textMypageLikeQuestion.isSelected = false
                textMypageLikeInfo.isSelected = true

                //폰트 설정
                textMypageLikeReview.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypageLikeQuestion.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_regular)
                textMypageLikeInfo.typeface =
                    ResourcesCompat.getFont(context(), R.font.pretendard_semibold)
            }
        }
    }

    private fun questionPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageLikeQuestion("question")
        myPageLikeQuestionAdapter = MyPageLikeQuestionAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter

        myPageViewModel.likeQuestion.observe(this) {
            myPageLikeQuestionAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }

    private fun infoPosting() {
        showLoading()
        intent.getIntExtra("userId", 0)

        myPageViewModel.getMyPageLikeQuestion("information")
        myPageLikeQuestionAdapter = MyPageLikeQuestionAdapter(2, intent.getIntExtra("userId", 0), 1)
        binding.rvMypageLike.adapter = myPageLikeQuestionAdapter

        myPageViewModel.likeQuestion.observe(this) {
            myPageLikeQuestionAdapter.setQuestionPost((it.data.likePostList) as MutableList<MyPageLikeQuestionData.Data.LikePost>)
        }
    }


    private fun initReviewListAdapter() {
        showLoading()
        intent.getIntExtra("userId", 0)
        myPageViewModel.getMyPageLikeReview("review")
        myPageLikeReviewAdapter = MyPageLikeReviewAdapter()
        binding.rvMypageLike.adapter = myPageLikeReviewAdapter

        myPageViewModel.likeReview.observe(this) {
            myPageLikeReviewAdapter.setReviewListData((it.data.likePostList) as MutableList<MyPageLikeReviewData.Data.LikePost>)
        }
    }


    private fun setClickListener() {
        showLoading()
        // RecyclerView ItemClickListener
        myPageLikeReviewAdapter.setItemClickListener(
            object : MyPageLikeReviewAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {

                    val reviewListData = myPageViewModel.likeReview.value

                    // null check
                    if (reviewListData != null) {
                        // postId Intent로 전달 (후기 상세보기 이동)
                        val intent =
                            Intent(this@MyPageLikeListActivity, ReviewDetailActivity::class.java)
                        val postId = reviewListData.data.likePostList[position].postId
                        intent.putExtra("postId", postId)
                        intent.putExtra("userId", intent.getIntExtra("userId", 0))
                        startActivity(intent)
                    }


                }

            }
        )
    }
}