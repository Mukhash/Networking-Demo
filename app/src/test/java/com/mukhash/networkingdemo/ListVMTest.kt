package com.mukhash.networkingdemo

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mukhash.networkingdemo.di.AppModule
import com.mukhash.networkingdemo.di.DaggerViewModelComponent
import com.mukhash.networkingdemo.model.Animal
import com.mukhash.networkingdemo.model.AnimalApiService
import com.mukhash.networkingdemo.model.ApiKey
import com.mukhash.networkingdemo.util.SharedPreferenceHelper
import com.mukhash.networkingdemo.viewmodel.ListViewVM
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListVMTest {

    private val key = "Test key"

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var animalApiService: AnimalApiService

    @Mock
    lateinit var prefs: SharedPreferenceHelper

    val application = Mockito.mock(Application::class.java)

    var listViewModel = ListViewVM(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val testComponent = DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(animalApiService))
            .prefsModule(PrefsModuleTest(prefs))
            .build()
            .inject(listViewModel)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun getAnimalsSuccess() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val animal = Animal(name = "tiger", lifeSpan = null, imageUrl = null, taxonomy = null, speed = null, location = null, diet = null)
        val animalList = listOf(animal)

        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()
        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getAnimalsFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(key)
        val testSingle = Single.error<List<Animal>>(Throwable())
        val keySingle = Single.just(ApiKey("OK", key))

        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)
        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(null, listViewModel.animals.value)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
    }

    @Test
    fun getApiKeySuccessIf() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle = Single.just(ApiKey("OK", null))

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)

        listViewModel.refresh()

        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Test
    fun getApiKeySuccessElse() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keySingle = Single.just(ApiKey("OK", key))

        val animal = Animal(name = "tiger", lifeSpan = null, imageUrl = null, taxonomy = null, speed = null, location = null, diet = null)
        val animalList = listOf(animal)
        val testSingle = Single.just(animalList)

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keySingle)
        Mockito.`when`(animalApiService.getAnimals(key)).thenReturn(testSingle)

        listViewModel.refresh()

        Assert.assertEquals(1, listViewModel.animals.value?.size)
        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(false, listViewModel.loadError.value)
    }

    @Test
    fun getApiKeyFailure() {
        Mockito.`when`(prefs.getApiKey()).thenReturn(null)
        val keyErrorSingle = Single.error<ApiKey>(Throwable())

        Mockito.`when`(animalApiService.getApiKey()).thenReturn(keyErrorSingle)

        listViewModel.refresh()

        Assert.assertEquals(false, listViewModel.loading.value)
        Assert.assertEquals(true, listViewModel.loadError.value)
        Assert.assertEquals(null, listViewModel.animals.value)
    }

}