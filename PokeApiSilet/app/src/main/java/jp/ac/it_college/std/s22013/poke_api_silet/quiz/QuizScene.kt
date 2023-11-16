package jp.ac.it_college.std.s22013.poke_api_silet.quiz


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.ac.it_college.std.s22013.poke_api_silet.model.PokeQuiz
import jp.ac.it_college.std.s22013.poke_api_silet.ui.theme.PokeApiSiletTheme



/**
 * クイズのメインとなるやつ
 */
@Composable
fun QuizScene(
    quiz: PokeQuiz,
    modifier: Modifier = Modifier,
) {
    Surface(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PokeImage(quiz.imageUrl)
            PokeNameList(quiz.choices)
        }
    }
}

/**
 * [imageUrl] にポケモンの画像がある URL を指定
 * [isSilhouette] が ture だとシルエット表示
 */
@Composable
fun PokeImage(imageUrl: String, isSilhouette: Boolean = true) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(240.dp)
                .clip(RoundedCornerShape(20))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "PokeImage",
                // カラーフィルターでシルエット表示みたいなことができる。
                // 画像加工の詳細は各自で勉強してください
                colorFilter = if (isSilhouette) ColorFilter.tint(
                    Color.Black,
                    BlendMode.SrcIn
                ) else null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun PokeName(name: String) {
    // 背景色・文字色を全体的に設定するために使ってる
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .padding(8.dp),

        ) {
            // なまえ
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,


            )
        }
    }
}

@Composable
fun PokeNameList(items: List<String>) {
    LazyColumn() {
        items(items) {
            PokeName(name = it)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun QuizScenePreview() {
    PokeApiSiletTheme {
        QuizScene(
            PokeQuiz(
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/906.png",
                choices = listOf("ニャオハ", "ホゲータ", "クワッス", "グルトン"),
                correct = "ニャオハ"
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}