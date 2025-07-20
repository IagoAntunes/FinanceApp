package com.iagoaf.appfinancas.src.features.home.presentation.listener

sealed class HomeListener() {

    object Idle : HomeListener()

    object ReleasedCreated : HomeListener()
    object ReleasedDeleted : HomeListener()
    object LoggedOut : HomeListener()

}