package com.example.coffeeprotectionandanalysissystem.ui.home
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.coffeeprotectionandanalysissystem.R
import com.example.coffeeprotectionandanalysissystem.adapter.ImageSlideAdapter
import com.example.coffeeprotectionandanalysissystem.databinding.FragmentHomeBinding
import com.example.coffeeprotectionandanalysissystem.response.WeatherResponse
import com.example.coffeeprotectionandanalysissystem.view.camera.CameraActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*
class HomeFragment : Fragment() {

    private val updateIntervalMillis: Long = 5000
    private val handler = Handler(Looper.getMainLooper())
    private val updateRunnable = object : Runnable {
        override fun run() {
            binding.viewPager.currentItem = (binding.viewPager.currentItem + 1) % imageList.size
            updateWeatherUI(viewModel.weatherResponse.value)
            handler.postDelayed(this, updateIntervalMillis)
        }
    }

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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLastLocation()
        } else {
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private val imageList = listOf(
        R.drawable.poster1,
        R.drawable.poster2,
        R.drawable.poster3
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        viewModel.weatherResponse.removeObservers(viewLifecycleOwner)
        viewModel.weatherResponse.observe(viewLifecycleOwner) { weatherResponse ->
            weatherResponse?.let {
                updateWeatherUI(it)
            }
        }

        binding.ambilgambar.setOnClickListener { startCameraX() }

        // Setup ViewPager2 with ImageSliderAdapter
        val imageSliderAdapter = ImageSlideAdapter(imageList)
        binding.viewPager.adapter = imageSliderAdapter

        // Start auto scrolling of ViewPager2
        startAutoScroll()

        return root
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
        (requireContext() as Activity).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
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

    private fun updateWeatherUI(weatherResponse: WeatherResponse?) {
        weatherResponse?.let {
            val current = it.current
            val condition = current?.condition
            val location = it.location
            val locationText = "${location?.name ?: "Unknown location"}, ${location?.country ?: "Unknown country"}"

            // Mendapatkan waktu saat ini
            val currentTimeMillis = System.currentTimeMillis()
            val formatter = SimpleDateFormat("HH:mm, dd MMM yyyy ", Locale.getDefault())
            val formattedDate = formatter.format(currentTimeMillis)

            binding.date.text = formattedDate
            binding.weatherDesc.text = condition?.text ?: "N/A"

            val roundedTempC = current?.tempC?.let { Math.round(it as Double) }
            binding.weather.text = "${roundedTempC ?: "N/A"}°C"

            binding.location.text = locationText
        }
    }


    private fun startAutoScroll() {
        handler.postDelayed(updateRunnable, updateIntervalMillis)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        handler.removeCallbacks(updateRunnable) // Stop the auto scroll when fragment is destroyed
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
        handler.postDelayed(updateRunnable, updateIntervalMillis) // Start the handler when fragment is resumed
        Log.d("HomeFragment", "Resuming")
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(updateRunnable) // Stop the handler when fragment is paused
        Log.d("HomeFragment", "Pausing")
    }

    companion object {
        private const val PERMISSION_REQUEST_LOCATION = 100
        private const val CAMERAX_RESULT = 101
    }
}
