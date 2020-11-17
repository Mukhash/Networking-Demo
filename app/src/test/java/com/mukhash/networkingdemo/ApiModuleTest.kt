package com.mukhash.networkingdemo

import com.mukhash.networkingdemo.di.ApiModule
import com.mukhash.networkingdemo.model.AnimalApiService

class ApiModuleTest(val mockService: AnimalApiService): ApiModule() {

    override fun provideAnimalApiService(): AnimalApiService {
        return mockService
    }

}