package rs.raf.marko_doder_rn12218.data.repositories

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.marko_doder_rn12218.data.datasource.local.MemoryDao
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.data.models.MemoryEntity
import timber.log.Timber
import java.util.*

class MemoryRepositoryImpl(
    val localDataSource: MemoryDao
) : MemoryRepository {
    override fun getAll(): Observable<List<Memory>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Memory(
                        it.id,
                        LatLng(it.latitude, it.longitude),
                        it.title,
                        it.content,
                        it.date
                    )
                }
            }
    }

    override fun update(memory: Memory): Completable {
        return localDataSource
            .update(MemoryEntity(memory.id, memory.cordinates.latitude, memory.cordinates.longitude,
            memory.title, memory.content, memory.date))
    }

    override fun insert(memory: Memory): Completable {
        return localDataSource
            .insert(
                MemoryEntity(memory.id, memory.cordinates.latitude, memory.cordinates.longitude,
                    memory.title, memory.content, memory.date)
            )
    }

    override fun getByTitleAndContent(filter: String): Observable<List<Memory>> {
        return localDataSource
            .getAll()
            .map {
                it.map {
                    Memory(
                        it.id,
                        LatLng(it.latitude, it.longitude),
                        it.title,
                        it.content,
                        it.date
                    )
                }.filter {
                    it.title.toLowerCase().contains(filter.toLowerCase())
                            || it.content.toLowerCase().contains(filter.toLowerCase())
                }
            }
    }

    override fun toggleDate(newest: Boolean): Observable<List<Memory>> {
        return localDataSource
            .getAll()
            .map {
                if(newest) {
                    it.reversed().map {
                        Memory(
                            it.id,
                            LatLng(it.latitude, it.longitude),
                            it.title,
                            it.content,
                            it.date
                        )
                    }
                } else {
                    it.map {
                        Memory(
                            it.id,
                            LatLng(it.latitude, it.longitude),
                            it.title,
                            it.content,
                            it.date
                        )
                    }
                }

            }
    }
}