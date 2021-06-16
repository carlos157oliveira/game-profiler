package com.github.carlos157oliveira.gameprofiler.ui

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_PICTURES
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.github.carlos157oliveira.gameprofiler.R
import com.github.carlos157oliveira.gameprofiler.utils.Profile
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import java.io.*


class ProfileResultActivity : AppCompatActivity() {

    private lateinit var avatarImg: ImageView
    private lateinit var txtName: TextView

    private lateinit var sprite: String
    private lateinit var queryString: String

    private lateinit var btnCopyName: Button
    private lateinit var btnSaveImage: Button

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE_CODE = 10100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        this.avatarImg = this.findViewById(R.id.avatarImg)
        this.txtName = this.findViewById(R.id.txtName)
        this.btnCopyName = this.findViewById(R.id.btnCopyName)
        this.btnSaveImage = this.findViewById(R.id.btnSaveImage)

        this.sprite = this.intent.getStringExtra("sprite")!!
        this.queryString = this.intent.getStringExtra("queryString")!!

        this.btnCopyName.setOnClickListener {
            val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Username", this.txtName.text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Texto copiado.", Toast.LENGTH_LONG).show()
        }

        this.btnSaveImage.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    savebitmap(this.avatarImg.drawable.toBitmap())
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            REQUEST_EXTERNAL_STORAGE_CODE
                            )
                }
            }


        }

        startUI()
    }

    private fun startUI() {
        val seed: String
        if (this.sprite == "initials") {
            seed = this.queryString
            this.queryString = ""
        }
        else {
            seed = Profile.generateRandomName(15)
        }
        this.txtName.text = seed
        GlideToVectorYou.justLoadImage(
            this,
            Uri.parse("https://avatars.dicebear.com/api/${sprite}/${seed}.svg?${queryString}"),
            this.avatarImg)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                savebitmap(this.avatarImg.drawable.toBitmap())
            }
        }
    }

    @Throws(IOException::class)
    fun savebitmap(bmp: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            this?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Imagem salva.", Toast.LENGTH_LONG).show()
        }
    }


}