package net.cristianzvl.multitask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.cristianzvl.multitask.Navigation.NavigationHost
import net.cristianzvl.multitask.ui.theme.MultitaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultitaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
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

    Scaffold(
        bottomBar = { BottomBarBody(navHostController,bottomNavItems) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavigationHost(navHostController = navHostController)
        }
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
