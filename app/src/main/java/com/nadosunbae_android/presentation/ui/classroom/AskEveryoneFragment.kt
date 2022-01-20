package com.nadosunbae_android.presentation.ui.classroom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.data.model.ui.classroom.ClassRoomData
import com.nadosunbae_android.databinding.FragmentAskEveryoneBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomAskEveryoneAdapter
import com.nadosunbae_android.presentation.ui.main.viewmodel.MainViewModel
import com.nadosunbae_android.util.Mapper
import okhttp3.internal.http.toHttpDateOrNull


class AskEveryoneFragment : BaseFragment<FragmentAskEveryoneBinding>(R.layout.fragment_ask_everyone) {
    private val mainViewModel: MainViewModel by activityViewModels{
        object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel() as T
            }
        }
    }

    private lateinit var classRoomAskEveryoneAdapter : ClassRoomAskEveryoneAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeClassRoom()
        initAskEveryone()
        goQuestionWrite()
    }


    //과방탭 메인으로 이동
    private fun changeClassRoom(){
        binding.imgAskEveroneTitle.setOnClickListener {
            mainViewModel.classRoomFragmentNum.value = 1
        }
    }

    //리사이클러뷰
    private fun initAskEveryone(){
        mainViewModel.getClassRoomMain(2,5)
        classRoomAskEveryoneAdapter = ClassRoomAskEveryoneAdapter()
        binding.rcAskEveryone.adapter = classRoomAskEveryoneAdapter
        mainViewModel.classRoomMain.observe(viewLifecycleOwner){
            classRoomAskEveryoneAdapter.setAskEveryone(Mapper.mapperToQuestionMain(it) as MutableList<ClassRoomData>)
        }

    }

    //전체 질문 작성으로 이동
    private fun goQuestionWrite(){
        binding.btnGoQuestionWrite.setOnClickListener {
            val intent = Intent(requireActivity(), QuestionWriteActivity::class.java)
            startActivity(intent)
        }
    }


}