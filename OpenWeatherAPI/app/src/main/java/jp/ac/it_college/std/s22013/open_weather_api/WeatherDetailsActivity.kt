package jp.ac.it_college.std.s22013.open_weather_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import jp.ac.it_college.std.s22013.open_weather_api.databinding.ActivityWeatherDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherDetailsActivity : AppCompatActivity() {
    companion object {
        private const val CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast?lang=ja"
        private const val APP_ID = BuildConfig.APP_ID
    }

    private lateinit var binding: ActivityWeatherDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedPrefectureId = intent.getIntExtra("selectedPrefectureId", -1)
        if (selectedPrefectureId != -1) {
            val currentWeatherUrl = "$CURRENT_WEATHER_URL&id=$selectedPrefectureId&appid=$APP_ID"
            val forecastUrl = "$FORECAST_URL&id=$selectedPrefectureId&appid=$APP_ID"

            lifecycleScope.launch {
                val currentWeatherResult = weatherInfoBackgroundRunner(currentWeatherUrl)
                showCurrentWeather(currentWeatherResult)

                val forecastResult = weatherInfoBackgroundRunner(forecastUrl)
                showForecast(forecastResult)
            }
        } else {
            // エラーメッセージを表示するなどの処理を追加
            binding.currentLocation.text = "都道府県IDが無効です"
        }
    }

    private suspend fun weatherInfoBackgroundRunner(urlString: String): String {
        return withContext(Dispatchers.IO) {
            val url = URL(urlString)
            val con = url.openConnection() as HttpURLConnection
            con.apply {
                connectTimeout = 1000
                readTimeout = 1000
                requestMethod = "GET"
            }
            try {
                con.connect()
                val result = con.inputStream.reader().readText()
                // 通信成功時に正しく切断する
                result
            } catch (ex: SocketTimeoutException) {
                // 通信がタイムアウトした場合のエラーハンドリング
                ex.printStackTrace()
                ""
            } finally {
                // 通信切断をfinallyブロックに移動
                con.disconnect()
            }
        }
    }

    private fun showCurrentWeather(result: String) {
        if (result.isNotEmpty()) {
            val root = JSONObject(result)
            val cityName = root.getString("name")
            val main = root.getJSONObject("main")
            val temperature = main.getDouble("temp") - 273.15
            val feelsLike = main.getDouble("feels_like") - 273.15
            val pressure = main.getDouble("pressure")
            val humidity = main.getDouble("humidity")
            val weatherArray = root.getJSONArray("weather")
            val weatherDescription = if (weatherArray.length() > 0) {
                weatherArray.getJSONObject(0).getString("description")
            } else {
                ""
            }
            val wind = root.getJSONObject("wind")
            val windSpeed = wind.getDouble("speed")
            val windDirection = wind.getDouble("deg")
            val windGust = wind.optDouble("gust", 0.0)
            val rain = root.optJSONObject("rain")
            val precipitation = rain?.optDouble("1h", 0.0) ?: 0.0
            val snow = root.optJSONObject("snow")
            val snowfall = snow?.optDouble("1h", 0.0) ?: 0.0
            val timestamp = root.getLong("dt")

            // 現在の天気情報を表示
            binding.currentLocation.text = "現在の天気 \n $cityName"

            binding.currentTemperature.text =
                "気温: ${formatTemperature(temperature)}°C"
            binding.feelsLike.text =
                "体感温度: ${formatTemperature(feelsLike)}°C"
            binding.pressure.text = "気圧: ${pressure}hPa"
            binding.humidity.text = "湿度: ${humidity}%"
            binding.weather.text = "天気: $weatherDescription"
            // 他の項目も同様に追加

        } else {
            // エラーメッセージを表示するなどの処理を追加
            binding.currentLocation.text = "現在の天気情報の取得に失敗しました"
        }
    }

    private fun showForecast(result: String) {
        if (result.isNotEmpty()) {
            val root = JSONObject(result)
            val forecastArray = root.getJSONArray("list")

            val forecastDetails = StringBuilder()
            for (i in 0 until forecastArray.length()) {
                val forecastData = forecastArray.getJSONObject(i)
                val timestamp = forecastData.getLong("dt")
                val weatherDetails = getWeatherDetails(forecastData.getJSONArray("weather"))

                // 3時間毎の5日間の天気予報を表示
                forecastDetails.append("${timestampToTime(timestamp)} - $weatherDetails\n")
            }

            // 以下、表示処理
            binding.hourlyForecast.text = ""
            binding.forecastDetails.text = "３時間毎の予報 \n$forecastDetails"

        } else {
            // エラーメッセージを表示するなどの処理を追加
            binding.forecastDetails.text = "天気予報情報の取得に失敗しました\n3 Hourly Forecast: 天気予報情報の取得に失敗しました"

        }
    }

    private fun getWeatherDetails(weatherArray: JSONArray): String {
        if (weatherArray.length() > 0) {
            val weatherData = weatherArray.getJSONObject(0)
            return weatherData.getString("description")
        }
        return ""
    }

    private fun timestampToTime(timestamp: Long): String {
        // タイムスタンプを時間に変換する処理を追加
        // 例: 1637149200 → 2021-11-17 12:00:00
        // 注意: 日付時刻のフォーマットは実際のAPIの仕様に合わせて調整する必要があります。
        // この例では単なる参考としています。
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(Date(timestamp * 1000))
    }

    private fun formatTemperature(temperature: Double): String {
        // 気温を日本語の数字にフォーマットする
        val numberFormat = NumberFormat.getInstance(Locale.JAPANESE)
        return numberFormat.format(temperature.toInt())
    }
}
