package com.ckh.electronicalbum

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ckh.electronicalbum.databinding.ActivityPhotoFrameBinding
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {
    private val binding by lazy { ActivityPhotoFrameBinding.inflate(layoutInflater)  }

    private val photoImageView = binding.photoImageVIew
    private val backgroundPhotoImageView = binding.backgroundPhotoImageView
    private  var currentPosition = 0
    private val photoList = mutableListOf<Uri>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getPhotoUriFromIntent()

    }
    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize",0)
        for(i in 0..size) {
            intent.getStringExtra("photo$i")?.let{
                photoList.add(Uri.parse(it))
                startTimer()
            }
        }
    }
    private fun startTimer() {
        timer(period = 5*1000){
            runOnUiThread{
                val current = currentPosition
                val next = if (photoList.size <=currentPosition+1) 0 else currentPosition+1

                backgroundPhotoImageView.setImageURI(photoList[current])
                photoImageView.alpha = 0f //투명도가 0이다
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }


}