package com.msx7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

public class RegularHexagonView extends View {
	protected PointF[] mVertexs;
	
	private Rect rect;
	
	public RegularHexagonView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RegularHexagonView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RegularHexagonView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		rect=new Rect(left, top, right, bottom);
		
	}

	/**
	 * 
	 * @return  必须在{@link #onLayout(int, int, int, int)} 之后调用才会有值,否则为null
	 */
	public PointF[] getVertexs() {
		return mVertexs;
	}

	/**
	 * 以rect的上方中点为起始点。
	 * 
	 * @param rect
	 *            必须为正方形
	 * @param angle
	 */
	public PointF rotatePoint(RectF rect, double angle) {
		float r = rect.height() / 2.0f;
		float x = rect.left + rect.width() / 2.0f;
		x += FloatMath.sin((float) Math.toRadians(angle)) * r;
		float y = rect.top + rect.height() / 2.0f
				- FloatMath.cos((float) Math.toRadians(angle)) * r;
		return new PointF((float) x, (float) y);
	}

	/**
	 * 
	 * 以经过rect中心点的水平线的左边距离r的点为起始点
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
