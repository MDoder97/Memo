package rs.raf.marko_doder_rn12218.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.marko_doder_rn12218.data.models.Memory

interface MemoryRepository {

    fun getAll(): Observable<List<Memory>>
    fun update(note: Memory): Completable
    fun insert(note: Memory): Completable
    fun getByTitleAndContent(filter: String): Observable<List<Memory>>
    fun toggleDate(newest: Boolean): Observable<List<Memory>>
}