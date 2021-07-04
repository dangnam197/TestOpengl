package com.example.testopengl.opengl

import android.content.res.Resources
import android.opengl.GLES20
import android.util.Log
import java.lang.StringBuilder

object GLUtils {
    fun loadStringAsset(res: Resources, path:String):String{
        val stringBuilder = StringBuilder()
        try {
            val inputStream = res.assets.open(path)
            val byteArray = ByteArray(1024)
            var count = 0
            while (true){
                count = inputStream.read(byteArray)
                if (count == -1) break
                val string  = String(byteArray,0,count)
                stringBuilder.append(string)
            }
            val result = stringBuilder.toString().replace("\\r\\n", "\n")
            return result
        } catch (e: Exception) {
        }
        return ""
    }
    private fun loadShader(type:Int, codeStr:String):Int{
        val shader = GLES20.glCreateShader(type)
        if (shader>0){
            GLES20.glShaderSource(shader,codeStr)
            GLES20.glCompileShader(shader)
            val status = IntArray(1)
            GLES20.glGetShaderiv(shader,GLES20.GL_COMPILE_STATUS,status,0)
            if (status[0] == 0){
                GLES20.glDeleteShader(shader)
                return 0
            }
        }
        return shader
    }
    fun loadProgram(vertexCode:String,shaderCode:String):Int{
        val program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, loadShader(GLES20.GL_VERTEX_SHADER, vertexCode))
        GLES20.glAttachShader(program, loadShader(GLES20.GL_FRAGMENT_SHADER,shaderCode))
        GLES20.glLinkProgram(program)
        GLES20.glUseProgram(program)
        val status = IntArray(1)
        return program
    }
}