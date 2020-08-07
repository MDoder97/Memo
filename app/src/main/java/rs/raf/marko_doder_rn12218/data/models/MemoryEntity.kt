package rs.raf.marko_doder_rn12218.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val content: String,
    val date: String
)