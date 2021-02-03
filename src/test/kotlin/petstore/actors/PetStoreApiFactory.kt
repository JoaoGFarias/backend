package petstore.actors

class PetStoreApiFactory {
    fun makeApi(): PetStoreApi {
        val baseUrl = System.getProperty("baseUrl")
        val version = System.getProperty("version")
        return when(version){
            "v2" -> RestAssuredPetStoreV2(baseUrl)
            else -> RestAssuredPetStoreV3("baseUrl")
        }
    }

}
