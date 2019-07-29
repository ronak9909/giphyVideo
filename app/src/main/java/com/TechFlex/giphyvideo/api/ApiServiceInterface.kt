package com.TechFlex.giphyvideo.api

import com.TechFlex.giphyvideo.BuildConfig
import com.TechFlex.giphyvideo.ui.list.domain.model.Giphy
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiServiceInterface {

    @GET("gifs/search?")
    fun getList(@Query("api_key") apiKey: String,
                 @Query("q") query: String,
                 @Query("limit") limit: String,
                 @Query("offset") offset: String,
                 @Query("rating") rating: String,
                 @Query("lang") lang: String): Observable<Giphy>


    companion object Factory {
        fun create(): ApiServiceInterface {

            val interceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                interceptor.level = HttpLoggingInterceptor.Level.NONE
            }
            val httpClientBuilder = OkHttpClient().newBuilder().addInterceptor(interceptor)
            httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)

            val httpClient = httpClientBuilder.build()

            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient)
                .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}