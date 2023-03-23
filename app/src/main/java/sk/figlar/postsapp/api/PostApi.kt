package sk.figlar.postsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET(
        "/posts"
    )
    suspend fun getPostApiModels(): List<PostApiModel>

    @GET(
        "/posts/{id}"
    )
//    suspend fun getPost(@Path("id") id: Int): PostApiModel?
    suspend fun getPost(@Path("id") id: Int): Response<PostApiModel>

    @GET(
        "/users"
    )
    suspend fun getUserApiModels(): List<UserApiModel>
}
