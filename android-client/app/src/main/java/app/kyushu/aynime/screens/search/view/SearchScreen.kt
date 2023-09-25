package app.kyushu.aynime.screens.search.view

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.screens.search.composables.SearchBar
import app.kyushu.aynime.screens.search.composables.SearchResults
import app.kyushu.aynime.screens.search.view_model.SearchScreenViewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator

object SearchScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current!!

        val viewModel = getViewModel<SearchScreenViewModel>()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SearchBar(onSearch = {
                viewModel.query.value = it
                viewModel.search(viewModel.query.value)
            },
                value = viewModel.query.value,)
            Spacer(modifier = Modifier.height(8.dp))
            if (viewModel.searchResults.value != null) {
                SearchResults(searchResults = viewModel.searchResults.value!!, navigator = navigator)
            }
        }
    }
}