package com.example.bookshelf.data

import com.example.bookshelf.network.BooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBooksRepository(
    private val api: BooksApiService
) : BooksRepository {

    override suspend fun getBooks(query: String): List<Book> {
        // 1) Buscar libros y quedarnos con los ids
        val ids = api.searchBooks(query).items.orEmpty().map { it.id }

        // 2) Pedir cada libro uno tras otro en la misma corrutina
        return ids.mapNotNull { id ->
            val info = api.getBook(id).volumeInfo
            val thumb = info?.imageLinks?.thumbnail ?: return@mapNotNull null
            Book(
                id = id,
                title = info.title ?: "Sin título",
                // Obligatorio: cambiar http por https para que la imagen cargue
                thumbnailUrl = thumb.replace("http://", "https://")
            )
        }
    }
}
