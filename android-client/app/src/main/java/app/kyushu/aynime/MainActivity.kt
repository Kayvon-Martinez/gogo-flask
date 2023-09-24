package app.kyushu.aynime

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.kyushu.aynime.screens.favorites.view.FavoritesScreen
import app.kyushu.aynime.screens.home.view.HomeScreen
import app.kyushu.aynime.screens.search.view.SearchScreen
import app.kyushu.aynime.screens.settings.view.SettingsScreen
import app.kyushu.aynime.ui.theme.AynimeTheme
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AynimeTheme {
                Navigator(
                    screen = HomeScreen,
                ) { _ ->
                    Scaffold(
                        bottomBar = {
                            if (LocalConfiguration.current.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    tonalElevation = 0.dp,
                                ) {
                                    BottomNavigationBarItem(
                                        screen = HomeScreen,
                                        imageVector = Icons.Outlined.Home,
                                        label = "Home"
                                    )
                                    BottomNavigationBarItem(
                                        screen = SearchScreen,
                                        imageVector = Icons.Default.Search,
                                        label = "Search"
                                    )
                                    BottomNavigationBarItem(
                                        screen = FavoritesScreen,
                                        imageVector = R.drawable.baseline_bookmark_border_24,
                                        label = "Favorites")
                                    BottomNavigationBarItem(
                                        screen = SettingsScreen,
                                        imageVector = Icons.Outlined.Settings,
                                        label = "Settings"
                                    )
                                }
                            }
                        }
                    ) { innerPadding ->
                        Row {
                            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                NavigationRail(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    NavigationRailRailItem(
                                        screen = HomeScreen,
                                        imageVector = Icons.Default.Home,
                                        label = "Home"
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    NavigationRailRailItem(
                                        screen = SearchScreen,
                                        imageVector = Icons.Default.Search,
                                        label = "Search"
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    NavigationRailRailItem(
                                        screen = FavoritesScreen,
                                        imageVector = R.drawable.baseline_bookmark_border_24,
                                        label = "Favorites")
                                    Spacer(modifier = Modifier.weight(1f))
                                    NavigationRailRailItem(
                                        screen = SettingsScreen,
                                        imageVector = Icons.Outlined.Settings,
                                        label = "Settings"
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Box(
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                CurrentScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationRailRailItem(screen: Screen, imageVector: Any, label: String) {
    val navigator = LocalNavigator.current!!
    val selected = navigator.lastItem.key == screen.key

    NavigationRailItem(
        selected = selected,
        onClick = { if (!selected) navigator.push(screen) },
        icon = { if (imageVector is ImageVector) Icon(imageVector = imageVector, contentDescription = label) else Icon(painter = painterResource(id = imageVector as Int), contentDescription = label) },
        label = { Text(text = label) },
        colors = NavigationRailItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
private fun RowScope.BottomNavigationBarItem(screen: Screen, imageVector: Any, label: String) {
    val navigator = LocalNavigator.current!!
    val selected = navigator.lastItem.key == screen.key

    NavigationBarItem(
        selected = selected,
        onClick = { if (!selected) navigator.push(screen) },
        icon = { if (imageVector is ImageVector) Icon(imageVector = imageVector, contentDescription = label) else Icon(painter = painterResource(id = imageVector as Int), contentDescription = label) },
        label = { Text(text = label) },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
    )
}