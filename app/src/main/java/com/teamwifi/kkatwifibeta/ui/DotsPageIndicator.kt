package com.teamwifi.kkatwifibeta.ui

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.teamwifi.kkatwifibeta.R

/**
 * Created by kyle on 10/11/15.
 */
class DotsPageIndicator(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), ViewPager.OnPageChangeListener {
    private var pages: Int = 0
    private var padding: Int = 0
    private var size: Float = 0.toFloat()
    private var dotColor: Int = 0
    private var dotSelectedColor: Int = 0

    init {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.DotsPageIndicator,
                0, 0)
        try {
            padding = a.getDimension(R.styleable.DotsPageIndicator_padding, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)).toInt()
            size = a.getDimension(R.styleable.DotsPageIndicator_dotSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, resources.displayMetrics))
            dotColor = a.getColor(R.styleable.DotsPageIndicator_dotColor, Color.WHITE)
            dotSelectedColor = a.getColor(R.styleable.DotsPageIndicator_dotSelectedColor, Color.DKGRAY)
        } finally {
            a.recycle()
        }
        this.gravity = Gravity.CENTER
        this.orientation = LinearLayout.HORIZONTAL
    }

    fun setViewPager(pager: ViewPager) {
        pager.addOnPageChangeListener(this)
        this.pages = pager.adapter!!.count
        createDots()
    }

    private fun createDots() {
        for (i in 0..pages - 1) {
            val tv = TextView(context)
            tv.text = context.getString(R.string.dot)
            tv.textSize = size
            tv.setPadding(padding, padding, padding, padding)
            tv.gravity = Gravity.CENTER
            if (i == 0) {
                tv.setTextColor(dotSelectedColor)
            } else {
                tv.setTextColor(dotColor)
            }
            this.addView(tv)
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        for (i in 0..pages - 1) {
            if (i == position)
                (this.getChildAt(i) as TextView).setTextColor(dotSelectedColor)
            else
                (this.getChildAt(i) as TextView).setTextColor(dotColor)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
