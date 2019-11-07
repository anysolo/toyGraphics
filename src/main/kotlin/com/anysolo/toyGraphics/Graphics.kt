package com.anysolo.toyGraphics

import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.geom.AffineTransform
import java.io.Closeable
import java.security.cert.TrustAnchor


/** Use this class to draw on a window.
 **/
class Graphics(val window: Window): Closeable {
    private val jdkGc: Graphics2D = if(window.buffered)
            window.pane.bufferStrategy.drawGraphics as Graphics2D
        else
            window.pane.graphics as Graphics2D

    /** Color of the pen. */
    var color: Color = Color(jdkGc.color)

    set(value) {
        field = value
        jdkGc.color = color.jdkColor
    }

    /** Background color. */
    var backgroundColor: Color = Color(jdkGc.background)
        set(value) {
            field = value
            jdkGc.background = color.jdkColor
    }

    /** Change size of the current font */
    fun setFontSize(size: Int) {
        val currentFont = jdkGc.font
        val newFont = currentFont.deriveFont(size.toFloat())
        jdkGc.font = newFont
    }

    /** Change current width of lines and size of dots */
    fun setStrokeWidth(width: Int) {
        val stroke = BasicStroke(width.toFloat())
        jdkGc.stroke = stroke
    }

    /** Finish drawing.
     *
     * If window is in buffered mode then pushes the result into the buffer.
     * You always should call this method after you done drawing. In buffered mode you call this method
     * after you finished drawing a frame.
     **/
    override fun close() {
        if(window.buffered) {
            if (!window.pane.bufferStrategy.contentsLost())
                window.pane.bufferStrategy.show()
        }

        jdkGc.dispose()
        window.sync()
    }

    /** Fills the window with background color */
    fun clear() {
        val oldColor = color
        jdkGc.color = jdkGc.background

        try {
            drawRect(0, 0, window.width, window.height, fill = true)
        }
        finally {
            color = oldColor
        }
    }

    /**
     * Draws a dot.
     *
     * Draws a line with of minimal length aka dot.
     */
    fun drawDot(x: Int, y: Int) {
        jdkGc.drawLine(x, y, x, y)
        window.doAutoSync()
    }

    fun drawDot(p: Point) = drawDot(p.x, p.y)

    /** Draws line from x1,y1 to x2,y2. */
    fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int) {
        jdkGc.drawLine(x1, y1, x2, y2)
        window.doAutoSync()
    }

    fun drawLine(p1: Point, p2: Point) = drawLine(p1.x, p1.y, p2.x, p2.y)

    /**
     * Draws a rect.
     *
     * if fill==true fills contents of the rect with current color.
     */
    fun drawRect(x: Int, y: Int, width: Int, height: Int, fill: Boolean = false) {
        if(fill)
            jdkGc.fillRect(x, y, width, height)
        else
            jdkGc.drawRect(x, y, width, height)

        window.doAutoSync()
    }

    fun drawRect(r: Rect, fill: Boolean = false) =
        drawRect(r.point.x, r.point.y, r.size.width, r.size.height, fill)

    /**
     * Draws the text.
     *
     * Draws the text starting from x, y
     */
    fun drawText(x: Int, y: Int, string: String) {
        jdkGc.drawString(string, x, y)
        window.doAutoSync()
    }

    fun drawText(p: Point, string: String) = drawText(p.x, p.y, string)

    /**
     * Draws an image.
     *
     * Recommended file formats: PNG, GIF and JPEG.
     **/
    fun drawImage(x: Int, y: Int, image: Image) {
        jdkGc.drawImage(image.jdkImage, x, y, null)
        window.doAutoSync()
    }

    /**
     * Draws rotated image.
     *
     * Draws an image rotated on [angle] measured in radians. angle=Math.PI will be a half of the full circle.
     * The image rotates coordinates around an anchor point. You can set the anchor point using
     * [anchorx] and [anchory]. By default the anchor point is 0,0, meaning the image rotates around its top left
     * corner.
     * Recommended file formats: PNG, GIF and JPEG.
     **/
    fun drawImage(x: Int, y: Int, image: Image, angle: Double, anchorx: Int = 0, anchory: Int = 0) {
        val transform = AffineTransform()
        transform.setToTranslation(x.toDouble(), y.toDouble())
        transform.rotate(angle, (anchorx - x).toDouble(), (anchory - y).toDouble())

        jdkGc.drawImage(image.jdkImage, transform, null)
        window.doAutoSync()
    }

    /**
     * Draws oval inside invisible rect.
     */
    fun drawOval(x: Int, y: Int, width: Int, height: Int, fill: Boolean = false) {
        if(fill)
            jdkGc.fillOval(x, y, width, height)
        else
            jdkGc.drawOval(x, y, width, height)

        window.doAutoSync()
    }

    fun rotate(angle: Double) {
        jdkGc.rotate(angle)
    }
}
