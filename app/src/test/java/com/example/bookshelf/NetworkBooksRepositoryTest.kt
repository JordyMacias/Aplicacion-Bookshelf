package com.example.bookshelf

import com.example.bookshelf.data.NetworkBooksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NetworkBooksRepositoryTest {

    @Test
    fun getBooks_returnsBooksWithHttpsThumbnails() = runTest {
        val repository = NetworkBooksRepository(FakeBooksApiService())

        val books = repository.getBooks("jazz")

        assertEquals(2, books.size)
        assertEquals("Libro 1", books[0].title)
        assertEquals("https://example.com/1.jpg", books[0].thumbnailUrl)
    }
}
