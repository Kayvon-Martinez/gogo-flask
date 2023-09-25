package app.kyushu.aynime.screens.info.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.data.remote.gogo_flask.responses.Item
import app.kyushu.aynime.screens.info.composables.GenreItem
import app.kyushu.aynime.screens.info.composables.InfoScreenHeader
import app.kyushu.aynime.screens.info.composables.OtherNameItem
import app.kyushu.aynime.screens.info.view_model.InfoScreenViewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import java.time.LocalDate

class InfoScreen(
    private val item: Item
) : AndroidScreen() {
    private var requested: Boolean by mutableStateOf(false)

    init {
        requested = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val viewModel = getViewModel<InfoScreenViewModel>()

        if (requested.not()) {
            viewModel.getDetails(item.id)
            requested = true
        }

        if (viewModel.details == null) {
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
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    item {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            InfoScreenHeader(imageUrl = item.imageUrl)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .offset(y = (100).dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(120.dp)
                                        .aspectRatio((3 / 4.5).toFloat())
                                        .clip(MaterialTheme.shapes.medium)
                                )
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                        .padding(top = 75.dp)
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(116.dp))
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                            ) {
                                OtherNameItem(otherName = "Released: ${LocalDate.parse(viewModel.details!!.result.released.split(" ")[0]).year}")
                                OtherNameItem(otherName = "Status: ${viewModel.details!!.result.status.title}")
                                OtherNameItem(otherName = if (viewModel.details!!.result.sub) "Sub" else "Dub")
                                OtherNameItem(otherName = viewModel.details!!.result.type.name)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                            ) {
                                for (genre in viewModel.details!!.result.genres) {
                                    GenreItem(genre = genre)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Synopsis",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = viewModel.details!!.result.synopsis,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Other Names",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            FlowRow(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier
                            ) {
                                for (otherName in viewModel.details!!.result.otherNames) {
                                    OtherNameItem(otherName = otherName)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}

