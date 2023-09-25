package app.kyushu.aynime.screens.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.screens.home.composables.CategorySwiper
import app.kyushu.aynime.screens.home.view_model.HomeScreenViewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import timber.log.Timber

object HomeScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current!!

        val viewModel = getViewModel<HomeScreenViewModel>()

        if (viewModel.requested.not()) {
            Timber.d("Requesting home cats")
            viewModel.getHomeCats()
            viewModel.requested = true
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            if (viewModel.homeCats.value == null) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            else {
                viewModel.homeCats.value!!.result.forEach() {
                    CategorySwiper(category = it, navigator = navigator)
                }
            }
        }
    }
}