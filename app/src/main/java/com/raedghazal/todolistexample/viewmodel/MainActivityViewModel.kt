package com.raedghazal.todolistexample.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raedghazal.todolistexample.model.Todo
import com.raedghazal.todolistexample.repostitory.TodoRepository
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutionException


class MainActivityViewModel : ViewModel() {

    companion object {
        const val TAG = "MainActivityViewModel"
    }

    private lateinit var todosList: MutableLiveData<MutableList<Todo>>
    private lateinit var isUpdating: MutableLiveData<Boolean>
    private lateinit var disposable: CompositeDisposable


    fun init() {
        if (!this::isUpdating.isInitialized)
            isUpdating = MutableLiveData<Boolean>()
        isUpdating.value = false
        if (!this::disposable.isInitialized)
            disposable = CompositeDisposable()
        if (!this::todosList.isInitialized) {
            todosList = MutableLiveData<MutableList<Todo>>()
            todosList.postValue(mutableListOf())
            getTodos()
        }

    }

    private fun getTodos() {
        isUpdating.value = true
        try {
            TodoRepository().getTodos().get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    object : Observer<MutableList<Todo>> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d(TAG, "onSubscribe: subscribed")
                            disposable.add(d)
                        }

                        override fun onNext(t: MutableList<Todo>) {
                            Log.d(TAG, "onNext: $t")
                            todosList.value?.let {
                                if (it.isNotEmpty())
                                    it.clear()
                            }
                            todosList.postValue(t)
                        }

                        override fun onError(e: Throwable) {
                            Log.d(TAG, "onError: ${e.message}")
                            isUpdating.value = false
                        }

                        override fun onComplete() {
                            Log.d(TAG, "onComplete: completed")
                            isUpdating.value = false
                        }

                    })
        } catch (e: ExecutionException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun getIsUpdating(): LiveData<Boolean> = isUpdating
    fun getTodosList(): LiveData<MutableList<Todo>> = todosList

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}
