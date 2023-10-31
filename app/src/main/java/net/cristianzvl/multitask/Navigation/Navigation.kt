package net.cristianzvl.multitask.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.cristianzvl.multitask.AjusteScreen
import net.cristianzvl.multitask.InicioScreen
import net.cristianzvl.multitask.NotaScreen
import net.cristianzvl.multitask.TareaScreen
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiContentType
import net.cristianzvl.multitask.utils.MultiNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(
    navHostController: NavHostController,
    multiViewModel: MultitaskViewModel
) {
    val navigationType: MultiNavigationType
    val contentType: MultiContentType



    NavHost(navController = navHostController, startDestination = ItemsMenu.NotaScreen.route){
        composable(route = ItemsMenu.NotaScreen.route){
            NotaScreen(multiViewModel)
        }

        composable(route = ItemsMenu.InicioScreen.route){
            InicioScreen(multiViewModel)
        }

        composable(route = ItemsMenu.TareaScreen.route){
            TareaScreen(multiViewModel)
        }

        composable(route = ItemsMenu.AjusteScreen.route){
            AjusteScreen(multiViewModel)
        }
    }
}