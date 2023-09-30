package net.cristianzvl.multitask.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ItemsMenu(
    val icon: ImageVector,
    val title: String,
    val route: String
){
    object InicioScreen : ItemsMenu(
        icon = Icons.Outlined.Home,
        title = "Inicio",
        route = "inicio_screen"
    )

    object TareaScreen : ItemsMenu(
        icon = Icons.Outlined.Edit,
        title = "Tareas",
        route = "tareas_screen"
    )

    object NotaScreen : ItemsMenu(
        icon = Icons.Outlined.Home,
        title = "Notas",
        route = "notas_screen"
    )
}