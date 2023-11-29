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
import net.cristianzvl.multitask.Room.NoteDB
import net.cristianzvl.multitask.TareaScreen
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationHost(
    navHostController: NavHostController,
    multiViewModel: MultitaskViewModel,
    navigationType: MultiNavigationType
) {

    NavHost(navController = navHostController, startDestination = ItemsMenu.InicioScreen.route){
        composable(route = ItemsMenu.NotaScreen.route){
            NotaScreen(multiViewModel,navigationType)
        }

        composable(route = ItemsMenu.InicioScreen.route){
            InicioScreen(multiViewModel)
        }

        composable(route = ItemsMenu.TareaScreen.route){
            TareaScreen(multiViewModel,navigationType)
        }

        composable(route = ItemsMenu.AjusteScreen.route){
            AjusteScreen(multiViewModel)
        }
    }
}