package com.kentlogic.mvvm_template.model

import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("/photos")
    fun getPhotos(): Single<List<Photos>>

}