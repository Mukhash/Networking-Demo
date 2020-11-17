package com.mukhash.networkingdemo.di

import com.mukhash.networkingdemo.model.AnimalApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalApiService)
}