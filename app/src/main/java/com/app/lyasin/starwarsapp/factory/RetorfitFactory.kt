package com.app.lyasin.starwarsapp.factory

import java.util.HashMap
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private val retrofit = HashMap<String, Retrofit?>()

    operator fun get(url: String): Retrofit? {


        if (!retrofit.containsKey(url)) {
            retrofit.put(url, null)
        }
        if (retrofit[url] == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(2, TimeUnit.MINUTES).readTimeout(2, TimeUnit.MINUTES).build()
            retrofit.put(url, Retrofit.Builder().baseUrl(url).client(client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build())
        }
        return retrofit[url]
    }
}