package rs.raf.marko_doder_rn12218.presentation.view.state

sealed class InsertMemoryState {
    data class Success(val message: String): InsertMemoryState()
    data class Error(val message: String): InsertMemoryState()
}