package rs.raf.marko_doder_rn12218.presentation.view.state

import rs.raf.marko_doder_rn12218.data.models.Memory

sealed class MemoryState {
    object Loading: MemoryState()
    data class Success(val memories: List<Memory>): MemoryState()
    data class Error(val message: String): MemoryState()
}