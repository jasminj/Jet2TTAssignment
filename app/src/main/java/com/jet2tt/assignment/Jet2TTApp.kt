package com.jet2tt.assignment

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

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        AppInjector.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }
}