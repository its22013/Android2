package jp.ac.it_college.std.s22013.poke_api_silet

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Dao
import jp.ac.it_college.std.s22013.poke_api_silet.api.GamesGroup
import jp.ac.it_college.std.s22013.poke_api_silet.api.PokemonGroup
import jp.ac.it_college.std.s22013.poke_api_silet.database.PokeRoomDatabase
import jp.ac.it_college.std.s22013.poke_api_silet.database.entity.Poke
import jp.ac.it_college.std.s22013.poke_api_silet.ui.theme.PokeApiSiletTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeApiSiletTheme {
                PokeNavigation()
            }
            }
        }
    }