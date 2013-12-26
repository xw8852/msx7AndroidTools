package com.msx7.chart.anim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.view.SurfaceHolder;
import android.view.animation.Transformation;

public class RiseBerzierCurvesAnimation extends BaseBerzierCurvesAnim {
	public static final int TYPE_X = 0x01;
	public static final int TYPE_Y = 0x02;
	int type;
	PointF[] points;
	Paint paint;
	Canvas canvas;
	SurfaceHolder holder;
	float scale;
	float origin;

	private RiseBerzierCurvesAnimation(int type, PointF[] points, Paint paint, float scale,
			float origin) {
		super();
		this.type = type;
		this.points = points;
		this.paint = paint;
		this.scale = scale;
		this.origin = origin;
		if (scale < 0 || scale > 1.0f) {
			throw new IllegalArgumentException(
					"the params scale must between 0.0f and 1.0f");
		}
	}

	public RiseBerzierCurvesAnimation(int type, PointF[] points, Paint paint,
			SurfaceHolder holder, float scale, float origin) {
		this(type, points, paint, scale, origin);
		this.holder = holder;
	}

	public RiseBerzierCurvesAnimation(int type, PointF[] points, Paint paint, Canvas canvas,
			float scale, float origin) {
		this(type, points, paint, scale, origin);
		this.canvas = canvas;
	}

	public RiseBerzierCurvesAnimation(int type, PointF[] points, Paint paint,
			SurfaceHolder holder, float scale, float origin, long durationMillis) {
		this(type, points, paint, scale, origin);
		this.holder = holder;
		setDuration(durationMillis);
	}

	public RiseBerzierCurvesAnimation(int type, PointF[] points, Paint paint, Canvas canvas,
			float scale, float origin, long durationMillis) {
		this(type, points, paint, scale, origin);
		this.canvas = canvas;
		setDuration(durationMillis);
	}

	@Override
	protected void transformation(float interpolatedTime, Transformation t) {
		int count = points.length;
		PointF[] _origin = new PointF[count];
		for (int i = 0; i < count; i++) {
			if (type == TYPE_X) {
				float _x = (points[i].x - origin) * interpolatedTime;
				_origin[i] = new PointF(_x + origin, points[i].y);
			} else {
				float _y = (points[i].y - origin) * interpolatedTime;
				_origin[i] = new PointF(points[i].x, _y + origin);
			}
		}
		PointF[][] _points = createCurves(_origin, scale);
		if (holder != null) {
			Canvas canvas = holder.lockCanvas();
			drawCurves(_origin, _points, canvas);
			if (interpolatedTime >= 1.0f) {
				onAnimEnd(canvas);
			}
			holder.unlockCanvasAndPost(canvas);
		} else {
			drawCurves(_origin, _points, canvas);
			if (interpolatedTime >= 1.0f) {
				onAnimEnd(canvas);
			}
		}
	}

	void drawCurves(PointF[] origin, PointF[][] points, Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		int count = points.length;
		Path path = new Path();
		path.moveTo(origin[0].x, origin[0].y);
		for (int i = 0; i < count; i++) {
			path.moveTo(points[i][0].x, points[i][0].y);
			path.cubicTo(points[i][1].x, points[i][1].y, points[i][2].x,
					points[i][2].y, points[i][3].x, points[i][3].y);
		}
		canvas.drawPath(path, paint);
	}
}
