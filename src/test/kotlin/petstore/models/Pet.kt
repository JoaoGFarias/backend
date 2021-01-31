package petstore.models

import assertk.Assert
import assertk.assertions.isEqualTo
import assertk.assertions.prop
import java.util.*

data class Pet(
    val id: String = randomId(),
    val category: PetCategory,
    val name: String,
    val photoUrls: List<String> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val status: String
)

fun randomId() = Random().nextInt().toUInt().toString()

data class PetCategory (
    val id: String = randomId(),
    val name: String
)

data class Tag (
    val id: String = randomId(),
    val name: String
)

fun Assert<Pet>.`has same id as`(expectedPet: Pet) {
    prop(Pet::id).isEqualTo(expectedPet.id)
}

fun Assert<Pet>.`has same name as`(expectedPet: Pet) {
    prop(Pet::name).isEqualTo(expectedPet.name)
}

fun Assert<Pet>.`has same status as`(expectedPet: Pet) {
    prop(Pet::status).isEqualTo(expectedPet.status)
}

fun Assert<PetCategory>.`is in same category as`(expectedPet: Pet) {
    prop(PetCategory::name).isEqualTo(expectedPet.category.name)
}