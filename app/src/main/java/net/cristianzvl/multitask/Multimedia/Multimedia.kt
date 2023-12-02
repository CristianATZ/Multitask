package net.cristianzvl.multitask.Multimedia

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import net.cristianzvl.multitask.R



// GUARDAR LA MULTIMEDIA TOMADA
// funcion para guardar los videos en la galeria
fun saveVideoToGallery(videoUri: Uri, context: Context, displayName: String) {
    // Insertar el video en la galería utilizando MediaStore.Video.Media
    val contentResolver = context.contentResolver
    val videoContentValues = ContentValues().apply {
        put(MediaStore.Video.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
    }

    val videoUriResult = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoContentValues)

    // Copiar el contenido del video a la ubicación proporcionada por el contentResolver
    contentResolver.openOutputStream(videoUriResult!!)?.use { outputStream ->
        contentResolver.openInputStream(videoUri)?.use { inputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    // Notificar al sistema del nuevo video para que aparezca en la Galería
    MediaScannerConnection.scanFile(
        context,
        arrayOf(videoUriResult.path),
        arrayOf("video/mp4"),
        null
    )
}

// funcion para guardar la imagen en la galeria
fun saveImageToGallery(uri: Uri, context: Context, displayName: String) {
    // Insertar la imagen en la galería utilizando MediaStore.Images.Media
    val contentResolver = context.contentResolver
    val imageContentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    val imageUriResult = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageContentValues)

    // Copiar el contenido de la imagen a la ubicación proporcionada por el contentResolver
    contentResolver.openOutputStream(imageUriResult!!)?.use { outputStream ->
        contentResolver.openInputStream(uri)?.use { inputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    // Notifica al sistema de la nueva imagen para que aparezca en la Galería
    MediaScannerConnection.scanFile(
        context,
        arrayOf(imageUriResult.path),
        arrayOf("image/jpeg"),
        null
    )
}

// DIALOGOS PARA MOSTRAR LA MULTIMEDIA
@Composable
fun DialogShowAudioSelected(
    onDismiss: () -> Unit,
    fileUri: Uri?
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                //Text(text = "Documento PDF cargado con exito.")
                AudioPlayer(
                    audioUri = fileUri
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowVideoTake(
    onDismiss: () -> Unit,
    videoUri: Uri?
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                VideoPlayer(
                    videoUri = videoUri,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowImageTake(
    onDismiss: () -> Unit,
    imageUri: Uri
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.size(16.dp))

                // guardar uri
                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}

@Composable
fun DialogShowFileSelected(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            Column(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.7f)
                    .padding(PaddingValues(16.dp))
            ) {
                Text(text = "Documento PDF cargado con exito.")

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = {
                        onDismiss()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = stringResource(id = R.string.btnAceptar))
                }
            }
        }
    }
}