package com.example.bookshelf.network

import com.example.bookshelf.data.Volume
import com.example.bookshelf.data.VolumesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {

    // https://www.googleapis.com/books/v1/volumes?q=jazz+history
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 10
    ): VolumesResponse

    // https://www.googleapis.com/books/v1/volumes/<volume_id>
    @GET("volumes/{id}")
    suspend fun getBook(@Path("id") id: String): Volume
}
