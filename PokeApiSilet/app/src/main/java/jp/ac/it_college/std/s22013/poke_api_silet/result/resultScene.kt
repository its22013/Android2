package jp.ac.it_college.std.s22013.poke_api_silet.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.std.s22013.poke_api_silet.R
import jp.ac.it_college.std.s22013.poke_api_silet.geration.SelectGenerationScene
import jp.ac.it_college.std.s22013.poke_api_silet.ui.theme.PokeApiSiletTheme

@Composable
fun ResultScene(
    result: Int,
    modifier: Modifier = Modifier) {
    Surface(modifier) {
        Column {
            Text(text = stringResource(id = R.string.score))
            Text(text = stringResource(id = R.string.point, result))
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ResultScenePreview() {
    PokeApiSiletTheme {
        ResultScene(
            result = 0,
            Modifier.fillMaxSize())
    }
}