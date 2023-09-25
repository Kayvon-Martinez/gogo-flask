package app.kyushu.aynime.screens.info.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kyushu.aynime.data.remote.gogo_flask.responses.DetailedItem
import app.kyushu.aynime.repository.gogo_flask_repository.GogoFlaskRepository
import app.kyushu.aynime.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoScreenViewModel @Inject constructor(
    private val repository: GogoFlaskRepository
) : ViewModel() {
    var details by mutableStateOf<DetailedItem?>(null)

    fun getDetails(id: String) {
        details = null
        viewModelScope.launch {
            repository.getDetails(id).let { result ->
                details = if (result is Resource.Success) {
                    result.data
                } else {
                    null
                }
            }
        }
    }
}