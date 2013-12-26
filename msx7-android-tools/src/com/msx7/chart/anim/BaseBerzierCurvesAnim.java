package com.msx7.chart.anim;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public abstract class BaseBerzierCurvesAnim extends Animation {
	AnimationListener listener;
	ILinePaint paint;
	
	

	@SuppressLint("FloatMath")
	public PointF[][] createCurves(PointF[] origin, float scale) {
		int originCount = origin.length;
		PointF[][] points = new PointF[originCount - 1][4];
		for (int i = 0; i < originCount - 1; i++) {
			int _nexti = (i + 1) % originCount;
			int _prev = (i + 2) % originCount;
			int _backi = (i + originCount - 1) % originCount;
			PointF p1 = origin[i];
			PointF p2 = origin[_nexti];
			PointF p3 = origin[_prev];
			PointF p0 = origin[_backi];

			if (i + 2 >= originCount - 1) {
				p3 = new PointF((p2.x - p1.x) * scale + p2.x, (p2.y - p1.y)
						* scale + p2.y);

			}
			if (i - 1 < 0) {
				p0 = new PointF((p1.x - p2.x) * scale + p1.x, (p1.y - p2.y)
						* scale + p1.y);
			}

			float len1 = FloatMath.sqrt((p0.x - p1.x) * (p0.x - p1.x)
					+ (p0.y - p1.y) * (p0.y - p1.y));
			float len2 = FloatMath.sqrt((p1.x - p2.x) * (p1.x - p2.x)
					+ (p1.y - p2.y) * (p1.y - p2.y));
			float len3 = FloatMath.sqrt((p3.x - p2.x) * (p3.x - p2.x)
					+ (p3.y - p2.y) * (p3.y - p2.y));
			float k1 = len1 / (len1 + len2);
			float k2 = len2 / (len2 + len3);
			float xm1 = k1 * ((p1.x + p2.x) / 2.0f - (p1.x + p0.x) / 2.0f)
					+ (p1.x + p0.x) / 2.0f;
			float ym1 = k1 * ((p1.y + p2.y) / 2.0f - (p1.y + p0.y) / 2.0f)
					+ (p1.y + p0.y) / 2.0f;
			float xm2 = k2 * ((p2.x + p3.x) / 2.0f - (p2.x + p1.x) / 2.0f)
					+ (p2.x + p1.x) / 2.0f;
			float ym2 = k2 * ((p2.y + p3.y) / 2.0f - (p2.y + p1.y) / 2.0f)
					+ (p2.y + p1.y) / 2.0f;
			float _ctrl_x1 = xm1 + p1.x - xm1 + scale
					* ((p1.x + p2.x) / 2.0f - xm1);
			float _ctrl_y1 = ym1 + p1.y - ym1 + scale
					* ((p1.y + p2.y) / 2.0f - ym1);

			float _ctrl_x2 = xm2 + p2.x - xm2 + scale
					* ((p2.x + p1.x) / 2.0f - xm2);
			float _ctrl_y2 = ym2 + p2.y - ym2 + scale
					* ((p2.y + p1.y) / 2.0f - ym2);
			PointF[] arr = new PointF[4];
			arr[0] = p1;
			arr[1] = new PointF(_ctrl_x1, _ctrl_y1);
			arr[2] = new PointF(_ctrl_x2, _ctrl_y2);
			arr[3] = p2;
			points[i] = arr;
		}
		return points;
	}
	public ILinePaint getPaint() {
		return paint;
	}

	public void setPaint(ILinePaint paint) {
		this.paint = paint;
	}
	@Override
	public void setAnimationListener(AnimationListener listener) {
		super.setAnimationListener(listener);
		this.listener=listener;
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		transformation(interpolatedTime, t);
	}
	public void onAnimEnd(Canvas canvas){
		if(animEnd!=null){
			animEnd.animEnd(canvas);
		}
	}
	
	ApplyAnimEnd animEnd;
	
	public ApplyAnimEnd getAnimEnd() {
		return animEnd;
	}

	public void setAnimEnd(ApplyAnimEnd animEnd) {
		this.animEnd = animEnd;
	}

	protected abstract void transformation(float interpolatedTime, Transformation t);
	
	public static interface ApplyAnimEnd{
		public void animEnd(Canvas canvas);
	}
	public static interface ILinePaint{
		public Paint getLinePaint();
	}
}
