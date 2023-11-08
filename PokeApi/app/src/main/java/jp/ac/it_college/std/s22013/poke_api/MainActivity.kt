package jp.ac.it_college.std.s22013.poke_api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import jp.ac.it_college.std.s22013.poke_api.api.Games
import jp.ac.it_college.std.s22013.poke_api.ui.theme.PokeApiPrototypeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeApiPrototypeTheme {
                MainScene(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainScene(modifier: Modifier = Modifier) {
    var imageUrl by remember {
        mutableStateOf("") // 画像のURLを保持する変数
    }
    val scope = rememberCoroutineScope()

    Surface(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedButton(
                modifier = Modifier.padding(vertical = 8.dp),
                onClick = {
                    scope.launch {
                        imageUrl = Games.getPokemonImage() // ポケモンの画像URLを取得
                    }
                }
            ) {
                Text(text = "押してみろ！")
            }

            if (imageUrl.isNotEmpty()) {
                // 画像URLがある場合、Imageコンポーネントで画像を表示
                Image(
                    painter = rememberImagePainter(
                        data = imageUrl,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else {
                // 画像URLがまだ取得されていない場合、何も表示しない
                Text(text = "No image loaded yet")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScenePreview() {
    PokeApiPrototypeTheme {
        MainScene(Modifier.fillMaxSize())
    }
}
