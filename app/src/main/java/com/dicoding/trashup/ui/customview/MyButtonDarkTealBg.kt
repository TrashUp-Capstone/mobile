package com.dicoding.trashup.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.trashup.R

class MyButtonDarkTealBg: AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    private var backgroundTeal : Drawable
    private var textColors: Int = 0

    init {
        backgroundTeal = ContextCompat.getDrawable(context, R.drawable.btn_dark_teal) as Drawable
        textColors = ContextCompat.getColor(context, android.R.color.white)
        setGravity(android.view.Gravity.CENTER);
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = backgroundTeal
        setTextColor(textColors)
    }
}