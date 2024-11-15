package com.example.contactformapp.audiorecorder.recorder

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.io.FileOutputStream

class AndroidAudioRecorder(
    private val context: Context, override val filePath: String
): AudioRecorder {

    private var recorder: MediaRecorder? = null

     private fun createRecorder(): MediaRecorder{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            MediaRecorder(context)
        } else MediaRecorder()
    }

    override fun start(outputFile : File){
        createRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)

            prepare()
            start()

            recorder = this
        }
    }

    override fun stop(){
        recorder?.stop()
        recorder?.reset()
        recorder = null
    }
}