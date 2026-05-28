package com.example.fuellog.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fuellog.R
import com.example.fuellog.models.FuelConsumption
import java.text.SimpleDateFormat
import java.util.*

class FuelConsumptionChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var data: List<FuelConsumption> = emptyList()
    
    private val density = context.resources.displayMetrics.density
    private val dp = { value: Float -> value * density }
    
    private val paddingLeft = dp(60f)
    private val paddingRight = dp(20f)
    private val paddingTop = dp(40f)
    private val paddingBottom = dp(40f)
    
    private val labelSize = dp(12f)
    private val pointRadius = dp(4f)
    private val minWidthPerPoint = dp(60f)

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.primary_orange)
        strokeWidth = dp(3f)
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.accent_purple)
        style = Paint.Style.FILL
    }

    private val axisPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.gray_medium)
        strokeWidth = dp(1.5f)
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.gray_light)
        strokeWidth = dp(0.8f)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.secondary_text)
        textSize = labelSize
    }

    private val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    private val chartPath = Path()
    private val fillPath = Path()

    fun setData(newData: List<FuelConsumption>) {
        this.data = newData
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val desiredWidth = if (data.size > 1) {
            (data.size * minWidthPerPoint + paddingLeft + paddingRight).toInt()
                .coerceAtLeast(MeasureSpec.getSize(widthMeasureSpec))
        } else {
            MeasureSpec.getSize(widthMeasureSpec)
        }
        setMeasuredDimension(desiredWidth, desiredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (data.isEmpty()) return

        val width = width.toFloat()
        val height = height.toFloat()
        
        val chartLeft = paddingLeft
        val chartRight = width - paddingRight
        val chartTop = paddingTop
        val chartBottom = height - paddingBottom
        
        val chartWidth = chartRight - chartLeft
        val chartHeight = chartBottom - chartTop

        val maxVal = data.maxOf { it.liters }
        val maxLiters = if (maxVal <= 0f) 10f else maxVal * 1.2f
        
        val literUnit = context.getString(R.string.liter_unit)

        // --- 1. Отрисовка сетки и значений оси Y ---
        textPaint.textAlign = Paint.Align.RIGHT
        val yStepCount = 5
        for (i in 0..yStepCount) {
            val ratio = i.toFloat() / yStepCount
            val y = chartBottom - (ratio * chartHeight)
            val literValue = ratio * maxLiters
            
            canvas.drawLine(chartLeft, y, chartRight, y, gridPaint)
            
            val label = String.format(Locale.getDefault(), "%.1f %s", literValue, literUnit)
            canvas.drawText(label, chartLeft - dp(8f), y + (labelSize / 3f), textPaint)
        }
        
        canvas.drawLine(chartLeft, chartTop, chartLeft, chartBottom, axisPaint)
        canvas.drawLine(chartLeft, chartBottom, chartRight, chartBottom, axisPaint)

        if (data.size < 1) return

        val stepX = if (data.size > 1) chartWidth / (data.size - 1) else chartWidth / 2
        val points = mutableListOf<PointF>()

        textPaint.textAlign = Paint.Align.CENTER

        data.forEachIndexed { index, item ->
            val x = if (data.size > 1) chartLeft + index * stepX else width / 2
            val y = chartBottom - (item.liters / maxLiters * chartHeight)
            points.add(PointF(x, y))
            
            canvas.drawText(dateFormat.format(Date(item.date)), x, chartBottom + dp(20f), textPaint)
        }

        if (data.size >= 2) {
            chartPath.reset()
            chartPath.moveTo(points[0].x, points[0].y)
            for (i in 0 until points.size - 1) {
                val p1 = points[i]
                val p2 = points[i + 1]
                val controlX = (p1.x + p2.x) / 2
                chartPath.cubicTo(controlX, p1.y, controlX, p2.y, p2.x, p2.y)
            }
            canvas.drawPath(chartPath, linePaint)

            fillPath.set(chartPath)
            fillPath.lineTo(points.last().x, chartBottom)
            fillPath.lineTo(points.first().x, chartBottom)
            fillPath.close()
            
            val orangeColor = ContextCompat.getColor(context, R.color.primary_orange)
            fillPaint.shader = LinearGradient(
                0f, chartTop, 0f, chartBottom,
                Color.argb(80, Color.red(orangeColor), Color.green(orangeColor), Color.blue(orangeColor)),
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
            )
            canvas.drawPath(fillPath, fillPaint)
        }

        points.forEach { point ->
            canvas.drawCircle(point.x, point.y, pointRadius, pointPaint)
        }
    }
}