package com.mukhash.networkingdemo.di

import com.mukhash.networkingdemo.viewmodel.ListViewVM
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewVM)
}