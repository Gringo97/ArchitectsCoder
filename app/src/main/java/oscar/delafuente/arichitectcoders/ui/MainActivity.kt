package oscar.delafuente.arichitectcoders.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import oscar.delafuente.arichitectcoders.R
import oscar.delafuente.arichitectcoders.model.MovieDb
import oscar.delafuente.arichitectcoders.ui.common.CoroutineScopeActivity
import kotlin.coroutines.resume

class MainActivity : CoroutineScopeActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val adapter = MoviesAdapter {
        startActivity<DetailActivity> {
            putExtra(DetailActivity.MOVIE, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        launch {
            val location = getLocation()
            val movies = MovieDb.service
                .listPopularMoviesAsync(
                    getString(R.string.api_key),
                    getRegionFromLocation(location)
                )

            adapter.movies = movies.results
        }

        recycler.adapter = adapter
    }

    private suspend fun getLocation(): Location? {
        val success = requestCoarseLocationPermission()
        return if (success) findLastLocation() else null
    }

    @SuppressLint("MissingPermission")
    private suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }

    private suspend fun requestCoarseLocationPermission(): Boolean =
        suspendCancellableCoroutine { continuation ->
            Dexter
                .withActivity(this@MainActivity)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation.resume(true)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation.resume(false)
                    }
                }
                ).check()
        }

    private fun getRegionFromLocation(location: Location?): String {
        val geocode = Geocoder(this@MainActivity)
        val fromLocation = location?.let {
            geocode.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        }
        return fromLocation?.firstOrNull()?.countryCode ?: "US"
    }
}
