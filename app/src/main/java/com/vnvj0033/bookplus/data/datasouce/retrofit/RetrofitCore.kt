package com.vnvj0033.bookplus.data.datasouce.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitCore {

    // OkHttp client
    private val client = OkHttpClient.Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }) // debugging 용도 로그 노출
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
//                .removeHeader("User-Agent")
//                .addHeader("User-Agent", "Google-HTTP-Java-Client/1.23.0 (gzip)")
                .build()

            chain.run {
                withConnectTimeout(10, TimeUnit.SECONDS)
                withReadTimeout(10, TimeUnit.SECONDS)
                withWriteTimeout(10, TimeUnit.SECONDS)
            }

            chain.proceed(request)
        }.build()

    // 레트로핏 객체
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:3000/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}