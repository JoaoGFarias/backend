package petstore.steps

import assertk.assertThat
import assertk.assertions.isNotNull
import io.cucumber.java8.En
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.Pet

class FetchPetsSteps: En {

    private lateinit var fetchedPets: Set<Pet>

    init {

        When("the user fetches the {string} pets") {
                status: String ->
            run {
                fetchedPets = fetchPetsByStatus(status)
            }
        }

        Then("there are {string} pets") {
                status: String ->
            run {
                val petsWithStatus = fetchedPets.find { it.status == status }
                assertThat { petsWithStatus }.isNotNull()
            }
        }
    }

    private fun fetchPetsByStatus(status: String): Set<Pet> =
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