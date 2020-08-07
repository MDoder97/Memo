package rs.raf.marko_doder_rn12218.presentation.view.fragments

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_saved_locations_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.contract.MemoryContract
import rs.raf.marko_doder_rn12218.presentation.view.state.MemoryState
import rs.raf.marko_doder_rn12218.presentation.viewmodel.MemoryViewModel
import timber.log.Timber


class SavedMemoriesMapFragment : Fragment(R.layout.fragment_saved_locations_map),
    OnMapReadyCallback {

    private val memoryViewModel : MemoryContract.ViewModel by
    sharedViewModel<MemoryViewModel>()

    private lateinit var map: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val DEFAULT_ZOOM = 13f

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapMv.onCreate(savedInstanceState)
        mapMv.onResume()

        initMap()
    }

    fun initMap() {
        mapMv.getMapAsync(this)
    }

    private fun getDeviceLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        try {
            val location: Task<*> = mFusedLocationProviderClient.getLastLocation()
            location.addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentLocation: Location = it.result as Location
                    moveCamera(
                        LatLng(
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude()
                        ),
                        DEFAULT_ZOOM
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Unable to get current location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e.message)
            Toast.makeText(
                context,
                "Unable to get current location",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        getDeviceLocation()
        initObservers()
        initListeners()
    }

    fun initObservers() {
        memoryViewModel.memoryState
            .observe(viewLifecycleOwner, Observer {
                when(it) {
                    is MemoryState.Success -> {
                        showLoadingSpinner(false)
                        renderMarkers(it.memories)
                    }
                    is MemoryState.Error -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is MemoryState.Loading -> {
                        showLoadingSpinner(true)
                    }
                }
            })
        memoryViewModel.getAllMemories()
    }

    private fun renderMarkers(memories: List<Memory>) {
        map.clear()
        for(memory in memories) {
            map.addMarker(MarkerOptions().position(memory.cordinates).title(memory.title).snippet(memory.content))
        }
    }

    private fun showLoadingSpinner(loading: Boolean) {
        pBar.isVisible = loading
    }

    fun initListeners() {
    }

}