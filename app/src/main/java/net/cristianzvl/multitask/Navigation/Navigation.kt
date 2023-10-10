package net.cristianzvl.multitask.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.cristianzvl.multitask.AjusteScreen
import net.cristianzvl.multitask.InicioScreen
import net.cristianzvl.multitask.NotaScreen
import net.cristianzvl.multitask.TareaScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = ItemsMenu.NotaScreen.route){
        composable(route = ItemsMenu.NotaScreen.route){
            NotaScreen()
        }

        composable(route = ItemsMenu.InicioScreen.route){
            InicioScreen()
        }

        composable(route = ItemsMenu.TareaScreen.route){
            TareaScreen()
        }

        composable(route = ItemsMenu.AjusteScreen.route){
            AjusteScreen()
        }
    }
}