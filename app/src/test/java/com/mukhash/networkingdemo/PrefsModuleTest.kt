package com.mukhash.networkingdemo

import android.app.Application
import com.mukhash.networkingdemo.di.PrefsModule
import com.mukhash.networkingdemo.util.SharedPreferenceHelper

class PrefsModuleTest(val mockPrefs: SharedPreferenceHelper): PrefsModule() {

    override fun providePrefs(app: Application): SharedPreferenceHelper {
        return mockPrefs
    }

}