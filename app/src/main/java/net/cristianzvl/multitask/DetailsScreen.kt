package net.cristianzvl.multitask

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgument
import net.cristianzvl.multitask.Room.NotesData
import net.cristianzvl.multitask.Room.WorksData
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiNavigationType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultiDetailsScreenNotes(
    item: NotesData,
    multiViewModel: MultitaskViewModel,
    changePreview: (NotesData) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(PaddingValues(16.dp))
    ) {
        if(item.id != -1){
            LazyColumn {
                item {
                    DetailsScreenHeader(item.titlenote,item.datenote.toString())
                    DetailsScreenBody(item.descnote)
                    if(item.id != -1){
                        DetailsScreenButtonsNotes(item, multiViewModel){ item ->
                            changePreview(item)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay elemento seleccionado.",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MultiDetailsScreenWorks(
    item: WorksData,
    multiViewModel: MultitaskViewModel,
    navigationType: MultiNavigationType,
    changePreview: (WorksData) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .padding(PaddingValues(16.dp))
    ) {
        if(item.id != -1){
            LazyColumn {
                item {
                    DetailsScreenHeader(item.titlework,item.datework.toString())
                    DetailsScreenBody(item.descwork)
                    if(item.id != -1){
                        DetailsScreenButtonsWorks(item, multiViewModel, navigationType){ item ->
                            changePreview(item)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No hay elemento seleccionado.",
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreenButtonsNotes(
    item: NotesData,
    multiViewModel: MultitaskViewModel,
    changePreview: (NotesData) -> Unit
) {
// mensaje
    val msg = buildAnnotatedString {
        append(stringResource(id = R.string.lblConfirmarP1_notas) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(item.titlenote)
        }
        append(" " + stringResource(id = R.string.lblConfirmarP2_notas))
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    var eliminar by remember {
        mutableStateOf(false)
    }

    if(eliminar){
        DialogDeleteNote(
            onDismiss = { eliminar = !eliminar },
            item = item,
            msg = msg,
            multiViewModel = multiViewModel
        ) { item ->
            changePreview(item)
        }
    }

    if(openDialog){
        DialogAddNote(
            onClick = { openDialog = !openDialog },
            multiViewModel = multiViewModel,
            update = true,
            nota = item
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(8.dp)),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                openDialog = !openDialog
            }
        ) {
            Text(
                text = stringResource(id = R.string.edit_button)
            )
        }
        
        Spacer(modifier = Modifier.size(8.dp))

        Button(
            onClick = {
                eliminar = !eliminar
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.error,
                contentColor = colorScheme.onError
            )
        ) {
            Text(
                text = stringResource(id = R.string.delete_button)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreenButtonsWorks(
    item: WorksData,
    multiViewModel: MultitaskViewModel,
    navigationType: MultiNavigationType,
    changePreview: (WorksData) -> Unit,
) {
// mensaje
    val msg = buildAnnotatedString {
        append(stringResource(id = R.string.lblConfirmarP1_notas) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(item.titlework)
        }
        append(" " + stringResource(id = R.string.lblConfirmarP2_notas))
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    var eliminar by remember {
        mutableStateOf(false)
    }

    if(eliminar){
        DialogDeleteWork(
            onDismiss = { eliminar = !eliminar },
            item = item,
            msg = msg,
            multiViewModel = multiViewModel
        ) { item ->
            changePreview(item)
        }
    }

    if(openDialog){
        DialogAddWork(
            onClick = { openDialog = !openDialog },
            multiViewModel = multiViewModel,
            update = true,
            tarea = item,
            navigationType = navigationType
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(8.dp)),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = {
                openDialog = !openDialog
            }
        ) {
            Text(
                text = stringResource(id = R.string.edit_button)
            )
        }

        Spacer(modifier = Modifier.size(8.dp))

        Button(
            onClick = {
                eliminar = !eliminar
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.error,
                contentColor = colorScheme.onError
            )
        ) {
            Text(
                text = stringResource(id = R.string.delete_button)
            )
        }
    }
}

@Composable
fun DetailsScreenBody(desc: String) {
    Text(
        text = desc,
        style = typography.bodyMedium,
        color = colorScheme.outline,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun DetailsScreenHeader(title: String, date: String) {
    Row(
        modifier = Modifier
            .padding(PaddingValues(16.dp))
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(
                24.dp
            )
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = 8.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = typography.labelMedium
            )
            Text(
                text = date,
                style = typography.labelMedium,
                color = colorScheme.outline
            )
        }
    }
}