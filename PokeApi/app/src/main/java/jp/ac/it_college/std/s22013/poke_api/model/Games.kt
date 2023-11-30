package jp.ac.it_college.std.s22013.poke_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Games カテゴリのモデル
@Serializable
data class Generation(
    val id: Int,
    val name: String,
    val abilities: List<NamedApiResource>,
    val names: List<Name>,
    @SerialName("main_region") val mainRegion: NamedApiResource,
    val moves: NamedApiResource,
    @SerialName("pokemon_species") val pokemonSpecies: List<NamedApiResource>,
    val types: List<NamedApiResource>,
    @SerialName("version_groups") val versionGroups: List<NamedApiResource>,
)