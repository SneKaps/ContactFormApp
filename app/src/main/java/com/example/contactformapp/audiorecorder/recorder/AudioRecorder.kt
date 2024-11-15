package com.example.contactformapp.audiorecorder.recorder

import java.io.File

interface AudioRecorder {
    abstract val filePath: String

    fun start(outputFile: File)
    fun stop()
}