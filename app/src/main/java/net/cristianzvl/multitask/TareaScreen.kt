package net.cristianzvl.multitask

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

data class TareasItem(
    val name: String,
    val desc: String,
    val day: String,
    val hour: String
)

@Composable
fun TareaScreen() {
    val tareas_items = listOf(
        TareasItem(
            name = "Tarea gestion",
            desc = "Hacer el punto 10, tablita de firmas o una mamada asi",
            day = "27 Sep",
            hour = "17:00"
        ),
        TareasItem(
            name = "Ingles",
            desc = "Hacer tarea de ingles pag. 61",
            day = "28 Sep",
            hour = "22:00"
        )
    )

    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ){
            items(tareas_items.size){ index ->
                val item = tareas_items[index]
                TareaBody(item)
            }
        }
        FABody()
    }
}



@Composable
private fun FABody(

) {
    var tarea by remember {
        mutableStateOf(false)
    }

    if(tarea){
        DialogAddTarea {
            tarea = !tarea
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                tarea = !tarea
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TareaBody(
    item: TareasItem
) {
    var eliminar by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
            .combinedClickable(
                onClick = {
                    if (eliminar) eliminar = !eliminar
                    else {
                        // abrir la nota para poder editarla
                    }
                },
                onLongClick = { eliminar = !eliminar }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(75.dp)
                .padding(PaddingValues(16.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // nombre y descripcion
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            ) {
                Text(
                    text = item.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = item.desc,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if(eliminar){
                Checkbox(
                    checked = eliminar,
                    onCheckedChange = {}
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.day,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = item.hour,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogAddTarea(
    onClick: () -> Unit
) {
    Dialog(
        onDismissRequest = { /*TODO*/ },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.size(8.dp))

            // icono para cerrar el dialog
            Row(
                modifier = Modifier
                    .padding(PaddingValues(8.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        onClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(
                        text = "Hoy",
                        style = typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(
                        text = "17:00",
                        style = typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))
            }

            // caja de texto titlo de la nota
            Row(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
            ) {
                var title by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title_tareas),
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    textStyle = MaterialTheme.typography.headlineSmall,
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            // caja de texto de la descripcion de la nota
            var nota by remember {
                mutableStateOf("")
            }

            Column(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
            ) {
                OutlinedTextField(
                    value = nota,
                    onValueChange = { nota = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title_tareas),
                            style = typography.titleMedium
                        )
                    },
                    textStyle = typography.titleMedium,
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.9f)
                )
            }

            // boton agregar nota
            // boton agregar voz, video y audio
            var documentos by remember {
                mutableStateOf(false)
            }

            Row(
                modifier = Modifier
                    .padding(
                        vertical = 0.dp,
                        horizontal = 16.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                if(documentos){
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(75.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Outlined.Call, contentDescription = null)
                            Text(
                                text = "Audio",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(75.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Outlined.ThumbUp, contentDescription = null)
                            Text(
                                text = "Video",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(75.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Outlined.AccountBox, contentDescription = null)
                            Text(
                                text = "Foto",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { documentos = !documentos }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null
                        )
                        Text(
                            text = "Galeri",
                            style = typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                IconButton(
                    onClick = {
                        onClick()
                    },
                    modifier = Modifier
                        .width(75.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                        Text(
                            text = "Guardar",
                            style = typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}