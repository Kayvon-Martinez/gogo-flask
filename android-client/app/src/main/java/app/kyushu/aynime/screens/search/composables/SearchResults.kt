package app.kyushu.aynime.screens.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.data.remote.gogo_flask.responses.Item
import app.kyushu.aynime.data.remote.gogo_flask.responses.ResultXX
import app.kyushu.aynime.data.remote.gogo_flask.responses.SearchResults
import app.kyushu.aynime.screens.info.view.InfoScreen
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchResults(searchResults: SearchResults, navigator: Navigator, modifier: Modifier = Modifier) {
    FlowRow(
        maxItemsInEachRow = if (LocalConfiguration.current.screenWidthDp > 600) 6 else 3,
        modifier = modifier
            .fillMaxWidth(),
        content = {
            repeat(searchResults.result.size) { index ->
                SearchResult(item = searchResults.result[index], navigator = navigator)
            }
        }
    )
}

@Composable
fun SearchResult(item: ResultXX, navigator: Navigator, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .width(
                if (LocalConfiguration.current.screenWidthDp > 600) LocalConfiguration.current.screenWidthDp.dp / 6 - 16.dp
                else
                LocalConfiguration.current.screenWidthDp.dp / 3 - 6.dp)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .clickable {
                    navigator.push(InfoScreen(
                        Item(
                            id = item.id,
                            title = item.title,
                            imageUrl = item.imageUrl,
                            sub = item.sub,
                            numOfEps = item.numOfEps,
                        )))
                }
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
            Text(text = "${if (item.sub) "Sub" else "Dub"}${if (item.numOfEps != null) " • ${item.numOfEps} eps" else ""}",
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