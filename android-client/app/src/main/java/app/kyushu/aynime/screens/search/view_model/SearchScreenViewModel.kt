package app.kyushu.aynime.screens.search.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import app.kyushu.aynime.repository.gogo_flask_repository.GogoFlaskRepository
import app.kyushu.aynime.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: GogoFlaskRepository
) : ViewModel() {
    var query = mutableStateOf("")
    var searchResults = mutableStateOf<SearchResults?>(null)

    fun search(query: String) {
        if (query.isEmpty()) return
        viewModelScope.launch {
            repository.search(query).let {result ->
                if (result is Resource.Success) {
                    searchResults.value = result.data
                } else {
                    searchResults.value = null
                }
            }
        }
    }
}