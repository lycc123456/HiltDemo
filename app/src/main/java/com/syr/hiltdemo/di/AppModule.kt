package com.syr.hiltdemo.di

import com.syr.hiltdemo.ApiService
import com.syr.module_common.BuildConfig
import com.syr.module_common.net.ApiFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Module to tell Hilt how to provide instances of types that cannot be constructor-injected.
 *
 * @author songyaru
 * @date 2020/8/17
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApiService(apiFactory: ApiFactory): ApiService {
        return apiFactory.createApiService(BuildConfig.BASE_URL, ApiService::class.java)
    }
}