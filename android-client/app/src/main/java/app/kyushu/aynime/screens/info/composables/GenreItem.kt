package app.kyushu.aynime.screens.info.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.data.remote.gogo_flask.responses.Genre


@Composable
fun GenreItem(genre: Genre) {
    Text(
        text = genre.name,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    )
}