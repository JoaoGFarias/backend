package petstore.clients

import com.google.gson.Gson
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.Pet
import java.lang.AssertionError
import java.util.logging.Logger

class RestAssuredPetStoreExternal(private val baseUrl: String, private val apiUrl: String) : PetStoreApi {
    override fun fetchPetsByStatus(status: String): Set<Pet> {
        logger.info { "Fetching pets with status: $status" }

        return Given {
            queryParam("status", status)
        }.When {
            get("$baseUrl$apiUrl/pet/findByStatus")
        }.Then {
            statusCode(HttpStatus.SC_OK)
        }.Extract {
            body().`as`(Array<Pet>::class.java)
        }.toSet()
    }
    override fun addPetToStore(pet: Pet): Pet {
        logger.info { "Adding pet to the store: Pet ID => ${pet.id}" }
        return Given {
            contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }.When {
            post("$baseUrl$apiUrl/pet")
        }.Then {
            statusCode(HttpStatus.SC_OK)
        }.Extract {
            body().`as`(Pet::class.java)
        }
    }

    private fun makePetJson(pet: Pet) =
        Gson().toJson(pet, Pet::class.java)

    override fun updatePetInStore(pet: Pet): Pet {
        logger.info { "Updating pet information: Pet ID => ${pet.id} | Pet new data => $pet" }
        return Given {
            contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }.When {
            put("$baseUrl$apiUrl/pet")
        }.Then {
            statusCode(HttpStatus.SC_OK)
        }.Extract {
            body().`as`(Pet::class.java)
        }
    }

    override fun deletePet(pet: Pet) {
        logger.info { "Deleting pet in the store: Pet ID => ${pet.id}" }
        When {
            delete("$baseUrl$apiUrl/pet/${pet.id}")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }
    }

    override fun isPetMissingInStore(pet: Pet): Boolean {
        logger.info { "Checking if Pet is in the store: Pet ID => ${pet.id}" }
        return try {
            When {
                get("$baseUrl$apiUrl/pet/${pet.id}")
            }. Then {
                statusCode(HttpStatus.SC_NOT_FOUND)
            }
            true
        } catch (e: AssertionError){
            false
        }
    }


    companion object {
        private val logger = Logger.getLogger(this.javaClass.name)
    }

}

