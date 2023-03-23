package sk.figlar.postsapp.api

import retrofit2.http.GET

interface PostApi {

    @GET(
        "/posts"
    )
    suspend fun getPostApiModels(): List<PostApiModel>

    @GET(
        "/users"
    )
    suspend fun getUserApiModels(): List<UserApiModel>
}
