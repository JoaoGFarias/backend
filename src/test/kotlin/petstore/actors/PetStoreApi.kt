package petstore.actors

import petstore.models.Pet

interface PetStoreApi {
    fun fetchPetsByStatus(status: String): Set<Pet>
    fun addPetToStore(pet: Pet): Pet
    fun updatePetInStore(pet: Pet): Pet
    fun deletePet(pet: Pet)
    fun isPetMissingInStore(pet: Pet): Boolean
}