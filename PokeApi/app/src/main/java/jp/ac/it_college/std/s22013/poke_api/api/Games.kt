package jp.ac.it_college.std.s22013.poke_api.api

import io.ktor.client.call.body
import jp.ac.it_college.std.s22013.poke_api.model.NamedApiResourceList

/**
 * PokeAPI の Games カテゴリにあるエンドポイントへのアクセスを実装
 *
 * いま時点では、世代の一覧を取る機能のみ
 */
object Games {
        suspend fun getPokemonImage(): String {
            // PokeAPIからポケモンの画像URLを取得するコード
            val pokemonId = (1..1000).random() // 1から898の間でランダムなポケモンIDを取得

            // 例として、ポケモンのIDに基づいて画像URLを取得（仮定のURL）
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
        }

        /**
         * /generation エンドポイントへパラメータなしだと
         * [NamedApiResourceList] 型で取得できる。
         */
        public suspend fun getGenerations(): NamedApiResourceList {
            return ApiClient.get("/generation").body()
        }
    }