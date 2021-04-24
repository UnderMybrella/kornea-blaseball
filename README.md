# kornea-blaseball

- ![kornea-blaseball-base](https://img.shields.io/maven-metadata/v?label=kornea-blaseball-base&metadataUrl=https%3A%2F%2Fmaven.brella.dev%2Fdev%2Fbrella%2Fkornea-blaseball-base%2Fmaven-metadata.xml)
- ![kornea-blaseball-api](https://img.shields.io/maven-metadata/v?label=kornea-blaseball-api&metadataUrl=https%3A%2F%2Fmaven.brella.dev%2Fdev%2Fbrella%2Fkornea-blaseball-api%2Fmaven-metadata.xml)

Api Wrapper for [Blaseball](https://www.blaseball.com), both via the official api and [Chronicler](https://github.com/xSke/Chronicler).

## Example

```kotlin

val client = HttpClient(OkHttp) {
    install(ContentEncoding) {
        gzip()
        deflate()
        identity()
    }

    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }

    defaultRequest {
        userAgent("Mozilla/5.0 (X11; Linux x86_64; rv:85.0) Gecko/20100101 Firefox/85.0")
    }
}

val blaseballApi = BlaseballApi(client)

blaseballApi.getSimulationData()
    .doOnSuccess { simData -> 
        println("Season ${simData.season + 1}, Day ${simData.day + 1}") 
    }

```