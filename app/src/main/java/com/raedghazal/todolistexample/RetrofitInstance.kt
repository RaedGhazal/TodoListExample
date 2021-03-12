package com.raedghazal.todolistexample

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raedghazal.todolistexample.apis.TodoAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: TodoAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // to parse the response to something as RxJava stream
            .build()
            .create(TodoAPI::class.java)
    }
}