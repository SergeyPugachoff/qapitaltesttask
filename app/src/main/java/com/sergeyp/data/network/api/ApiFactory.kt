package com.sergeyp.data.network.api

import com.google.gson.Gson
import com.sergeyp.BuildConfig
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {
    private const val BASE_URL = "http://qapital-ios-testtask.herokuapp.com/"
    private const val TIMEOUT = 30L

    private val converterFactory: Converter.Factory = GsonConverterFactory.create(Gson())

    private fun buildOkHttpClient(timeout: Long): OkHttpClient =
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()

    private fun provideRetrofit(baseUrl:String, timeout: Long): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(baseUrl)
            .client(buildOkHttpClient(timeout))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    fun <T> provideApiService(serviceClass: Class<T>): T =
        provideRetrofit(BASE_URL, TIMEOUT).create(serviceClass)
}