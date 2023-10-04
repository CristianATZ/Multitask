package net.cristianzvl.multitask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class NotaItem(
    // val miniatura: ImageVector
    val name: String,
    val desc: String,
    val isSelected: Boolean = false
)

@Composable
fun NotaScreen() {
    val notas_items = listOf(
        NotaItem(
            name = "Importante",
            desc = "El dia de hoy tuvimos muchos pedos jajajajajajajajajaj"
        ),
        NotaItem(
            name = "Medio importante",
            desc = "No sabemos que hacer con la aplicacion"
        )
    )


    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ){
            items(notas_items.size){ index ->
                val item = notas_items[index]
                NotaBody(item)
            }
        }
        FABody()
    }
}

@Composable
fun NotaBody(
    item: NotaItem
) {
    Card(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(75.dp)
        ) {

            // nombre y descripcion
            Column {
                Text(text = item.name)
                Text(text = item.desc)
            }
        }
    }
}


@Composable
private fun FABody() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {

            },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null
            )
        }
    }
}