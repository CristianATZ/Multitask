package net.cristianzvl.multitask

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.cristianzvl.multitask.Navigation.NavigationHost
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.ViewModel.UiState
import net.cristianzvl.multitask.ui.theme.MultitaskTheme
import net.cristianzvl.multitask.utils.MultiContentType
import net.cristianzvl.multitask.utils.MultiNavigationType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val multiViewModel = MultitaskViewModel(context)
            val multiUiState by multiViewModel.uiState.collectAsState()

            val windowSize = calculateWindowSizeClass(activity = this)

            MultitaskTheme(multiUiState.currentTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(
                        multiViewModel,
                        multiUiState,
                        windowSize.widthSizeClass
                    )
                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    multiViewModel: MultitaskViewModel,
    multiUiState: UiState,
    windowSize: WindowWidthSizeClass
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = stringResource(id = R.string.bottom_first),
            route = "inicio_screen",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            title = stringResource(id = R.string.bottom_second),
            route = "notas_screen",
            selectedIcon = Icons.Filled.Edit,
            unSelectedIcon = Icons.Outlined.Edit
        ),
        BottomNavItem(
            title = stringResource(id = R.string.bottom_third),
            route = "tareas_screen",
            selectedIcon = Icons.Filled.Build,
            unSelectedIcon = Icons.Outlined.Build
        ),
        BottomNavItem(
            title = stringResource(id = R.string.bottom_fourth),
            route = "ajustes_screen",
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings
        )
    )

    val navHostController = rememberNavController()
    val scope = rememberCoroutineScope()

    val navigationType: MultiNavigationType
    val contentType: MultiContentType

    // inicilizar variables dependiendo de la forma del width
    when(windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = MultiNavigationType.BOTTOM_NAVIGATION
            contentType = MultiContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = MultiNavigationType.BOTTOM_NAVIGATION
            contentType = MultiContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = MultiNavigationType.NAVIGATION_RAIL
            contentType = MultiContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = MultiNavigationType.BOTTOM_NAVIGATION
            contentType = MultiContentType.LIST_ONLY
        }
    }

    if(navigationType == MultiNavigationType.NAVIGATION_RAIL){
        MultiNavigationExtended(
            bottomNavItems,
            multiViewModel,
            navHostController,
            navigationType
        )
    } else {
        MultiNavigationCompact(
            bottomNavItems,
            multiViewModel,
            navHostController,
            navigationType
        )
    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultiNavigationCompact(
    bottomNavItems: List<BottomNavItem>,
    multiViewModel: MultitaskViewModel,
    navHostController: NavHostController,
    navigationType: MultiNavigationType
) {
    Scaffold(
        bottomBar = { BottomBarBody(navHostController,bottomNavItems) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavigationHost(
                navHostController = navHostController,
                multiViewModel = multiViewModel,
                navigationType
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultiNavigationExtended(
    bottomNavItems: List<BottomNavItem>,
    multiViewModel: MultitaskViewModel,
    navHostController: NavHostController,
    navigationType: MultiNavigationType
) {
    Row {
        // rail de navegacion
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = stringResource(id = R.string.rail_title),
                fontWeight = FontWeight.Bold,
                color = colorScheme.primary
            )

            NavigationRail {
                var currentRoute = currentRoute(navHostController = navHostController)

                bottomNavItems.forEach(){ item ->
                    NavigationRailItem(
                        selected = currentRoute == item.route,
                        onClick = { navHostController.navigate(item.route) },
                        icon = {
                            Row {
                                Icon(
                                    imageVector = if(currentRoute == item.route) item.selectedIcon else item.unSelectedIcon,
                                    contentDescription = item.title
                                )

                                Spacer(modifier = Modifier.size(8.dp))

                                Text(
                                    text = item.title
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }

        // conjunto
        NavigationHost(
            navHostController = navHostController,
            multiViewModel = multiViewModel,
            navigationType = navigationType
        )
    }
}

@Composable
fun BottomBarBody(
    navHostController: NavHostController,
    navigationItem: List<BottomNavItem>
) {
    BottomAppBar(

    ) {
        var currentRoute = currentRoute(navHostController = navHostController)

        navigationItem.forEach(){ item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navHostController.navigate(item.route) },
                icon = {
                    Icon(
                        imageVector = if(currentRoute == item.route) item.selectedIcon else item.unSelectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun currentRoute(
    navHostController: NavHostController
): String? {
    val entrada by navHostController.currentBackStackEntryAsState()
    return entrada?.destination?.route
}
