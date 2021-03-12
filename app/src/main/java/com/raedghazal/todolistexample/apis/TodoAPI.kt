package com.raedghazal.todolistexample.apis

import com.raedghazal.todolistexample.model.Todo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoAPI {

    /*set suspend fun to execute in coroutines*/
    @GET("/todos")
    fun getTodos(): Observable<MutableList<Todo>>


    /** Post data function
     * @Post("/[followed path]")
     * @Query stands for get parameters, parameters that appear in the url .com?title="[ title ]"
     * @Body stands for post parameters, parameters that don't appear in the url

    @POST("/createTodo")
    fun createTodo(@Body todo: Todo): Response<CreateTodoResponse>
     */
}