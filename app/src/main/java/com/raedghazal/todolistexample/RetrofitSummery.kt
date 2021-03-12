package com.raedghazal.todolistexample

/** 1. Permissions
 * INTERNET
 */

/** 2. Create a model for the retrieved data
 * Such as Todo
 * Can be created using "kotlin data class File from JSON" if a json response is available
 * */

/** 3. Create an API interface with retrofit annotations such as @GET, @POST, ...
 *
@GET("/todos")
fun getTodos(parameters): Response<List<Todo>>
 * */

/** 3. B)
 * @Post("/[followed path]")
 * @Query stands for get parameters, parameters that appear in the url .com?title="[ title ]"
 * @Body stands for post parameters, parameters that don't appear in the url

@POST("/createTodo")
fun createTodo(@Body todo: Todo): Response<CreateTodoResponse>
 * */


/** 4. Create a retrofit builder instance to make api calls
 * such as [RetrofitInstance.api]
 * */

/***/
/***/