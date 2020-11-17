package com.mukhash.networkingdemo.di

import android.app.Application
import com.mukhash.networkingdemo.util.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class PrefsModule {

    @Provides
    @Singleton
    open fun providePrefs(app: Application): SharedPreferenceHelper {
        return SharedPreferenceHelper(app)
    }

}