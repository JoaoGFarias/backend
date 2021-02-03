package petstore.steps

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isTrue
import io.cucumber.java8.En
import petstore.clients.PetStore
import petstore.models.*

class ChangePetsSteps(private val petStore: PetStore): En {


    private lateinit var returnedPet: Pet

    init {

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

    private fun createPet(name: String, category: String, status: String) =
        Pet(name = name, category = PetCategory(name = category), status = status)

}
