package rs.raf.marko_doder_rn12218.presentation.view.fragments

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_map_input.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.contract.MemoryContract
import rs.raf.marko_doder_rn12218.presentation.viewmodel.MemoryViewModel
import timber.log.Timber
import java.text.DateFormat
import java.util.*


class InputMapFragment : Fragment(R.layout.fragment_map_input), OnMapReadyCallback {

    private val memoryViewModel : MemoryContract.ViewModel by
    sharedViewModel<MemoryViewModel>()

    private lateinit var map: GoogleMap
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private val DEFAULT_ZOOM = 13f

    private var cordinates: LatLng? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        initMap()
    }

    fun initMap() {
        mapView.getMapAsync(this)
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
        map.setMyLocationEnabled(true);
        initListeners()
    }

    fun initListeners() {
        map.setOnMapClickListener {
            cordinates = it
            map.clear()
            map.addMarker(MarkerOptions().position(it))
        }
        saveBtn.setOnClickListener {
            if(titleEt.text.toString() == "" || contentEt.text.toString() == "") {
                Toast.makeText(context, "Sva polja moraju bit popunjena", Toast.LENGTH_LONG).show()
            } else if(cordinates == null) {
                Toast.makeText(context, "Nije označeno mesto na mapi", Toast.LENGTH_LONG).show()
            } else {
                val current = DateFormat.getDateTimeInstance().format(Date())
                val memory = Memory(0,
                    cordinates!!, titleEt.text.toString(), contentEt.text.toString(), current)

                memoryViewModel.insertMemory(memory)

                clearFields()
                Toast.makeText(context, "Lokacija sačuvana", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun clearFields() {
        map.clear()
        titleEt.text.clear()
        contentEt.text.clear()
    }
}