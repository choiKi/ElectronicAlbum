package com.ckh.electronicalbum

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val addPhotoBtn: Button by lazy{findViewById(R.id.addPhotoBtn)}
    private val startPhotoFrameBTN:Button by lazy { findViewById(R.id.startPhotoFrameBtn) }
    private val imageViewList:List<ImageView> by lazy {
        mutableListOf<ImageView>().apply{
            add(findViewById(R.id.photo1))
            add(findViewById(R.id.photo2))
            add(findViewById(R.id.photo3))
            add(findViewById(R.id.photo4))
            add(findViewById(R.id.photo5))
            add(findViewById(R.id.photo6))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotoBtn()
        initStartPhotoFrameBtn()
    }

    private fun initAddPhotoBtn() {
        addPhotoBtn.setOnClickListener {
            when{
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    navigatePhoto()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) ->
                {
                    showPermissionContextPopUp()
                }else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
                  }
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1000 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    navigatePhoto()
                }
            }else -> {
                Toast.makeText(this,"권한을 거부하였습니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun navigatePhoto(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,2000)
    }
    private fun showPermissionContextPopUp() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다")
            .setMessage("앱에서 사진을 불러오기위해 권한이 필요합니다")
            .setPositiveButton("동의하기"){ _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1000)
            }
            .setNegativeButton("취소하기"){_,_ -> }
            .create()
            .show()
    }
    private fun initStartPhotoFrameBtn() {

    }
}