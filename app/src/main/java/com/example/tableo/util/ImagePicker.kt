package com.example.tableo.util

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode

/**
 * Öffnet den Photo Picker des Handys.
 *
 * @param onImagePicked Bekommt den Inhalt des gewählten Bildes, null wenn abgebrochen
 * @return Funktion, die die Auswahl öffnet
 */
@Composable
fun rememberImagePicker(onImagePicked: (ByteArray?) -> Unit): () -> Unit {
    if (LocalInspectionMode.current) return {}

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Bild gleich als Bytes lesen, so geht es direkt an Firebase Storage
        val bytes = uri?.let { context.contentResolver.openInputStream(it)?.use { stream -> stream.readBytes() } }
        onImagePicked(bytes)
    }
    return { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
}
