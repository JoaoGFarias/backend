package petstore.actors

class PetStoreApiFactory {
    fun makeApi(): PetStoreApi {
        val target = System.getProperty("target")
        return when(target){
            "external" -> RestAssuredPetStoreExternal(System.getProperty("baseUrl"), System.getProperty("apiUrl"))
            "local" -> RestAssuredPetStoreLocal(System.getProperty("apiUrl"), System.getProperty("port").toInt())
            else -> throw Error()
        }
    }

}
