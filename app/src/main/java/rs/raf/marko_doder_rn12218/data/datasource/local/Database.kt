package rs.raf.marko_doder_rn12218.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.marko_doder_rn12218.data.models.MemoryEntity

@Database(
    entities = [MemoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class Database : RoomDatabase() {
    abstract fun getMemoryDao() : MemoryDao
}