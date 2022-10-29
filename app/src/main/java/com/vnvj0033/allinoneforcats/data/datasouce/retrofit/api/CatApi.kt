package com.vnvj0033.allinoneforcats.data.datasouce.retrofit.api

import com.vnvj0033.allinoneforcats.data.entry.Cat
import retrofit2.Call
import retrofit2.http.GET

interface CatApi {

    @GET("image/search")
    fun getCat(): Call<Cat>
}