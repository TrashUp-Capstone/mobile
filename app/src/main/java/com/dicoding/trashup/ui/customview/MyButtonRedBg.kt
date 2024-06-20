package com.dicoding.trashup.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.trashup.R

class MyButtonRedBg: AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    private var backgroundRed : Drawable
    private var textColors: Int = 0

    init {
        backgroundRed = ContextCompat.getDrawable(context, R.drawable.btn_read_bg) as Drawable
        textColors = ContextCompat.getColor(context, android.R.color.white)
        setGravity(android.view.Gravity.CENTER)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundRed
        setTextColor(textColors)
    }
}