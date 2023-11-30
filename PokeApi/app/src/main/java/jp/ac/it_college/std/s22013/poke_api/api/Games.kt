package jp.ac.it_college.std.s22013.poke_api.api

import io.ktor.client.call.body
import jp.ac.it_college.std.s22013.poke_api.model.NamedApiResourceList

/**
 * PokeAPI の Games カテゴリにあるエンドポイントへのアクセスを実装
 *https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$randomPokemonId.png
 * https://img-monst.appbank.net/images/monster/big/1.png?cb=20170719.01 モンスト
 * https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png
 * いま時点では、世代の一覧を取る機能のみ
 */
object Games {

    suspend fun getSilhouetteImageForGeneration(generation: Int): String {
        val pokemonIds = when (generation) {
            0 -> 1..1017
            1 -> 1..151
            2 -> 152..251
            3 -> 252..386
            4 -> 387..493
            5 -> 494..649
            6 -> 650..721
            7 -> 722..809
            8 -> 810..905
            9 -> 906..1017

            // 他の世代に対しても同様に追加してください
            else -> 1..1017 // デフォルトは1から386までを全世代として扱う
        }
        val randomPokemonId = pokemonIds.random()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${randomPokemonId}.png"
    }
        suspend fun getPokemonImage(generation: Int): String {
        // 仮の実装: ポケモンの世代に応じた画像を取得する処理
        // 実際のAPI呼び出しやデータベースアクセスに置き換えてください
        return "image_url_for_generation_$generation"
    }
        suspend fun getSilhouetteImage(): String {
            val randomPokemonId = (1..1000).random() // 1から1000の範囲からランダムにポケモンIDを選択
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$randomPokemonId.png" // シルエット画像のURLを返す
        }

        suspend fun getAnswerImage(): String {
            val randomPokemonId = (1..1000).random() // 1から898の範囲からランダムにポケモンIDを選択
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$randomPokemonId.png" // 答えの画像のURLを返す
        }
        suspend fun getPokemonImage(): String {
            // モンストの画像URLを取得するコード
            val pokemonId = (1..7386).random() // 1から7386の間でランダムのモンストIDを取得

            // 例として、ポケモンのIDに基づいて画像URLを取得（仮定のURL）
            return "https://img-monst.appbank.net/images/monster/big/$pokemonId.png?cb=20170719.01"
        }

        /**
         * /generation エンドポイントへパラメータなしだと
         * [NamedApiResourceList] 型で取得できる。
         */
        public suspend fun getGenerations(): NamedApiResourceList {
            return ApiClient.get("/generation").body()
        }
    }