package petstore.steps

import assertk.assertThat
import assertk.assertions.isNotNull
import io.cucumber.java8.En
import petstore.actors.PetStore
import petstore.models.Pet

class FetchPetsSteps(private val petStore: PetStore): En {

    private lateinit var fetchedPets: Set<Pet>

    init {
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

}