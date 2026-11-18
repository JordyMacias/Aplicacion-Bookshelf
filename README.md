# 📚 Bookshelf

App Android que muestra una cuadrícula de portadas de libros obtenidas desde la
[Google Books API](https://developers.google.com/books). Por defecto busca
**"jazz history"** y presenta cada libro con su portada y título.

Construida con **Kotlin**, **Jetpack Compose** (Material 3), **Retrofit** y **Coil**,
siguiendo una arquitectura sencilla por capas (UI → ViewModel → Repositorio → API).

## Características

- Búsqueda de libros en Google Books y carga de las portadas con Coil.
- Cuadrícula adaptable (`GridCells.Adaptive(150.dp)`) para teléfonos y tablets.
- Tres estados de UI: **Cargando**, **Error** (con botón *Reintentar*) y **Éxito**.
- Manejo de campos nulos de la API y conversión de `http://` a `https://` en las
  miniaturas para que las imágenes carguen.
- Inyección de dependencias manual mediante un `AppContainer`.
- Prueba unitaria del repositorio con un servicio falso (sin red).

## Requisitos

| Herramienta | Versión |
|---|---|
| Android Studio | Una versión reciente compatible con AGP 9.x |
| Android Gradle Plugin | 9.3.3 |
| Kotlin | 2.2.10 |
| JDK | 11 o superior |
| `minSdk` / `targetSdk` / `compileSdk` | 35 / 37 / 37 |

> **Nota:** con `minSdk = 35` la app solo se instala en dispositivos o emuladores
> con Android 15 o superior.

## Cómo ejecutarla

1. Abre la carpeta del proyecto (`Bookshelf/`) en Android Studio.
2. Espera a que termine el **Gradle Sync**.
3. Selecciona un emulador o dispositivo con Android 15+ y pulsa **Run ▶**.

Desde la terminal:

```bash
./gradlew assembleDebug      # compila el APK de depuración
./gradlew installDebug       # lo instala en un dispositivo/emulador conectado
```

El dispositivo necesita conexión a Internet (la app declara el permiso `INTERNET`).

### Clave de API (opcional)

`app/build.gradle.kts` lee `BOOKS_API_KEY` desde `local.properties` y la expone como
`BuildConfig.BOOKS_API_KEY`. Actualmente el código **no la utiliza** (las consultas
se hacen sin clave), pero si quieres usarla añade en `local.properties`:

```properties
BOOKS_API_KEY=tu_clave_aquí
```

`local.properties` no debe subirse al control de versiones.

## Pruebas

```bash
./gradlew test                    # pruebas unitarias (JVM)
./gradlew connectedAndroidTest    # pruebas instrumentadas (requiere dispositivo)
```

`NetworkBooksRepositoryTest` verifica, usando `FakeBooksApiService`, que el repositorio
devuelve los libros esperados y que las miniaturas se convierten a `https`.

## Arquitectura

```
UI (Compose)  ──►  BookshelfViewModel  ──►  BooksRepository  ──►  BooksApiService (Retrofit)
BookshelfApp       uiState                  NetworkBooksRepository   Google Books API
```

**Flujo de datos**

1. `BookshelfViewModel` llama a `getBooks("jazz history")` al iniciarse.
2. `NetworkBooksRepository` busca los volúmenes (`GET volumes?q=...&maxResults=10`)
   y se queda con sus `id`.
3. Por cada `id` pide el detalle (`GET volumes/{id}`), descarta los libros sin
   miniatura y los convierte al modelo limpio `Book`.
4. El ViewModel publica el resultado como `BookshelfUiState` y la UI se recompone.

Los errores de red (`IOException`) y HTTP (`HttpException`) se transforman en el estado
`Error`.

## Estructura del proyecto

```
Bookshelf/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle/libs.versions.toml          # catálogo de versiones
└── app/
    ├── build.gradle.kts
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml
        │   └── java/com/example/bookshelf/
        │       ├── MainActivity.kt
        │       ├── BookshelfApplication.kt   # crea el AppContainer
        │       ├── data/
        │       │   ├── Models.kt             # modelos JSON (Gson) y Book
        │       │   ├── BooksRepository.kt    # interfaz + implementación de red
        │       │   └── AppContainer.kt       # configura Retrofit y el repositorio
        │       ├── network/
        │       │   └── BooksApiService.kt    # endpoints de Retrofit
        │       └── ui/
        │           ├── BookshelfViewModel.kt # estado de la pantalla
        │           ├── BookshelfApp.kt       # Scaffold + cuadrícula de libros
        │           └── theme/                # tema Material 3
        ├── test/                             # pruebas unitarias + servicio falso
        └── androidTest/                      # pruebas instrumentadas
```

## Tecnologías

- [Jetpack Compose](https://developer.android.com/jetpack/compose) + Material 3
- [Retrofit](https://square.github.io/retrofit/) 2.11.0 con conversor Gson
- [Coil](https://coil-kt.github.io/coil/) 2.7.0 para imágenes
- Lifecycle ViewModel para Compose
- Corrutinas de Kotlin (`kotlinx-coroutines-test` en las pruebas)

## Posibles mejoras

- Añadir un campo de búsqueda (el ViewModel ya acepta un `query`).
- Cargar los detalles de los libros en paralelo en lugar de uno tras otro.
- Pantalla de detalle del libro.
- Distinguir errores concretos (sin conexión, límite de cuota, etc.).
└── test/java/com/example/bookshelf/
    ├── FakeBooksApiService.kt
    └── NetworkBooksRepositoryTest.kt
```
