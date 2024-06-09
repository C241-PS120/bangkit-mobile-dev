package com.example.coffeeprotectionandanalysissystem.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHomeBinding
import com.example.coffeeprotectionandanalysissystem.response.WeatherResponse
import com.example.coffeeprotectionandanalysissystem.view.camera.CameraActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
        }
    }

    private var currentImageUri: Uri? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLastLocation()

        viewModel.weatherResponse.removeObservers(viewLifecycleOwner)
        viewModel.weatherResponse.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                updateWeatherUI(it)
            }
        }

        binding.ambilgambar.setOnClickListener { startCameraX() }

        return root
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("LocationPermission", "Location permissions not granted")
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val latitude = it.latitude
                    val longitude = it.longitude
                    Log.d("LocationData", "Latitude: $latitude, Longitude: $longitude")
                    viewModel.fetchWeather(latitude, longitude)
                } ?: Log.d("LocationData", "No last known location")
            }
    }

    private fun updateWeatherUI(weatherResponse: WeatherResponse) {
        // Safely access current, condition, and location properties
        val current = weatherResponse.current
        val condition = current?.condition
        val location = weatherResponse.location
        val locationText = "${location?.name ?: "Unknown location"}, ${location?.country ?: "Unknown country"}"

        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val localTimeDate = location?.localtime?.let { parser.parse(it) }

        val formatter = SimpleDateFormat("HH:mm, dd MMM yyyy ", Locale.getDefault())
        val formattedDate = localTimeDate?.let { formatter.format(it) }

        binding.date.text = formattedDate ?: "N/A"
        binding.weatherDesc.text = condition?.text ?: "N/A"
        binding.weather.text = "${current?.tempC ?: "N/A"}°C"
        binding.location.text = locationText
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_LOCATION && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_LOCATION = 100
        private const val CAMERAX_RESULT = 101
    }
}
