package rs.raf.marko_doder_rn12218.presentation.view.state

import rs.raf.marko_doder_rn12218.data.models.Memory

sealed class ToggleMemoryState {
    data class Success(val memories: List<Memory>): ToggleMemoryState()
    data class Error(val message: String): ToggleMemoryState()
}