package jp.ac.it_college.std.s22013.poke_api_silet.model

data class PokeQuiz(
    val imageUrl: String,
    val choices: List<String>,
    val correct: String
) {
}