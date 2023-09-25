package app.kyushu.aynime.screens.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import app.kyushu.aynime.data.remote.gogo_flask.responses.Result
import app.kyushu.aynime.screens.info.view.InfoScreen
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage

@Composable
fun CategorySwiper(category: Result, navigator: Navigator) {
    Column{
        Text(text = category.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(4.dp))
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(content = {
            items(category.items.size) { item ->
                AnimeCard(item = category.items[item], navigator = navigator)
            }
        })
    }
}

@Composable
fun AnimeCard(item: Item, navigator: Navigator) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .clickable {
                    navigator.push(InfoScreen(item))
                }
                .width(120.dp)
                .aspectRatio((3 / 4.5).toFloat())
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
                .width(120.dp)
                .padding(5.dp)
        )
    }
}