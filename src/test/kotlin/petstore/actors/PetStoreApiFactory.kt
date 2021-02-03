package petstore.actors

class PetStoreApiFactory {
    fun makeApi(): PetStoreApi {
        val baseUrl = System.getProperty("baseUrl")
        return RestAssuredPetStoreV2(baseUrl)
    }

}
