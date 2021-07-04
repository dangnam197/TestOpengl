package com.example.testopengl.opengl

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLSurfaceView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class GLRender(var context: Context): GLSurfaceView.Renderer {
    private val COORDS_PER_VERTEX = 2

    private val COLOR_PER_VERTEX = 3

    private val BYTES_PER_FLOAT = 4

    private val STRIDE: Int = (COORDS_PER_VERTEX + COLOR_PER_VERTEX) * BYTES_PER_FLOAT

    private val A_POSITION = "a_Position"

    private val A_COLOR = "a_Color"

    private var mVertexData: FloatBuffer? = null

    private var programId = 0
    init {
        val TRIANGLE_COORDS = floatArrayOf(
            1f, 1f,1f, 0f,0f,
            -1f, -1f,0f, 1f,0f,
            1f, -1f,0f, 0f,1f,
            1f, 1f,1f, 0f,0f,
            -1f, 1f,0f, 0f,1f,
            -1f, -1f,0f, 1f,0f,
            )

        mVertexData = ByteBuffer
            .allocateDirect(TRIANGLE_COORDS.size * BYTES_PER_FLOAT) //需要多少字节内存
            .order(ByteOrder.nativeOrder())//大小端排序
            .asFloatBuffer()
            .put(TRIANGLE_COORDS);//设置数据
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexCode = GLUtils.loadStringAsset(context.resources,"gl/vertex1.glsl")
        val shaderCode = GLUtils.loadStringAsset(context.resources,"gl/shader1.glsl")
        programId = GLUtils.loadProgram(vertexCode,shaderCode)


        val aPosition = GLES20.glGetAttribLocation(programId, A_POSITION)
        mVertexData!!.position(0)
        GLES20.glVertexAttribPointer(
            aPosition,
            COORDS_PER_VERTEX,  //用几个偏移描述一个顶点
            GLES20.GL_FLOAT,  //顶点数据类型
            false,
            STRIDE,  //一个顶点需要多少个字节偏移
            mVertexData //分配的buffer
        )

        //开启顶点着色器的attribute

        //开启顶点着色器的attribute
        GLES20.glEnableVertexAttribArray(aPosition)

        val aColor = GLES20.glGetAttribLocation(programId, A_COLOR)
        mVertexData!!.position(COORDS_PER_VERTEX)
        GLES20.glVertexAttribPointer(aColor, COLOR_PER_VERTEX, GL_FLOAT, false, STRIDE, mVertexData)
        GLES20.glEnableVertexAttribArray(aColor)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height);
    }

    override fun onDrawFrame(gl: GL10?) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,6);
    }
}