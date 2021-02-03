package petstore.actors

import petstore.models.Pet

class PetStore: PetStoreApi {
    private var delegate: PetStoreApi? = null

    private fun getDelegate(): PetStoreApi {
        if (delegate == null) {
            delegate = PetStoreApiFactory().makeApi()
        }
        return delegate!!
    }

    override fun fetchPetsByStatus(status: String): Set<Pet> = getDelegate().fetchPetsByStatus(status)

    override fun addPetToStore(pet: Pet) = getDelegate().addPetToStore(pet)

    override fun updatePetInStore(pet: Pet) = getDelegate().updatePetInStore(pet)

    override fun deletePet(pet: Pet) = getDelegate().deletePet(pet)

    override fun isPetMissingInStore(pet: Pet): Boolean = getDelegate().isPetMissingInStore(pet)
}