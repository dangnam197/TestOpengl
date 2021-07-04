package com.example.testopengl

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testopengl.opengl.GLRender
import com.example.testopengl.opengl.GLUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val glSurfaceView:GLSurfaceView = findViewById(R.id.glSurface)
        glSurfaceView.setEGLContextClientVersion(2)
        val render = GLRender(this)
        glSurfaceView.setRenderer(render)
    }
}