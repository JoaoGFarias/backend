package petstore

import assertk.Assert
import assertk.assertAll
import assertk.assertThat
import assertk.assertions.*
import com.google.gson.Gson
import io.cucumber.java8.En
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.*
class ChangePetsSteps: En {


    private lateinit var returnedPet: Pet

    init {
        Given("the user adds a new pet with {string} name, {string}, and {string} status") {
                name: String, category: String, status: String ->
            run {
                val petToSave = createPet(name, category, status)

                returnedPet = addPetToStore(
                    petToSave
                )

                assertAll {
                    assertThat(returnedPet).`has same name as`(petToSave)
                    assertThat(returnedPet.category).`is in same category as`(petToSave)
                    assertThat(returnedPet).`has same status as`(petToSave)
                }
            }
        }

        When("the user marks the pet as {string}") {
            newStatus: String ->
            run {
                val updatedPet = returnedPet.copy(status = newStatus)
                returnedPet = updatePetInStore(updatedPet)

                assertAll {
                    assertThat(returnedPet).`has same id as`(updatedPet)
                    assertThat(returnedPet).`has same name as`(updatedPet)
                    assertThat(returnedPet).`has same status as`(updatedPet)
                }
            }
        }

        When("the user deletes the pet") {
            deletePet(returnedPet)
        }

        Then("the pet no longer exists") {
            assertThat {
                fetchPetById(returnedPet)
            }.isFailure().messageContains("<200> but was <404>")
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

    private fun updatePetInStore(pet: Pet) =
        Given {
            port(8080).contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }. When {
            put("/api/v3/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    private fun deletePet(pet: Pet) {
        Given {
            port(8080)
        }. When {
            delete("/api/v3/pet/${pet.id}")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }
    }

    private fun fetchPetById(pet: Pet) =
        Given {
            port(8080)
        }. When {
            get("/api/v3/pet/${pet.id}")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }
}


