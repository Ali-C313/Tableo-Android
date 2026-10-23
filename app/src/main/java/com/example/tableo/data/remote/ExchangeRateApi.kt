package com.example.tableo.data.remote

import com.example.tableo.data.model.ExchangeRateResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.frankfurter.app/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/** Wechselkurs-API (frankfurter.app). */
interface ExchangeRateApiService {

    /** Kurse von [from] in die Währungen aus [to], z.B. "USD,GBP". */
    @GET("latest")
    suspend fun getLatestRates(
        @Query("from") from: String = "EUR",
        @Query("to") to: String
    ): ExchangeRateResponse
}

object ExchangeRateApi {
    val retrofitService: ExchangeRateApiService by lazy { retrofit.create(ExchangeRateApiService::class.java) }
}
