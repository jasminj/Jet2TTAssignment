package com.jet2tt.assignment

import android.content.Context
import com.jet2tt.assignment.di.AppComponent
import com.jet2tt.assignment.di.AppInjector
import com.jet2tt.assignment.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class Jet2TTApp : DaggerApplication() {

    private val component: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }

    fun getContext(): Context {
        return applicationContext
    }

    companion object {
        private var instance: Jet2TTApp? = null

        @JvmName("getAppInstance")
        @JvmStatic
        fun getInstance(): Jet2TTApp? {
            return instance
        }
    }
}