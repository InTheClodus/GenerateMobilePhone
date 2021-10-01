package com.lf.generatemobilephone.view

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * @ClassName MyGridView
 * @Description TODO
 * @date 2021/10/1 12:51
 * @Version 1.0
 * @Author 李建新
 */
class MyGridView : GridView {
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, h)
    }
}