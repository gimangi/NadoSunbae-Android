package com.nadosunbae_android.app.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isVisible
import com.nadosunbae_android.app.R
import com.nadosunbae_android.app.databinding.ActivityQuestionWriteBinding
import com.nadosunbae_android.app.presentation.base.BaseActivity
import com.nadosunbae_android.app.presentation.ui.classroom.viewmodel.QuestionWriteViewModel
import com.nadosunbae_android.app.util.CustomDialog
import com.nadosunbae_android.domain.model.classroom.ClassRoomPostWriteItem
import com.nadosunbae_android.domain.model.classroom.WriteUpdateItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class QuestionWriteActivity :
    BaseActivity<ActivityQuestionWriteBinding>(R.layout.activity_question_write) {
    private lateinit var dialog: CustomDialog
    private val questionWriteViewModel: QuestionWriteViewModel by viewModel()
    var title = false
    var content = false

    //작성 수정 구분( 0 -> 작성, 1 -> 수정)
    private var division : Int = write

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        writeTitle()
        writeContent()
        divisionWrite()
        completeBtnCheck()
        cancelWrite()
        titleChange()
        initUpdateDetail()

    }


    //제목 입력했을 때
    private fun writeTitle() {
        binding.etQuestionWriteAllTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.title.value = s.toString().isNotEmpty()
                questionWriteViewModel.titleData.value = s.toString()
                binding.viewQuestionWriteAllTitleLineGray.isVisible = false
                binding.viewQuestionWriteAllTitleLineBlack.isVisible = true
            }
        })
    }

    // 본문 입력했을 때
    private fun writeContent() {
        binding.etQuestionWriteAllContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.etQuestionWriteAllTitle.isSelected = false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                questionWriteViewModel.content.value = s.toString().isNotEmpty()
                questionWriteViewModel.contentData.value = s.toString()
                binding.viewQuestionWriteAllTitleLineBlack.isVisible = false
                binding.viewQuestionWriteAllTitleLineGray.isVisible = true
            }
        })
    }


    //완료 버튼 활성화
    private fun completeBtnCheck() {
        questionWriteViewModel.completeBtn.observe(this) {
            binding.textQuestionWriteAllBtn.isSelected = it
            //완료 버튼 누를 때
            if (it) {
                binding.textQuestionWriteAllBtn.setOnClickListener {
                    initCompleteDialog()
                }
            }
        }
    }

    //제목 변경
    private fun titleChange() {
        val title = intent.getStringExtra("title")
        val hintContent = intent.getStringExtra("hintContent")
        binding.textQuestionWriteAllTitle.text = title.toString()
        binding.etQuestionWriteAllContent.hint = hintContent.toString()
    }


    //종료 버튼 클릭
    private fun cancelWrite() {
        binding.imgQuestionWriteAllCancle.setOnClickListener {
            initCancelDialog()
        }
    }

    //작성취소 다이얼로그 띄우기
    private fun initCancelDialog() {
        dialog = CustomDialog(this)
        dialog.writeCancelDialog(R.layout.dialog_question_write_cancel, division)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 1) finish()
            }
        })
    }

    //작성 완료 다이얼로그 띄우기
    private fun initCompleteDialog() {
        dialog = CustomDialog(this)
        dialog.writeCompleteDialog(R.layout.dialog_question_write_complete)
        dialog.setOnClickedListener(object : CustomDialog.ButtonClickListener {
            override fun onClicked(num: Int) {
                if (num == 2) {
                    if(division == write){
                        selectQuestionWrite()
                    }else{
                        updateWrite()
                    }
                }
            }
        })
    }

    //작성 서버통신 분기처리( 2-> 정보, 3-> 질문(전체), 4 -> 질문(1:1)
    private fun selectQuestionWrite() {
        val postTypeId = intent.getIntExtra("postTypeId", 1)
        val answerId = intent.getIntExtra("userId", 1)
        val majorId = intent.getIntExtra("majorId", 5)


        when (postTypeId) {
            2 -> questionWrite(majorId, null, 2)
            3 -> questionWrite(majorId, null, 3)
            4 -> questionWrite(majorId, answerId, 4)
        }

    }


    //작성 서버통신
    private fun questionWrite(majorId: Int, answerId: Int?, postTypeId: Int) {
        questionWriteViewModel.postClassRoomWrite(
            ClassRoomPostWriteItem(
                majorId, answerId, postTypeId,
                questionWriteViewModel.titleData.value.toString(),
                questionWriteViewModel.contentData.value.toString()
            )
        )
        questionWriteViewModel.postDataWrite.observe(this) { its ->
            Timber.d("its: ${its.success}")
            if (its.success) {
                finish()
            }
        }
    }

    //수정 서버통신
    private fun updateWrite(){
        val postId = intent.getIntExtra("postId", 0)
        Timber.d("updateWritePostId: $postId")
        Timber.d("updateWrite: ${questionWriteViewModel.title.value} , ${questionWriteViewModel.content.value}")
        questionWriteViewModel.putWriteUpdate(postId,
        WriteUpdateItem(
            questionWriteViewModel.titleData.value.toString(),
            questionWriteViewModel.contentData.value.toString()
        ))
        questionWriteViewModel.writeUpdateData.observe(this){
            if(it.success){
                finish()
            }
        }

    }

    //수정시에 서버통신 작성 창
    private fun initUpdateDetail(){
        val title = intent.getStringExtra("writerUpdateTitle")
        val content = intent.getStringExtra("writerUpdateContent")

        binding.etQuestionWriteAllTitle.setText(title)
        binding.etQuestionWriteAllContent.setText(content)
    }

    //수정 작성 구분
    private fun divisionWrite(){
        division = intent.getIntExtra("division", -1)

    }

    companion object{
        const val write = 0
        const val update = 1
    }

    //백버튼
    override fun onBackPressed() {
        super.onBackPressed()
        initCancelDialog()
    }
}