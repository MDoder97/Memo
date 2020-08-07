package rs.raf.marko_doder_rn12218.presentation.view.state

sealed class UpdateMemoryState {
    data class Success(val message: String): UpdateMemoryState()
    data class Error(val message: String): UpdateMemoryState()
}