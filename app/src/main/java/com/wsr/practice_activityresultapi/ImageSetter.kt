package com.wsr.practice_activityresultapi

import android.app.AlertDialog
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.*

class ImageSetter(
    private val activity: FragmentActivity,
    private val imageView: ImageView
    ) : DefaultLifecycleObserver{

    private lateinit var getContent: ActivityResultLauncher<String>
    private lateinit var dispatchTakePicture: ActivityResultLauncher<Uri>

    private val registry = activity.activityResultRegistry
    private var uri: Uri? = null

    override fun onCreate(owner: LifecycleOwner) {
        getContent =
            registry.register(
                "select-key",
                owner,
                ActivityResultContracts.GetContent()
            ){
                it?.let{
                    this.uri = it
                    imageView.setImageURI(uri)
                }
            }

        dispatchTakePicture =
            registry.register(
                "take-keys",
                owner,
                ActivityResultContracts.TakePicture()
            ){
                if (it) {
                    Log.d("takePicture", "Success")
                    imageView.setImageURI(uri)
                } else {
                    Log.d("takePicture", "Failed")
                }
            }
    }

    fun selectImage(){
        val items = arrayOf("画像を選択", "撮影する")

        AlertDialog.Builder(activity)
            .setItems(items) { dialog, which ->
                Log.d("dialog", dialog.toString())
                Log.d("which", which.toString())
                when(which){
                    0 -> getContent.launch("image/*")
                    1 -> takePicture()
                }
            }
            .show()
    }

    private fun takePicture(){
        val filename = UUID.randomUUID().toString() + ".jpg"
        val path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(path, filename)

        uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", file)

        dispatchTakePicture.launch(uri)
        Log.d("Uri", uri.toString())
    }
}