package net.cristianzvl.multitask

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.cristianzvl.multitask.Model.Note
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiNavigationType
import java.time.LocalDateTime

data class NotaItem(
    // val miniatura: ImageVector
    val name: String,
    val desc: String,
    // val date: String,
    val isSelected: Boolean = false
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotaScreen(
    multiViewModel: MultitaskViewModel,
    navigationType: MultiNavigationType
) {
    val scope = rememberCoroutineScope()
    val snackbarHost = remember {
        SnackbarHostState()
    }

    val multiUiState by multiViewModel.uiState.collectAsState()

    val notas_items = multiUiState.notes


    Row {

        // cuerpo de las notas
        Column(
            Modifier.weight(1f)
        ) {
            TopBar()

            Box {

                if(multiUiState.countNotes > 0){
                    // notas
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        items(notas_items.size){ index ->
                            val item = notas_items[index]
                            NotaBody(item)
                        }
                    }
                } else {
                    // tip de notas
                    Column(
                        Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.tip_notes),
                            style = typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                        )
                    }
                }

                // boton para agregar notas
                FABody(multiViewModel)
            }
        }

        // visualizacion previa
        if(navigationType == MultiNavigationType.NAVIGATION_RAIL){
            MultiDetailsScreen(
                title = "Titulo nota",
                date = "Fecha nota",
                desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.notas_title))
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotaBody(
    item: Note
) {
    var eliminar by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
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
                    text = item.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.size(16.dp))
                
                Text(
                    text = item.desc,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.bodyMedium
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
                        style = typography.bodySmall
                    )
                    Text(
                        text = item.month,
                        style = typography.bodySmall
                    )
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FABody(
    multiViewModel: MultitaskViewModel
) {
    var dialog by remember {
        mutableStateOf(false)
    }
    
    if(dialog){
        DialogAddNote(
            multiViewModel = multiViewModel,
            onClick = {
                dialog = !dialog
            }
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            onClick = {
                dialog = !dialog
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogAddNote(
    onClick: () -> Unit,
    multiViewModel: MultitaskViewModel
) {

    var title by remember {
        mutableStateOf("")
    }

    var nota by remember {
        mutableStateOf("")
    }

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

                Text(
                    text = LocalDateTime.now().dayOfMonth.toString() + " " + LocalDateTime.now().month.toString(),
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.size(16.dp))
            }

            // caja de texto titlo de la nota
            Row(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.title_notas),
                            style = typography.headlineSmall
                        )
                    },
                    textStyle = typography.headlineSmall,
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorScheme.surfaceVariant,
                        unfocusedBorderColor = colorScheme.surfaceVariant
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

            Column(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
            ) {
                OutlinedTextField(
                    value = nota,
                    onValueChange = { nota = it },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.desc_notas),
                            style = typography.titleMedium
                        )
                    },
                    textStyle = typography.titleMedium,
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorScheme.surfaceVariant,
                        unfocusedBorderColor = colorScheme.surfaceVariant
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

                // opciones de documentos
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
                                style = typography.bodySmall
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
                                style = typography.bodySmall
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
                                style = typography.bodySmall
                            )
                        }
                    }
                }

                // abrir documentos
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

                // agregar nota
                IconButton(
                    onClick = {
                        multiViewModel.addNote(
                            Note(
                                title,
                                nota,
                                LocalDateTime.now().dayOfMonth.toString(),
                                LocalDateTime.now().month.toString()
                            )
                        )
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
