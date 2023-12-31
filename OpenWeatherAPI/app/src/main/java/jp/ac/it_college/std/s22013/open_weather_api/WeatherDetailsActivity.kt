package jp.ac.it_college.std.s22013.open_weather_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
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
                result
            } catch (ex: SocketTimeoutException) {
                ex.printStackTrace()
                ""
            } finally {
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
            val weatherIcon = if (weatherArray.length() > 0) {
                weatherArray.getJSONObject(0).getString("icon")
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
            val windSpeedText = "風速: $windSpeed m/s"
            val windDirectionText = "風向: $windDirection°"
            val windGustText = "瞬間風速: $windGust m/s"
            val rainText = "降水確率: ${precipitation}%"

            binding.currentLocation.text = "現在の天気 \n $cityName"

            binding.currentTemperature.text = "気温: ${formatTemperature(temperature)}°C"

            binding.feelsLike.text = "体感温度: ${formatTemperature(feelsLike)}°C"

            binding.pressure.text = "気圧: ${pressure}hPa"

            binding.humidity.text = "湿度: ${humidity}%"

            binding.weather.text = "天気: $weatherDescription"

            binding.windSpeed.text = windSpeedText

            binding.windDirection.text = windDirectionText

            binding.windGust.text = windGustText

            binding.rain.text = rainText

            if (snowfall > 0) {
                binding.snowfall.text = "積雪量: ${snowfall}mm"
            } else {
                binding.snowfall.text = "積雪なし"
            }

            Glide.with(this@WeatherDetailsActivity)
                .load("https://openweathermap.org/img/wn/$weatherIcon.png")
                .into(binding.weatherIcon)

        } else {
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

                forecastDetails.append("${timestampToTime(timestamp)}  $weatherDetails ${getPrecipitation(forecastData)}%\n")
            }

            binding.hourlyForecast.text = ""
            binding.forecastDetails.text = "３時間毎の予報 \n$forecastDetails"

        } else {
            binding.forecastDetails.text = "天気予報情報の取得に失敗しました\n3 Hourly Forecast: 天気予報情報の取得に失敗しました"
        }
    }

    private fun getPrecipitation(forecastData: JSONObject): Double {
        val rain = forecastData.optJSONObject("rain")
        return rain?.optDouble("1h", 0.0) ?: 0.0
    }

    private fun getWeatherDetails(weatherArray: JSONArray): String {
        if (weatherArray.length() > 0) {
            val weatherData = weatherArray.getJSONObject(0)
            return weatherData.getString("description")
        }
        return ""
    }

    private fun timestampToTime(timestamp: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(Date(timestamp * 1000))
    }

    private fun formatTemperature(temperature: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.JAPANESE)
        return numberFormat.format(temperature.toInt())
    }
}
