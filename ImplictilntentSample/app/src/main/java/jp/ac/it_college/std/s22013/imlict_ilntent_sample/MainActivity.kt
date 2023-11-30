package jp.ac.it_college.std.s22013.imlict_ilntent_sample

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import jp.ac.it_college.std.s22013.implict_ilntent_sample.databinding.ActivityMainBinding
import java.net.URLEncoder
import android.Manifest
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGrantedFineLocation =
            permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false)
        val isGrantedCoarseLocation =
            permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false)

        // どちらかの権限は許可貰えたという場合
        if (isGrantedFineLocation || isGrantedCoarseLocation) {
            requestLocationUpdates()
            return@registerForActivityResult
        }
        // 結局権限の許可を貰えなかった
        Log.w("CHAPTER14", "権限もらえなかったのでいじけました。")
    }

    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btMapSearch.setOnClickListener(::onMapSearchButton)
        binding.btMapShowCurrent.setOnClickListener(::onMapShowCurrentButtonClick)

        // 位置情報取得関連↓
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 5000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.also { location ->
                    // 緯度経度とる
                    latitude = location.latitude
                    longitude = location.longitude

                    // 緯度経度を表示する
                    binding.tvLatitude.text = latitude.toString()
                    binding.tvLongitude.text = longitude.toString()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdates()
    }

    override fun onPause() {
        fusedLocationClient.removeLocationUpdates(locationCallback)

        super.onPause()
    }

    private fun onMapSearchButton(view: View) {
        val searchWord = URLEncoder.encode(
            binding.etSearchWord.text.toString(), "UTF-8"
        )
        val uri = Uri.parse("geo:0,0?q=${searchWord}")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun onMapShowCurrentButtonClick(view: View) {
        val uri = Uri.parse("geo:${latitude},${longitude}")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun requestLocationUpdates() {
        val isGrantedFineLocation = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isGrantedCoarseLocation = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // 位置情報取得の権限のうち、どちらか一方でもあればOKなので位置情報取得開始
        if (isGrantedFineLocation || isGrantedCoarseLocation) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
            return
        }
        // ここまで来たらどの権限もないのでリクエスト
        locationPermissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}