package rs.raf.marko_doder_rn12218.data.datasource.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.marko_doder_rn12218.data.models.MemoryEntity

@Dao
abstract class MemoryDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MemoryEntity>): Completable

    @Query("SELECT * FROM memories")
    abstract fun getAll(): Observable<List<MemoryEntity>>

    @Update
    abstract fun update(memoryEntity: MemoryEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(memoryEntity: MemoryEntity): Completable

}