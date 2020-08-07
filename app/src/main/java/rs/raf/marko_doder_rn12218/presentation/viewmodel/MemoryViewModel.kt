package rs.raf.marko_doder_rn12218.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.data.repositories.MemoryRepository
import rs.raf.marko_doder_rn12218.presentation.contract.MemoryContract
import rs.raf.marko_doder_rn12218.presentation.view.state.InsertMemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.MemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.UpdateMemoryState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MemoryViewModel(
    private val memoryRepository : MemoryRepository
) : ViewModel(), MemoryContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val memoryState: MutableLiveData<MemoryState> = MutableLiveData()
    override val insertMemoryState: MutableLiveData<InsertMemoryState> = MutableLiveData()
    override val updateMemoryState: MutableLiveData<UpdateMemoryState> = MutableLiveData()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    private var newest: Boolean = false

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                memoryRepository
                    .getByTitleAndContent(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    memoryState.value = MemoryState.Success(it)
                },
                {
                    memoryState.value = MemoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllMemories() {
        val subscription = memoryRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    memoryState.value = MemoryState.Success(it)
                },
                {
                    memoryState.value = MemoryState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertMemory(memory: Memory) {
        val subscription = memoryRepository
            .insert(memory)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    insertMemoryState.value = InsertMemoryState.Success("Lokacija sačuvana")
                },
                {
                    insertMemoryState.value = InsertMemoryState.Error("Greška pri dodavanju")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun updateMemory(memory: Memory) {
        val subscription = memoryRepository
            .update(memory)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    updateMemoryState.value = UpdateMemoryState.Success("Lokacija ažurirana")
                },
                {
                    updateMemoryState.value = UpdateMemoryState.Error("Greška pri ažuriranju")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun toggleMemories() {
        newest = !newest
        val subscription = memoryRepository
            .toggleDate(newest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    memoryState.value = MemoryState.Success(it)
                },
                {
                    memoryState.value = MemoryState.Error("Greška pri ažuriranju")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getByTitleAndContent(filter: String) {
        publishSubject.onNext(filter)
    }

    override fun isNewest() : Boolean {
        return newest
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}