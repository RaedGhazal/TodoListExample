package com.raedghazal.todolistexample.repostitory

import com.raedghazal.todolistexample.RetrofitInstance
import com.raedghazal.todolistexample.model.Todo
import io.reactivex.Observable
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class TodoRepository {

    fun getTodos(): Future<Observable<MutableList<Todo>>> {
        val executor = Executors.newSingleThreadExecutor()
        val callable = Callable {
            return@Callable RetrofitInstance.api.getTodos()
        }

        return object : Future<Observable<MutableList<Todo>>> {
            override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
                if (mayInterruptIfRunning) {
                    executor.shutdown()
                }
                return false
            }

            override fun isCancelled(): Boolean {
                return executor.isShutdown
            }

            override fun isDone(): Boolean {
                return executor.isTerminated
            }

            override fun get(): Observable<MutableList<Todo>> {
                return executor.submit(callable).get()
            }

            override fun get(timeout: Long, unit: TimeUnit?): Observable<MutableList<Todo>> {
                return executor.submit(callable).get(timeout, unit)
            }

        }
    }
}