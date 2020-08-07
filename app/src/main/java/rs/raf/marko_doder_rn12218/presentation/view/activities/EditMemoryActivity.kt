package rs.raf.marko_doder_rn12218.presentation.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_edit_memory.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.marko_doder_rn12218.R
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.contract.MemoryContract
import rs.raf.marko_doder_rn12218.presentation.view.fragments.SavedMemoriesListFragment
import rs.raf.marko_doder_rn12218.presentation.viewmodel.MemoryViewModel

class EditMemoryActivity : AppCompatActivity(R.layout.activity_edit_memory), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var memory: Memory

    private var newLocation: LatLng? = null

    companion object {
        const val EDIT_KEY = "editKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init() {
        initMap()
        parseIntent()
        initUI()
    }

    private fun initUI() {
        titleEt.setText(memory.title)
        contentEt.setText(memory.content)
        dateTv.text = memory.date
    }

    private fun parseIntent() {
        intent?.let {
            memory = it.getParcelableExtra(EDIT_KEY)
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.editMemoryMv) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initListeners() {
        map.setOnMapClickListener {
            newLocation = it
            map.clear()
            map.addMarker(MarkerOptions().position(it))
        }
        backBtn.setOnClickListener {
            finish()
        }
        saveBtn.setOnClickListener {
            memory.title = titleEt.text.toString()
            memory.content = contentEt.text.toString()
            if(newLocation != null)
                memory.cordinates = newLocation as LatLng

            val returnIntent = Intent()
            returnIntent.putExtra(SavedMemoriesListFragment.MEMORY_EDIT, memory)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.addMarker(MarkerOptions().position(memory.cordinates))
        moveCamera(memory.cordinates, 12f)
        initListeners()
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }
}