package com.kentlogic.mvvm_template.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kentlogic.mvvm_template.model.ApiService
import com.kentlogic.mvvm_template.model.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PhotosViewModel(application: Application): BaseViewModel(application) {

    val photos = MutableLiveData<List<Photos>>()
    val isLoading = MutableLiveData<Boolean>()
    val hasError = MutableLiveData<Boolean>()

    val apiService = ApiService()

    val disposable = CompositeDisposable()

    fun refresh(){
        //show loading animation while loading data
        isLoading.value = true

        disposable.add(
            apiService.getPhotos()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Photos>>(){
                    override fun onSuccess(t: List<Photos>) {
                        photos.value = t
                        hasError.value = false
                        isLoading.value = false
                        Log.i("I", t.toString())
                    }

                    override fun onError(e: Throwable) {
                        isLoading.value = false
                        hasError.value = true
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}