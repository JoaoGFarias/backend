package petstore.models

data class Pet(
    val id: String,
    val category: PetCategory,
    val name: String,
    val photoUrls: List<String>,
    val tags: List<Tag>,
    val status: String
)

data class PetCategory (
    val id: String,
    val name: String
)

data class Tag (
    val id: String,
    val name: String
)
