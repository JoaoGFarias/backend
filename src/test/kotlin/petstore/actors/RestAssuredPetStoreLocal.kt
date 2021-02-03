package petstore.actors

import com.google.gson.Gson
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.apache.http.HttpStatus
import petstore.models.Pet
import java.lang.AssertionError

class RestAssuredPetStoreLocal(private val apiUrl: String, private val port: Int = 8080) : PetStoreApi {
    override fun fetchPetsByStatus(status: String): Set<Pet> =
        Given {
            port(port).queryParam("status", status)
        }. When {
            get("$apiUrl/pet/findByStatus")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Array<Pet>::class.java)
        }.toSet()

    override fun addPetToStore(pet: Pet): Pet =
        Given {
            port(port).
            contentType(ContentType.JSON).body(makePetJson(pet))
        }. When {
            post("$apiUrl/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    private fun makePetJson(pet: Pet) =
        Gson().toJson(pet, Pet::class.java)

    override fun updatePetInStore(pet: Pet): Pet =
        Given {
            port(port).
            contentType(ContentType.JSON).body(makePetJson(pet))
        }. When {
            put("$apiUrl/pet")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }. Extract {
            body().`as`(Pet::class.java)
        }

    override fun deletePet(pet: Pet) {
        Given {
            port(port)
        }. When {
            delete("$apiUrl/pet/${pet.id}")
        }. Then {
            statusCode(HttpStatus.SC_OK)
        }
    }

    override fun isPetMissingInStore(pet: Pet): Boolean {
        return try {
            Given {
                port(port)
            }.When {
                get("$apiUrl/pet/${pet.id}")
            }. Then {
                statusCode(HttpStatus.SC_NOT_FOUND)
            }
            true
        } catch (e: AssertionError){
            false
        }
    }

}
