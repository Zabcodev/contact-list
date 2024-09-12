package com.zabcode.contactlistapp.core.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

class ImagePicker(
    private val activity: ComponentActivity
) {

    private lateinit var getContent: ActivityResultLauncher<String>

    @Composable
    fun RegisterPicker(onImagePicker: (Bitmap) -> Unit) {
        getContent = activity.registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                activity.contentResolver.openInputStream(uri)?.use {
                    val bmp = BitmapFactory.decodeStream(it)
                    onImagePicker(bmp)
                }
            }
        }
    }

    fun pickImage() {
        getContent.launch("image/*")
    }

}