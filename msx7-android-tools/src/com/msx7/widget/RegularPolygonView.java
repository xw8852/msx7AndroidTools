package com.msx7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.widget.TextView;

import com.example.listviewtemplate.R;

public class RegularPolygonView extends TextView {
	private int mBorders = 3;
	Rect rect;
	RectF rectF;
	Path path;
	Drawable mBgDrawable;

	public RegularPolygonView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public RegularPolygonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RegularPolygon, defStyle, 0);
		mBorders = a.getInt(R.styleable.RegularPolygon_border, 3);
		mBorders = Math.max(3, mBorders);
		a.recycle();

	}

	public void setBorders(int border) {
		mBorders = Math.max(3, border);
		path = null;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		if (path == null) {
			initPath();
		}
		canvas.save();
		rect = new Rect();
		getFocusedRect(rect);
		Path clip = new Path();
		Matrix matrix = new Matrix();
		matrix.preRotate(-30f, rect.centerX(), rect.centerY());
		clip.addPath(path);
		canvas.clipPath(clip);
		mBgDrawable
				.setBounds(0, 0, (int) (rect.width()), (int) (rect.height()));
		mBgDrawable.draw(canvas);
		super.onDraw(canvas);
	}

	@Override
	public void setBackgroundDrawable(Drawable d) {
		mBgDrawable = d;
		super.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	}

	public void initPath() {
		rect = new Rect();
		getFocusedRect(rect);
		int min = Math.min(rect.width(), rect.height());
		rectF = new RectF(rect.centerX() - min / 2.0f, rect.centerY() - min
				/ 2.0f, rect.centerX() + min / 2.0f, rect.centerY() + min
				/ 2.0f);
		System.out.println(rectF);
		path = null;
		float ave_angle = 360.0f / mBorders;
		// path.moveTo(rectF.left + rectF.width() / 2.0f, rectF.top);
		PointF pointF = null;
		int i = 0;
		while (i < mBorders + 1) {
			// PointF pointF = testcal(rectF, i * ave_angle);
			// path.lineTo(pointF.x, pointF.y);
			pointF = rotatePoint(new RectF(rect), i * ave_angle,
					rect.width() / 2.0f);
			if (path == null) {
				path = new Path();
				path.moveTo(pointF.x, pointF.y);
			} else
				path.lineTo(pointF.x, pointF.y);
			System.out.println(i + ":" + pointF.x + "," + pointF.y);
			i++;
		}
		path.lineTo(pointF.x, pointF.y);
		path.close();

	}

	/**
	 * 正弦函数0-90°的值是依次递增的
	 * 
	 * @param rect
	 * @param angle
	 */
	public PointF testcal(RectF rect, double angle) {
		float r = rect.height() / 2.0f;
		float x = rect.left + rect.width() / 2.0f;
		x += FloatMath.sin((float) Math.toRadians(angle)) * r;
		float y = rect.top + rect.height() / 2.0f
				- FloatMath.cos((float) Math.toRadians(angle)) * r;
		return new PointF((float) x, (float) y);
	}

	/**
	 * 
	 * @param rect
	 *            以此矩形的中心点为中心点
	 * @param angle
	 *            旋转角度
	 * @param start
	 *            起始位置
	 * @return
	 */
	public PointF rotatePoint(RectF rect, double angle, float r) {
		float x = rect.centerX() - FloatMath.cos((float) Math.toRadians(angle))
				* r;
		float y = rect.centerY() - FloatMath.sin((float) Math.toRadians(angle))
				* r;
		return new PointF(x, y);
	}

}
