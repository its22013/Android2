// WeatherActivity.kt
package jp.ac.it_college.std.s22013.open_weather_api

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_activity)

        val spinner: Spinner = findViewById(R.id.spinnerPrefecture)
        val button: Button = findViewById(R.id.btnGetWeather)

        val prefectures = resources.getStringArray(R.array.prefectures)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prefectures)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        button.setOnClickListener {
            val selectedPrefecture = spinner.selectedItem.toString()
            val selectedPrefectureId = getPrefectureId(selectedPrefecture)
            val intent = Intent(this, WeatherDetailsActivity::class.java)
            intent.putExtra("selectedPrefectureId", selectedPrefectureId)
            startActivity(intent)
        }
    }

    private fun getPrefectureId(prefectureName: String): Int {
        val resources = resources
        val allPrefectures = resources.getStringArray(R.array.prefectures)
        val allPrefectureIds = resources.getIntArray(R.array.city_ids)

        val index = allPrefectures.indexOf(prefectureName)
        return if (index != -1 && index < allPrefectureIds.size) {
            allPrefectureIds[index]
        } else {
            -1 // エラーなどの場合は-1を返す
        }
    }
}