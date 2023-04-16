package com.vnvj0033.bookplus

import android.app.Application
import com.vnvj0033.bookplus.service.fcm.FirebaseMessagingServiceModel
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        val fcmModel = EntryPoints.get(applicationContext, AppEntryPoints::class.java)
            .firebaseMessagingServiceModel

        GlobalScope.launch(CoroutineExceptionHandler{ _, t ->
            t.printStackTrace()
        }) {
            fcmModel.updateToken()
        }
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface AppEntryPoints {
        val firebaseMessagingServiceModel: FirebaseMessagingServiceModel
    }
}