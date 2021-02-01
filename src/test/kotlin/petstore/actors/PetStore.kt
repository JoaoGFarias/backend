package petstore.actors

import assertk.assertThat
import assertk.assertions.isFailure
import assertk.assertions.messageContains
import com.google.gson.Gson
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.Pet
import java.lang.AssertionError

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

    fun addPetToStore(pet: Pet): Pet =
        Given {
            contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }. When {
            post("https://petstore.swagger.io/v2/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    private fun makePetJson(pet: Pet) =
        Gson().toJson(pet, Pet::class.java)

    fun updatePetInStore(pet: Pet) =
        Given {
            contentType(ContentType.JSON)
                .body(makePetJson(pet))
        }. When {
            put("https://petstore.swagger.io/v2/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    fun deletePet(pet: Pet) {
        When {
            delete("https://petstore.swagger.io/v2/pet/${pet.id}")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }
    }

    fun isPetMissingInStore(pet: Pet): Boolean {
        return try {
            When {
                get("https://petstore.swagger.io/v2/pet/${pet.id}")
            }. Then {
                statusCode(HttpStatus.SC_NOT_FOUND)
            }
            true
        } catch (e: AssertionError){
            false
        }
    }

}

