package petstore.steps

import assertk.assertThat
import assertk.assertions.isNotNull
import io.cucumber.java8.En
import io.cucumber.java8.HookNoArgsBody
import petstore.actors.PetStore
import petstore.models.Pet

class FetchPetsSteps: En {

    private lateinit var petStore: PetStore
    private lateinit var fetchedPets: Set<Pet>

    init {

        Before(HookNoArgsBody { startStore() })

        When("the user fetches the {string} pets") {
                status: String ->
            run {
                fetchedPets = petStore.fetchPetsByStatus(status)
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

    private fun startStore() {
        petStore = PetStore()
    }

}