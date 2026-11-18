# Bookshelf – cómo usar estos archivos

## 1. Crear el proyecto
Android Studio → New Project → **Empty Activity** (Compose).
Nombre: `Bookshelf`, paquete: `com.example.bookshelf`.

## 2. Copiar los archivos
Copia el contenido de `app/src/` de este zip dentro del `app/src/` de tu proyecto
(reemplaza `MainActivity.kt` y `AndroidManifest.xml`).
No toques la carpeta `ui/theme/` que generó Android Studio.

## 3. Dependencias (app/build.gradle.kts)
Añade dentro de `dependencies { ... }`:

```kotlin
// Retrofit + Gson
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// Coil (imágenes)
implementation("io.coil-kt:coil-compose:2.7.0")

// ViewModel en Compose
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

// Pruebas
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
```

Luego **Sync Now**.

## 4. Revisar el manifest
- Si tu proyecto usa otro nombre de tema, cambia `@style/Theme.Bookshelf`
  (o deja el que ya tenía tu manifest original).
- Si tu tema Compose no se llama `BookshelfTheme`, ajusta el import en `MainActivity.kt`.

## Estructura
```
app/src/
├── main/
│   ├── AndroidManifest.xml
│   └── java/com/example/bookshelf/
│       ├── MainActivity.kt
│       ├── BookshelfApplication.kt
│       ├── data/
│       │   ├── Models.kt
│       │   ├── BooksRepository.kt
│       │   └── AppContainer.kt
│       ├── network/
│       │   └── BooksApiService.kt
│       └── ui/
│           ├── BookshelfViewModel.kt
│           └── BookshelfApp.kt
└── test/java/com/example/bookshelf/
    ├── FakeBooksApiService.kt
    └── NetworkBooksRepositoryTest.kt
```
