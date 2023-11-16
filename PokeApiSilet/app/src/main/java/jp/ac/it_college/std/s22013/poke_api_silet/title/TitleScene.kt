package jp.ac.it_college.std.s22013.poke_api_silet.title

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.std.s22013.poke_api_silet.R
import jp.ac.it_college.std.s22013.poke_api_silet.ui.theme.PokeApiSiletTheme

@Composable
fun TitleScene(modifier: Modifier = Modifier, onTitleClick: () -> Unit = {}) {
    Surface(modifier) {
        // 念のため、縦に並べられるように。
        Column(
            modifier = Modifier
                .fillMaxSize()
                // 通常クリックできないUIについては、Modifier.clickable を指定するとクリック可能になる。
                .clickable(onClick = onTitleClick),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // タイトル
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun TitleScenePreview() {
    PokeApiSiletTheme {
        TitleScene(Modifier.fillMaxSize())
    }
}