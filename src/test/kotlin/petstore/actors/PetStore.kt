package petstore.actors

import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.Pet

class PetStore {
    fun fetchPetsByStatus(status: String): Set<Pet> =
        Given {
            queryParam("status", status)
        }. When {
            get("https://petstore.swagger.io/v2/pet/findByStatus")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Array<Pet>::class.java)
        }.toSet()
}