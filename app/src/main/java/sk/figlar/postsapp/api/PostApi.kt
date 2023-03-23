package sk.figlar.postsapp.api

import retrofit2.http.GET

interface PostApi {

    @GET(
        "/posts"
    )
//    suspend fun getPostApiModels(@Query("start_date") startDate: String): List<ApodApiModel>
    suspend fun getPostApiModels(): List<PostApiModel>
}
