package net.cristianzvl.multitask

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import net.cristianzvl.multitask.Notifications.createChannelNotification
import net.cristianzvl.multitask.Notifications.workAlarm
import net.cristianzvl.multitask.Room.WorksData
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiNavigationType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TareaScreen(multiViewModel: MultitaskViewModel, navigationType: MultiNavigationType) {
    val multiUiState by multiViewModel.uiState.collectAsState()

    val tareas_items = multiUiState.works

    Row {
        Column(
            Modifier.weight(1f)
        ) {
            TopBar()

            Box {
                if(multiUiState.countHomeworks > 0){
                    // tareas sin caducar
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        // lista de tareas pendientes
                        item {
                            Text(
                                text = "Tareas pendientes",
                                style = typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        items(tareas_items.size){ index ->
                            val item = tareas_items[index]
                            if(item.datework >= LocalDate.now()){
                                TareaBody(item,multiViewModel)
                            }
                        }
                        
                        item {
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Tareas caducadas o finalizadas",
                                style = typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        // lista de tareas caducadas o finalizadas
                        items(tareas_items.size){ index ->
                            val item = tareas_items[index]
                            if(item.datework < LocalDate.now()){
                                TareaBody(item,multiViewModel)
                            }
                        }
                    }

                } else {
                    // tip de tareas
                    Column(
                        Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.tip_homeworks),
                            style = typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                        )
                    }
                }

                FABody(multiViewModel)
            }
        }

        if(navigationType == MultiNavigationType.NAVIGATION_RAIL){
            MultiDetailsScreen(
                title = "Titulo tarea",
                date = "Fecha tarea",
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
            Text(text = stringResource(id = R.string.tareas_title))
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FABody(
    multiViewModel: MultitaskViewModel
) {
    var tarea by remember {
        mutableStateOf(false)
    }

    if(tarea){
        DialogAddTarea(
            multiViewModel = multiViewModel,
            onClick = {
                tarea = !tarea
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TareaBody(
    item: WorksData,
    multiViewModel: MultitaskViewModel
) {
    val msg = buildAnnotatedString {
        append(stringResource(id = R.string.lblConfirmarP1_notas) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(item.titlework)
        }
        append(" " + stringResource(id = R.string.lblConfirmarP2_notas))
    }

    var eliminar by remember {
        mutableStateOf(false)
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    if(openDialog){
        DialogAddTarea(
            onClick = { openDialog = !openDialog },
            multiViewModel = multiViewModel,
            update = true,
            tarea = item
        )
    }

    if(eliminar){
        Dialog(
            onDismissRequest = { /*TODO*/ }
        ) {
            Card(
                Modifier.fillMaxWidth(0.85f)
            ) {
                Column(
                    Modifier.padding(PaddingValues(16.dp))
                ) {
                    Text(
                        text = msg,
                        Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    // boton eliminar
                    Button(
                        onClick = {
                            // eliminar
                            multiViewModel.deleteWork(item)
                            eliminar = false
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = stringResource(id = R.string.btnEliminar))
                    }


                    // boton cancelar
                    TextButton(
                        onClick = {
                            eliminar = !eliminar
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = stringResource(id = R.string.btnCancelar))
                    }
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .combinedClickable(
                onClick = {
                    openDialog = !openDialog
                },
                onLongClick = {
                    eliminar = !eliminar
                }
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
                    text = item.titlework,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = item.descwork,
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
                        text = "${item.datework.format(DateTimeFormatter.ofPattern("dd-MMM"))}",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "${item.hour.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogAddTarea(
    onClick: () -> Unit,
    multiViewModel: MultitaskViewModel,
    update: Boolean = false,
    tarea: WorksData = WorksData(0,"","", LocalDate.now(), LocalTime.now())
) {
    // variables para en canal de notificacion
    val context = LocalContext.current
    val idCanal = "CanalTareas"
    val idNotificacion = 0

    // se crea el canal de notificacion en base a las variables anteriores
    LaunchedEffect(Unit){
        createChannelNotification(idCanal,context)
    }

    val timePickerState = rememberTimePickerState()

    var title by remember {
        mutableStateOf(if(update) tarea.titlework else "")
    }

    var desc by remember {
        mutableStateOf(if(update) tarea.descwork else "")
    }

    var hour by remember {
        mutableStateOf(false)
    }
    var hourSelected by remember {
        mutableStateOf(if(update) tarea.hour else LocalTime.now())
    }


    // calendario
    val calendar = Calendar.getInstance()

    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    var date: LocalDate by remember {
        mutableStateOf(if(update) tarea.datework else LocalDate.now())
    }

    val context1 = LocalContext.current
    val datePicker = DatePickerDialog(
        context1,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            date = LocalDate.of(selectedYear, selectedMonth+1, selectedDayOfMonth)
        }, year, month, dayOfMonth
    )


    if(hour){
        DialogSelectHour(
            onDismiss = {
                hour = !hour
            },
            state = timePickerState,
            selected = { hour ->
                hourSelected = hour
            }
        )
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

                TextButton(
                    onClick = { datePicker.show() },
                ) {
                    Text(
                        text = "${date.format(DateTimeFormatter.ofPattern("dd-MMM"))}",
                        style = typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                TextButton(
                    onClick = { hour = !hour },
                ) {
                    Text(
                        text = "${hourSelected.format(DateTimeFormatter.ofPattern("HH:mm"))}",
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

            Column(
                modifier = Modifier
                    .padding(PaddingValues(16.dp))
            ) {
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
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
                // archivos
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

                // abrir archivos
                IconButton(
                    onClick = { documentos = !documentos },
                    modifier = Modifier
                        .width(75.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null
                        )
                        Text(
                            text = "Galeria",
                            style = typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                // guardar tarea
                IconButton(
                    onClick = {
                        if(!update){
                            val item = WorksData(
                                id = 0,
                                titlework = title,
                                descwork = desc,
                                datework = date,
                                hour = hourSelected
                            )
                            workAlarm(
                                context = context,
                                title = item.titlework,
                                longDesc = item.descwork,
                                expiration = item.hour,
                                time = 10000
                            )

                            multiViewModel.addWork(item)
                        } else {
                            val item = WorksData(
                                id = tarea.id,
                                titlework = title,
                                descwork = desc,
                                datework = date,
                                hour = hourSelected
                            )
                            if(title != tarea.titlework || desc != tarea.descwork || hourSelected != tarea.hour || date != tarea.datework){
                                multiViewModel.updateWork(item)
                            }
                        }
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSelectHour(
    onDismiss: () -> Unit,
    state: TimePickerState,
    selected: (LocalTime) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card {
            Column(
                Modifier.padding(PaddingValues(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = state)
                Button(
                    onClick = {
                        selected(
                            LocalTime.of(
                                state.hour,
                                state.minute
                            )
                        )
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
                TextButton(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnCancelar))
                }
            }
        }
    }
}
