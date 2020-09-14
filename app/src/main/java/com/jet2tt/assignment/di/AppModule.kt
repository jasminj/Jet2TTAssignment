package com.jet2tt.assignment.di

import com.google.gson.Gson
import com.jet2tt.assignment.BuildConfig
import com.jet2tt.assignment.Jet2TTApp
import com.jet2tt.assignment.api.ArticleService
import com.jet2tt.assignment.api.CacheInterceptor
import com.jet2tt.assignment.api.ResponseHandler
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val appCache =
        Jet2TTApp.getInstance()?.getContext()?.cacheDir?.let { Cache(it, cacheSize) }

    @Singleton
    @Provides
    fun provideArticleService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, ArticleService::class.java)

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(appCache)
            .addInterceptor(cacheInterceptor)
            .addInterceptor(interceptor)
            .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    fun provideCacheInterceptor(): CacheInterceptor = CacheInterceptor()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideResponseHandles(): ResponseHandler = ResponseHandler()

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ArticleService.ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}