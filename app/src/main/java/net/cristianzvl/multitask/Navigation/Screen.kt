package net.cristianzvl.multitask.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenu(
    val route: String
){
    object InicioScreen : ItemsMenu(
        route = "inicio_screen"
    )

    object TareaScreen : ItemsMenu(
        route = "tareas_screen"
    )

    object NotaScreen : ItemsMenu(
        route = "notas_screen"
    )

    object AjusteScreen : ItemsMenu(
        route = "ajustes_screen"
    )
}