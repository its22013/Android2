package jp.ac.it_college.std.s22013.poke_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import jp.ac.it_college.std.s22013.poke_api.api.Games
import jp.ac.it_college.std.s22013.poke_api.ui.theme.PokeApiPrototypeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeApiPrototypeTheme {
                MainScene()
            }
        }
    }
}

enum class PokemonGeneration {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    EIGHTH,
    NINTH,
    // 追加の世代を必要に応じてここに追加してください
    ALL // 全世代
}

@Composable
fun MainScene() {
    var selectedGeneration by remember { mutableStateOf(PokemonGeneration.ALL) }
    var imageUrl by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedGeneration) {
        imageUrl = Games.getSilhouetteImageForGeneration(
            when (selectedGeneration) {
                PokemonGeneration.FIRST -> 1
                PokemonGeneration.SECOND -> 2
                PokemonGeneration.THIRD -> 3
                PokemonGeneration.FOURTH -> 4
                PokemonGeneration.FIFTH -> 5
                PokemonGeneration.SIXTH -> 6
                PokemonGeneration.SEVENTH -> 7
                PokemonGeneration.EIGHTH -> 8
                PokemonGeneration.NINTH -> 9

                // 他の世代に対しても同様に追加してください
                else -> 0 // 0はALLとして扱う
            }
        )
    }

    Surface {
        Column {
            HeaderSection()
            ImageSection(imageUrl)
            ButtonSection(scope) {
                scope.launch {
                    imageUrl = Games.getSilhouetteImageForGeneration(
                        when (selectedGeneration) {
                            PokemonGeneration.FIRST -> 1
                            PokemonGeneration.SECOND -> 2
                            PokemonGeneration.THIRD -> 3
                            PokemonGeneration.FOURTH -> 4
                            PokemonGeneration.FIFTH -> 5
                            PokemonGeneration.SIXTH -> 6
                            PokemonGeneration.SEVENTH -> 7
                            PokemonGeneration.EIGHTH -> 8
                            PokemonGeneration.NINTH -> 9
                            // 他の世代に対しても同様に追加してください
                            else -> 0
                        }
                    )
                }
            }
            GenerationSelection(selectedGeneration) { generation ->
                selectedGeneration = generation
            }
        }
    }
}


@Composable
fun HeaderSection() {
    Text(
        text = "このキャラは？",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(jp.ac.it_college.std.s22013.poke_api.ui.theme.Pink41)
    )
}

@Composable
fun ImageSection(imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp) // 画像の高さは適宜調整してください
        )
    } else {
        Text(text = "ポケモンの画像はありません")
    }

}

@Composable
fun ButtonSection(scope: CoroutineScope, onClick: () -> Unit)
{
    ElevatedButton(onClick = { scope.launch { onClick() } }) {
        Text(text = "レッツ回答！")
    }
}

@Composable
fun GenerationSelection(selectedGeneration: PokemonGeneration, onGenerationSelected: (PokemonGeneration) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PokemonGeneration.values().forEach { generation ->
                if (generation in PokemonGeneration.FIRST..PokemonGeneration.THIRD) {
                    val backgroundColor = if (selectedGeneration == generation) Color.Green else Color.White
                    Button(
                        onClick = { onGenerationSelected(generation) },
                        modifier = Modifier.background(backgroundColor)
                    ) {
                        Text(text = generation.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PokemonGeneration.values().forEach { generation ->
                if (generation in PokemonGeneration.FOURTH..PokemonGeneration.SIXTH) {
                    val backgroundColor = if (selectedGeneration == generation) Color.Green else Color.White
                    Button(
                        onClick = { onGenerationSelected(generation) },
                        modifier = Modifier.background(backgroundColor)
                    ) {
                        Text(text = generation.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            PokemonGeneration.values().forEach { generation ->
                if (generation in PokemonGeneration.SEVENTH..PokemonGeneration.NINTH) {
                    val backgroundColor = if (selectedGeneration == generation) Color.Green else Color.White
                    Button(
                        onClick = { onGenerationSelected(generation) },
                        modifier = Modifier.background(backgroundColor)
                    ) {
                        Text(text = generation.name)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScenePreview() {
    MainScene()
}
