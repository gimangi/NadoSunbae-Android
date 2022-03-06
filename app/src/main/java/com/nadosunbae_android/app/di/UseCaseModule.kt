package com.nadosunbae_android.app.di

import com.nadosunbae_android.domain.usecase.classroom.*
import com.nadosunbae_android.domain.usecase.like.PostLikeDataUseCase
import com.nadosunbae_android.domain.usecase.main.GetMajorListDataUseCase
import com.nadosunbae_android.domain.usecase.main.GetAppLinkUseCase
import com.nadosunbae_android.domain.usecase.mypage.*
import com.nadosunbae_android.domain.usecase.notification.DeleteNotificationUseCase
import com.nadosunbae_android.domain.usecase.notification.GetNotificationListDataUseCase
import com.nadosunbae_android.domain.usecase.notification.ReadNotificationUseCase
import com.nadosunbae_android.domain.usecase.review.*
import com.nadosunbae_android.domain.usecase.sign.GetSecondDepartmentUseCase
import com.nadosunbae_android.domain.usecase.sign.PostCertificationEmailUseCase
import com.nadosunbae_android.domain.usecase.sign.PostRenewalTokenUseCase
import org.koin.dsl.module

val useCaseModule = module{

    //classRoom
    single {GetClassRoomMainDataUseCase(get())}
    single {GetInformationDetailUseCase(get())}
    single {GetQuestionDetailDataUseCase(get())}
    single {GetQuestionSeniorListDataUseCase(get())}
    single {GetSeniorPersonalDataUseCase(get())}
    single {PostClassRoomWriteUseCase(get())}
    single {PostQuestionCommentWriteUseCase(get())}
    single {GetSeniorDataUseCase(get())}
    single {PutCommentUpdateUseCase(get())}
    single {PutWriteUpdateUseCase(get())}
    single { DeleteCommentDataUseCase(get())}
    single {DeletePostDataUseCase(get())}
    single {PostReportUseCase(get())}

    //Notification
    single {DeleteNotificationUseCase(get())}
    single {GetNotificationListDataUseCase(get())}
    single {ReadNotificationUseCase(get())}

    //sign
    single {GetFirstDepartmentUseCase(get())}
    single {PostSignEmailUseCase(get())}
    single {PostSignInUseCase(get())}
    single {PostSignNicknameUseCase(get())}
    single {PostSignUpUseCase(get())}
    single { GetSecondDepartmentUseCase(get()) }
    single {PostCertificationEmailUseCase(get())}
    single {PostRenewalTokenUseCase(get())}

    // main
    single {GetMajorListDataUseCase(get())}
    single { GetAppLinkUseCase(get()) }

    // review
    single {GetReviewListDataUseCase(get())}
    single {GetReviewDetailDataUseCase(get())}
    single {PostReviewDataUseCase(get())}
    single {PutReviewDataUseCase(get())}
    single {DeleteReviewDataUseCase(get())}
    single {GetBackgroundImageListDataUseCase(get())}
    single {GetMajorInfoDataUseCase(get())}
    single { PostLikeDataUseCase(get()) }

    //mypage
    single {GetMyPageMyInfoUseCase(get())}
    single {GetMyPageQuestionUseCase(get())}
    single {PutMyPageModifyUseCase(get())}
    single {GetMyPagePostUseCase(get())}
    single {GetMyPageReplyUseCase(get())}
    single {GetMyPageVersionUseCase(get())}
    single {PostMyPageLogOutUseCase(get())}
    single {GetMyPageLikeReviewUseCase(get())}
    single {GetMyPageLikeQuestionUseCase(get())}
    single {GetMyPageReviewUseCase(get())}
    single {GetMyPageBlockUseCase(get())}
    single {PostMyPageBlockUpdateUseCase(get())}
    single {PostMyPageResetPasswordUseCase(get())}
    single {DeleteMyPageQuitUseCase(get())}



}