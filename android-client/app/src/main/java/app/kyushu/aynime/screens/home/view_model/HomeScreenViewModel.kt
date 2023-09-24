package app.kyushu.aynime.screens.home.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kyushu.aynime.data.remote.gogo_flask.responses.HomeCategories
import app.kyushu.aynime.repository.gogo_flask_repository.GogoFlaskRepository
import app.kyushu.aynime.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: GogoFlaskRepository
) : ViewModel() {
    val homeCats = mutableStateOf<HomeCategories?>(null)
    var requested = false

    fun getHomeCats() {
        viewModelScope.launch {
            repository.getHomeCats().let {result ->
                if (result is Resource.Success) {
                    homeCats.value = result.data
                    Timber.tag("HomeScreenViewModel").d("getHomeCats: ${result.data}")
                } else {
                    homeCats.value = null
                    Timber.tag("HomeScreenViewModel").d("getHomeCats: ${result.message}")
                }
            }
        }
    }
}