package petstore.steps

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isFailure
import assertk.assertions.isTrue
import assertk.assertions.messageContains
import com.google.gson.Gson
import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.actors.PetStore
import petstore.models.*

class ChangePetsSteps: En {


    private lateinit var petStore: PetStore
    private lateinit var returnedPet: Pet

    init {
        Before(HookNoArgsBody { startStore() })

        Given("the user adds a new pet with {string} name, {string}, and {string} status") {
                name: String, category: String, status: String ->
            run {
                val petToSave = createPet(name, category, status)

                returnedPet = petStore.addPetToStore(
                    petToSave
                )

                assertAll {
                    assertThat(returnedPet).`has same name as`(petToSave)
                    assertThat(returnedPet.category!!).`is in same category as`(petToSave)
                    assertThat(returnedPet).`has same status as`(petToSave)
                }
            }
        }

        When("the user marks the pet as {string}") {
            newStatus: String ->
            run {
                val updatedPet = returnedPet.copy(status = newStatus)
                returnedPet = petStore.updatePetInStore(updatedPet)

                assertAll {
                    assertThat(returnedPet).`has same id as`(updatedPet)
                    assertThat(returnedPet).`has same name as`(updatedPet)
                    assertThat(returnedPet).`has same status as`(updatedPet)
                }
            }
        }

        When("the user deletes the pet") {
            petStore.deletePet(returnedPet)
        }

        Then("the pet no longer exists") {
            assertThat(petStore.isPetMissingInStore(returnedPet)).isTrue()
        }
    }

    private fun startStore() {
        petStore = PetStore()
    }

    private fun createPet(name: String, category: String, status: String) =
        Pet(name = name, category = PetCategory(name = category), status = status)

}
