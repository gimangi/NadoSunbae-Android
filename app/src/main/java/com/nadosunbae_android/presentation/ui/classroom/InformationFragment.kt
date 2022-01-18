package com.nadosunbae_android.presentation.ui.classroom

import android.os.Bundle
import android.view.View
import com.nadosunbae_android.R
import com.nadosunbae_android.data.model.response.classroom.ResponseClassRoomMainData
import com.nadosunbae_android.databinding.FragmentInformationBinding
import com.nadosunbae_android.presentation.base.BaseFragment
import com.nadosunbae_android.presentation.ui.classroom.adapter.ClassRoomInfoDetailAdapter


class InformationFragment : BaseFragment<FragmentInformationBinding>(R.layout.fragment_information) {
    private lateinit var classRoomInfoMainAdapter : ClassRoomInfoDetailAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initInfoMain()
    }


   /* private fun initInfoMain(){

        classRoomInfoMainAdapter = ClassRoomInfoDetailAdapter()
        binding.rcClassroomInfo.adapter = classRoomInfoMainAdapter
        classRoomInfoMainAdapter.setQuestionMain(exampleData)

    } */
}