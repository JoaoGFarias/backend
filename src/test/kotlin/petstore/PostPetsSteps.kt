package petstore

import assertk.Assert
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import com.google.gson.Gson
import io.cucumber.java8.En
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.*

class PostPetsSteps: En {


    private lateinit var returnedPet: Pet

    init {
        Given("the user adds a new pet with {string} name, {string}, and {string} status") {
                name: String, category: String, status: String ->
            run {
                returnedPet = addPetToStore(
                    createPet(name, category, status)
                )
            }

        }

        Then("the pet with {string} name, {string}, and {string} is returned") {
                name: String, category: String, status: String ->
            run {
                val expectedPet = createPet(name, category, status)
                assertAll {
                    assertThat(returnedPet).`has same name as`(expectedPet)
                    assertThat(returnedPet.category).`is in same category as`(expectedPet)
                    assertThat(returnedPet).`has same status as`(expectedPet)
                }
            }
        }
    }

    private fun createPet(name: String, category: String, status: String) =
        Pet(name = name, category = PetCategory(name = category), status = status)

    private fun addPetToStore(pet: Pet) =
        Given {
            port(8080).contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }. When {
            post("/api/v3/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    private fun makePetJson(pet: Pet) =
        Gson().toJson(pet, Pet::class.java)

}

