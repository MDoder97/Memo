package rs.raf.marko_doder_rn12218.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.marko_doder_rn12218.data.models.Memory
import rs.raf.marko_doder_rn12218.presentation.view.state.InsertMemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.MemoryState
import rs.raf.marko_doder_rn12218.presentation.view.state.UpdateMemoryState

interface MemoryContract {

    interface ViewModel {

        val memoryState: LiveData<MemoryState>
        val insertMemoryState: LiveData<InsertMemoryState>
        val updateMemoryState: LiveData<UpdateMemoryState>

        fun getAllMemories()
        fun insertMemory(memory: Memory)
        fun updateMemory(memory: Memory)
        fun toggleMemories()
        fun getByTitleAndContent(filter: String)
        fun isNewest() : Boolean
    }
}