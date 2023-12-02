package net.cristianzvl.multitask

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import net.cristianzvl.multitask.Multimedia.AudioPlayer
import net.cristianzvl.multitask.Multimedia.ComposeFileProvider
import net.cristianzvl.multitask.Multimedia.DialogShowAudioSelected
import net.cristianzvl.multitask.Multimedia.DialogShowFileSelected
import net.cristianzvl.multitask.Multimedia.DialogShowImageTake
import net.cristianzvl.multitask.Multimedia.DialogShowVideoTake
import net.cristianzvl.multitask.Multimedia.VideoPlayer
import net.cristianzvl.multitask.Notifications.createChannelNotification
import net.cristianzvl.multitask.Room.NotesData
import net.cristianzvl.multitask.Room.WorksData
import net.cristianzvl.multitask.ViewModel.MultitaskViewModel
import net.cristianzvl.multitask.utils.MultiNavigationType
import java.time.LocalDate
import java.time.LocalDateTime


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

    var notas_items = multiUiState.notes

    Row {

        // cuerpo de las notas
        Column(
            Modifier.weight(1f)
        ) {
            TopBar()

            Box {

                if(notas_items.isNotEmpty()){
                    // notas
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ){
                        items(notas_items.size){ index ->
                            val item = notas_items[index]
                            NotaBody(item,multiViewModel)
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotaBody(
    item: NotesData,
    multiViewModel: MultitaskViewModel
) {

    // mensaje
    val msg = buildAnnotatedString {
        append(stringResource(id = R.string.lblConfirmarP1_notas) + " ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(item.titlenote)
        }
        append(" " + stringResource(id = R.string.lblConfirmarP2_notas))
    }

    var eliminar by remember {
        mutableStateOf(false)
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    var showMultimedia by remember {
        mutableStateOf(false)
    }

    if(openDialog){
        DialogAddNote(
            onClick = { openDialog = !openDialog },
            multiViewModel = multiViewModel,
            nota = item,
            update = true
        )
    }

    if(eliminar){
        DialogDeleteNote(
            onDismiss = { eliminar = !eliminar },
            item = item,
            msg = msg,
            multiViewModel = multiViewModel
        )
    }

    if(showMultimedia){
        DialogShowMultimediaNote(
            onDismiss = { showMultimedia = !showMultimedia },
            item = item
        )
    }

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .combinedClickable(
                onClick = {
                    openDialog = !openDialog
                },
                onLongClick = {
                    eliminar = !eliminar
                },
                onDoubleClick = {
                    showMultimedia = !showMultimedia
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
                    text = item.titlenote,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.size(16.dp))
                
                Text(
                    text = item.descnote,
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
                        text = item.datenote.dayOfMonth.toString(),
                        style = typography.bodySmall
                    )
                    Text(
                        text = item.datenote.month.toString(),
                        style = typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun DialogShowMultimediaNote(
    onDismiss: () -> Unit,
    item: NotesData
) {
    val context = LocalContext.current
    var uri: Uri by remember { mutableStateOf(Uri.EMPTY) }
    var showVideo by remember {
        mutableStateOf(false)
    }

    var showImage by remember {
        mutableStateOf(false)
    }

    var showAudio by remember {
        mutableStateOf(false)
    }

    if(showImage){
        Dialog(
            onDismissRequest = { showImage = !showImage },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(0.6f)
            )
        }
    }

    if(showVideo){
        Dialog(
            onDismissRequest = { showVideo = !showVideo },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            VideoPlayer(
                videoUri = uri,
                modifier = Modifier
                    .fillMaxSize(0.6f)
            )
        }
    }

    if(showAudio){
        Dialog(onDismissRequest = { showAudio = !showAudio }) {
            Card {
                AudioPlayer(audioUri = uri)
            }
        }
    }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .padding(PaddingValues(16.dp))
                    .fillMaxWidth(0.8f)
            ) {
                // imagenes
                Text(
                    text = "Imagenes",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        item.images.forEach { item ->
                            AsyncImage(
                                model = item,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(100.dp)
                                    .clickable {
                                        uri = item
                                        showImage = !showImage
                                    }
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }

                // videos
                Text(
                    text = "Videos",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        item.videos.forEach { item ->
                            Column(
                                Modifier
                                    .height(100.dp)
                                    .width(56.dp)
                                    .background(colorScheme.primary)
                                    .clickable {
                                        uri = item
                                        showVideo = !showVideo
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) { }
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }

                // audios
                Text(
                    text = "Audios",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        item.audios.forEach { item ->
                            Column(
                                Modifier
                                    .height(100.dp)
                                    .width(56.dp)
                                    .background(colorScheme.primary)
                                    .clickable {
                                        uri = item
                                        showAudio = !showAudio
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) { }
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }

                // files
                Text(
                    text = "PDF",
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    item {
                        item.audios.forEach { item ->
                            Column(
                                Modifier
                                    .height(100.dp)
                                    .width(56.dp)
                                    .background(colorScheme.primary)
                                    .clickable {
                                        uri = item
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(uri, "application/pdf") // Cambia el tipo de MIME según el tipo de documento que estás mostrando
                                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        }
                                        context.startActivity(intent)
                                    },
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) { }
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DialogDeleteNote(
    multiViewModel: MultitaskViewModel,
    msg: AnnotatedString,
    onDismiss: () -> Unit,
    item: NotesData
) {
    Dialog(
        onDismissRequest = { onDismiss() }
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
                        multiViewModel.deleteNote(item)
                        onDismiss()
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
    multiViewModel: MultitaskViewModel,
    update: Boolean = false,
    nota: NotesData = NotesData(0,"","", LocalDate.now(), emptyList(), emptyList(), emptyList(), emptyList())
) {
    // variables para en canal de notificacion
    val context = LocalContext.current
    val idCanal = "CanalTareas"

    // se crea el canal de notificacion en base a las variables anteriores
    LaunchedEffect(Unit){
        createChannelNotification(idCanal,context)
    }

    var title by remember {
        mutableStateOf(if(update) nota.titlenote else "")
    }

    var desc by remember {
        mutableStateOf(if(update) nota.descnote else "")
    }

// multimedia
    var uri: Uri by remember { mutableStateOf(Uri.EMPTY) }

    // tomar foto INICIO ---------------------------------------------------------------------------

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listImageUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showImage by remember {
        mutableStateOf(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            uri.let {
                listImageUri = listImageUri + it // Agrega la Uri a la lista
            }
            imageUri = uri
            showImage = !showImage
        }
    }

    if(showImage){
        imageUri?.let {
            DialogShowImageTake(
                onDismiss = { showImage = !showImage },
                imageUri = it
            )
        }
    }
    // tomar foto FIN ------------------------------------------------------------------------------

    // tomar video INICIO --------------------------------------------------------------------------
    var videoUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listVideoUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showVideo by remember {
        mutableStateOf(false)
    }

    val videoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo()
    ) { success ->
        if (success) {
            uri.let {
                listVideoUri = listVideoUri + it // Agrega la Uri a la lista
            }
            videoUri = uri
            showVideo = !showVideo
        }
    }
    if(showVideo){
        DialogShowVideoTake(
            onDismiss = { showVideo = !showVideo },
            videoUri = videoUri
        )
    }
    // tomar video FIN -----------------------------------------------------------------------------


    // seleccionar audio INICIO ------------------------------------------------------------------
    var audioUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listAudioUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showAudio by remember {
        mutableStateOf(false)
    }

    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            // Aquí puedes manejar la URI del archivo seleccionado
            listAudioUri = listAudioUri + it // Agrega la Uri del archivo a la lista
        }
        audioUri = uri
        showAudio = !showAudio
    }

    if(showAudio){
        DialogShowAudioSelected(
            onDismiss = { showAudio = !showAudio },
            fileUri = audioUri
        )
    }
    // seleccionar audio FIN ---------------------------------------------------------------------

    // seleccionar audio INICIO ------------------------------------------------------------------
    var fileUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var listFileUri by remember {
        mutableStateOf(listOf<Uri>())
    }
    var showFile by remember {
        mutableStateOf(false)
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            // Aquí puedes manejar la URI del archivo seleccionado
            listFileUri = listFileUri + it // Agrega la Uri del archivo a la lista
        }
        fileUri = uri
        showFile = !showFile
    }

    if(showFile){
        DialogShowFileSelected(
            onDismiss = { showFile = !showFile }
        )
    }
    // seleccionar audio FIN ---------------------------------------------------------------------

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
                    value = desc,
                    onValueChange = { desc = it },
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
                    // documentos
                    IconButton(
                        onClick = {
                            filePickerLauncher.launch("application/pdf")
                        },
                        modifier = Modifier
                            .width(75.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Outlined.Call, contentDescription = null)
                            Text(
                                text = "PDF",
                                style = typography.bodySmall
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            audioPickerLauncher.launch("audio/*")
                        },
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
                        onClick = {
                            uri = ComposeFileProvider.getImageUri(context)
                            videoLauncher.launch(uri)
                        },
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
                        onClick = {
                            uri = ComposeFileProvider.getImageUri(context)
                            cameraLauncher.launch(uri)
                        },
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

                // agregar nota
                if(!documentos){
                    IconButton(
                        onClick = {
                            if(!update){
                                val item = NotesData(
                                    id = 0,
                                    titlenote = title,
                                    descnote = desc,
                                    datenote = LocalDate.now(),
                                    images = listImageUri,
                                    videos = listVideoUri,
                                    audios = listAudioUri,
                                    files = listFileUri
                                )
                                multiViewModel.addNote(item)
                            } else {
                                val item = NotesData(
                                    id = nota.id,
                                    titlenote = title,
                                    descnote = desc,
                                    datenote = LocalDate.now(),
                                    images = listImageUri,
                                    videos = listVideoUri,
                                    audios = listAudioUri,
                                    files = listFileUri
                                )
                                if(title != nota.titlenote || desc != nota.descnote){
                                    multiViewModel.updateNote(item)
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
}
