package com.msx7.chart.anim;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.util.FloatMath;
import android.view.SurfaceHolder;
import android.view.animation.Transformation;

public class TrackBerzierCurvesAnimation extends BaseBerzierCurvesAnim {
	int step = 5;
	PointF[] points;
	Paint paint;
	Canvas canvas;
	SurfaceHolder holder;
	float scale;

	private TrackBerzierCurvesAnimation(PointF[] points, Paint paint, float scale) {
		super();
		this.points = points;
		this.paint = paint;
		this.scale = scale;
		if (scale < 0 || scale > 1.0f) {
			throw new IllegalArgumentException(
					"the params scale must between 0.0f and 1.0f");
		}
	}

	public TrackBerzierCurvesAnimation(PointF[] points, Paint paint, float scale,
			SurfaceHolder holder, long durationMillis) {
		this(points, paint, scale);
		this.holder = holder;
		setDuration(durationMillis);
	}

	public TrackBerzierCurvesAnimation(PointF[] points, Paint paint, float scale,
			Canvas canvas, long durationMillis) {
		this(points, paint, scale);
		this.canvas = canvas;
		setDuration(durationMillis);
	}

	@Override
	protected void transformation(float interpolatedTime, Transformation t) {
		PointF[][] _points = createCurves(points, scale);
		if (holder != null) {
			Canvas canvas = holder.lockCanvas();
			drawCurves(points, _points, canvas, interpolatedTime);
			holder.unlockCanvasAndPost(canvas);
		} else {
			drawCurves(points, _points, canvas, interpolatedTime);
		}
	}

	void drawCurves(PointF[] origin, PointF[][] points, Canvas canvas,
			float interpolatedTime) {
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		calculateTimes();
		if (interpolatedTime >= 1.0f) {
			int count = points.length;
			Path path = new Path();
			path.moveTo(origin[0].x, origin[0].y);
			for (int i = 0; i < count; i++) {
				path.moveTo(points[i][0].x, points[i][0].y);
				path.cubicTo(points[i][1].x, points[i][1].y, points[i][2].x,
						points[i][2].y, points[i][3].x, points[i][3].y);
			}
			canvas.drawPath(path, paint);
			onAnimEnd(canvas);
		} else {
			canvas.save();
			int count = 0;
			while (count < startTime.length) {
				if (interpolatedTime < startTime[count]) {
					break;
				}
				count++;
			}
			count--;
			float t = (interpolatedTime - startTime[count]) / time[count];
			PointF _p = new PointF(bezierNfuncX(t, points[count]),
					bezierNfuncY(t, points[count]));
			Path path = new Path();
			path.moveTo(points[count][0].x, points[count][0].y);
			float step = 0f;
			while (step < t) {
				_p = new PointF(bezierNfuncX(step, points[count]), bezierNfuncY(step,
						points[count]));
				path.lineTo(_p.x, _p.y);
				step += 0.01f;
			}
			canvas.drawPath(path, paint);
			canvas.restore();
			int i = 0;
			path.reset();
			path.moveTo(origin[0].x, origin[0].y);
			for (i = 0; i < count; i++) {
				path.lineTo(points[i][0].x, points[i][0].y);
				path.cubicTo(points[i][1].x, points[i][1].y, points[i][2].x,
						points[i][2].y, points[i][3].x, points[i][3].y);
			}
		
			canvas.drawPath(path, paint);
		}
	}

	float[] time;
	float[] startTime;

	void calculateTimes() {
		if (time != null)
			return;
		startTime = new float[points.length - 1];
		float sumLen = 0f;
		float[] len = new float[points.length - 1];
		time = new float[points.length - 1];
		for (int i = 0; i < len.length; i++) {
			len[i] = FloatMath.pow(points[i].x - points[i + 1].x, 2)
					+ FloatMath.pow(points[i].y - points[i + 1].y, 2);
			sumLen += len[i];
		}
		startTime[0] = 0f;
		for (int i = 0; i < len.length; i++) {
			time[i] = len[i] / sumLen;

			for (int j = 0; j < i; j++) {
				startTime[i] += time[j];
			}
		}
		return;
	}

	float bezierNfuncX(float t, PointF[] arr) {
		float part1 = FloatMath.pow(1.0f - t, 3.0f) * arr[0].x;
		float part2 = 3 * t * FloatMath.pow(1 - t, 2) * arr[1].x;
		float part3 = 3 * (1.0f - t) * FloatMath.pow(t, 2.0f) * arr[2].x;
		float part4 = FloatMath.pow(t, 3.0f) * arr[3].x;
		return part1 + part2 + part3 + part4;
	}

	float bezierNfuncY(float t, PointF[] arr) {
		float part1 = FloatMath.pow(1.0f - t, 3.0f) * arr[0].y;
		float part2 = 3 * t * FloatMath.pow(1 - t, 2) * arr[1].y;
		float part3 = 3 * (1.0f - t) * FloatMath.pow(t, 2.0f) * arr[2].y;
		float part4 = FloatMath.pow(t, 3.0f) * arr[3].y;
		return part1 + part2 + part3 + part4;
	}
}
