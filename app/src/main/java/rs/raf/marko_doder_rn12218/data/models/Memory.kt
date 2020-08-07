package rs.raf.marko_doder_rn12218.data.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Memory(
    val id: Long,
    var cordinates: LatLng,
    var title: String,
    var content: String,
    val date: String
) : Parcelable
