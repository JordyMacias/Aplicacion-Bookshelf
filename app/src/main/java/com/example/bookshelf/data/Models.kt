package com.example.bookshelf.data

// ---- Modelos de la respuesta JSON (Gson) ----
// Todo es nullable porque la API a veces omite campos.
data class VolumesResponse(val items: List<Volume>?)

data class Volume(val id: String, val volumeInfo: VolumeInfo?)

data class VolumeInfo(val title: String?, val imageLinks: ImageLinks?)

data class ImageLinks(val thumbnail: String?)

// ---- Modelo limpio para la UI ----
data class Book(
    val id: String,
    val title: String,
    val thumbnailUrl: String
)
