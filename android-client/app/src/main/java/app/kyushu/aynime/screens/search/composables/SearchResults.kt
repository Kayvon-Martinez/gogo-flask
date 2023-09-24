package app.kyushu.aynime.screens.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.data.remote.gogo_flask.responses.Item
import app.kyushu.aynime.data.remote.gogo_flask.responses.ResultXX
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import coil.compose.AsyncImage

@Composable
fun SearchResults(searchResults: SearchResults) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(searchResults.result.size) { index ->
                SearchResult(searchResults.result[index])
            }
        }
    )
}

@Composable
fun SearchResult(item: ResultXX) {
    Column(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .height(180.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
            )
            Text(text = "${if (item.sub) "Sub" else "Dub"}${" • ${item.numOfEps} eps"}",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(5.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp)
            )
        }
        Text(
            text = item.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}