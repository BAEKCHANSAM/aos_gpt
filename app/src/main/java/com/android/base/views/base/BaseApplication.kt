package com.android.base.views.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class BaseApplication : Application() {

    companion object {
        val diskIO: ExecutorService = Executors.newSingleThreadExecutor()

        var instance: BaseApplication? = null
            private set
    }

    abstract val viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    override fun onTerminate() {
        instance = null
        super.onTerminate()
    }
}