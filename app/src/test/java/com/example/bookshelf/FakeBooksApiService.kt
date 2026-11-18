package com.example.bookshelf

import com.example.bookshelf.data.ImageLinks
import com.example.bookshelf.data.Volume
import com.example.bookshelf.data.VolumeInfo
import com.example.bookshelf.data.VolumesResponse
import com.example.bookshelf.network.BooksApiService

/** Servicio falso: no hace peticiones reales a la Google Books API. */
class FakeBooksApiService : BooksApiService {

    override suspend fun searchBooks(query: String, maxResults: Int): VolumesResponse =
        VolumesResponse(items = listOf(Volume("1", null), Volume("2", null)))

    override suspend fun getBook(id: String): Volume =
        Volume(
            id = id,
            volumeInfo = VolumeInfo(
                title = "Libro $id",
                imageLinks = ImageLinks("http://example.com/$id.jpg")
            )
        )
}
