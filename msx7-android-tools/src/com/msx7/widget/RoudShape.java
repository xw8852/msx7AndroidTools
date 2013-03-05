package com.msx7.widget;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.listviewtemplate.R;

/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * A Drawable that clips another Drawable based on this Drawable's current level
 * value. You can control how much the child Drawable gets clipped in width and
 * height based on the level, as well as a gravity to control where it is placed
 * in its overall container. Most often used to implement things like progress
 * bars, by increasing the drawable's level with
 * {@link android.graphics.drawable.Drawable#setLevel(int) setLevel()}.
 * <p class="note">
 * <strong>Note:</strong> The drawable is clipped completely and not visible
 * when the level is 0 and fully revealed when the level is 10,000.
 * </p>
 * 
 * <p>
 * It can be defined in an XML file with the
 * <code>&lt;clip></code> element.  For more
 * information, see the guide to <a
 * href="{@docRoot} element. For more information, see the guide to <a
 * href="{@docRoot}guide/topics/resources/drawable-resource.html">Drawable
 * Resources</a>.
 * </p>
 * 
 * @attr ref android.R.styleable#ClipDrawable_clipOrientation
 * @attr ref android.R.styleable#ClipDrawable_gravity
 * @attr ref android.R.styleable#ClipDrawable_drawable
 */
public class RoudShape extends Drawable implements Drawable.Callback {
	private ClipState mClipState;
	private final Rect mTmpRect = new Rect();

	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;

	RoudShape() {
		this(null, null);
	}

	/**
	 * @param orientation
	 *            Bitwise-or of {@link #HORIZONTAL} and/or {@link #VERTICAL}
	 */
	public RoudShape(Drawable drawable, int gravity, int orientation) {
		this(null, null);

		mClipState.mDrawable = drawable;
		mClipState.mGravity = gravity;
		mClipState.mOrientation = orientation;

		if (drawable != null) {
			drawable.setCallback(this);
		}
	}

	@Override
	public void inflate(Resources r, XmlPullParser parser, AttributeSet attrs)
			throws XmlPullParserException, IOException {
		super.inflate(r, parser, attrs);

		int type;

		TypedArray a = r.obtainAttributes(attrs, R.styleable.RoudShape);

		int orientation = a.getInt(R.styleable.RoudShape_clipOrientation,
				HORIZONTAL);
		int g = a.getInt(android.R.attr.gravity, Gravity.LEFT);
		Drawable dr = a.getDrawable(android.R.attr.drawable);

		a.recycle();

		final int outerDepth = parser.getDepth();
		while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
				&& (type != XmlPullParser.END_TAG || parser.getDepth() > outerDepth)) {
			if (type != XmlPullParser.START_TAG) {
				continue;
			}
			dr = Drawable.createFromXmlInner(r, parser, attrs);
		}

		if (dr == null) {
			throw new IllegalArgumentException(
					"No drawable specified for <clip>");
		}

		mClipState.mDrawable = dr;
		mClipState.mOrientation = orientation;
		mClipState.mGravity = g;

		dr.setCallback(this);
	}

	// overrides from Drawable.Callback

	@TargetApi(11)
	public void invalidateDrawable(Drawable who) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.invalidateDrawable(this);
		}
	}

	@TargetApi(11)
	public void scheduleDrawable(Drawable who, Runnable what, long when) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.scheduleDrawable(this, what, when);
		}
	}

	@TargetApi(11)
	public void unscheduleDrawable(Drawable who, Runnable what) {
		final Callback callback = getCallback();
		if (callback != null) {
			callback.unscheduleDrawable(this, what);
		}
	}

	// overrides from Drawable

	@Override
	public int getChangingConfigurations() {
		return super.getChangingConfigurations()
				| mClipState.mChangingConfigurations
				| mClipState.mDrawable.getChangingConfigurations();
	}

	@Override
	public boolean getPadding(Rect padding) {
		// XXX need to adjust padding!
		return mClipState.mDrawable.getPadding(padding);
	}

	@Override
	public boolean setVisible(boolean visible, boolean restart) {
		mClipState.mDrawable.setVisible(visible, restart);
		return super.setVisible(visible, restart);
	}

	@Override
	public void setAlpha(int alpha) {
		mClipState.mDrawable.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mClipState.mDrawable.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return mClipState.mDrawable.getOpacity();
	}

	@Override
	public boolean isStateful() {
		return mClipState.mDrawable.isStateful();
	}

	@Override
	protected boolean onStateChange(int[] state) {
		return mClipState.mDrawable.setState(state);
	}

	@Override
	protected boolean onLevelChange(int level) {
		mClipState.mDrawable.setLevel(level);
		invalidateSelf();
		return true;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		mClipState.mDrawable.setBounds(bounds);
	}

	@Override
	public void draw(Canvas canvas) {

		if (mClipState.mDrawable.getLevel() == 0) {
			return;
		}

		final Rect r = mTmpRect;
		final Rect bounds = getBounds();
		int level = getLevel();
		int w = bounds.width();
		final int iw = 0; // mClipState.mDrawable.getIntrinsicWidth();
		if ((mClipState.mOrientation & HORIZONTAL) != 0) {
			w -= (w - iw) * (10000 - level) / 10000;
		}
		int h = bounds.height();
		final int ih = 0; // mClipState.mDrawable.getIntrinsicHeight();
		if ((mClipState.mOrientation & VERTICAL) != 0) {
			h -= (h - ih) * (10000 - level) / 10000;
		}
		double angle = (getLevel() * 180.0) / 10000;
		Path path = new Path();
		path.reset();
		path.moveTo(bounds.exactCenterX(), bounds.exactCenterY());
		path.lineTo(bounds.left + bounds.width() / 2.0f, bounds.top);
		PointF mPointF = testcal(bounds, angle);
		if (mPointF.x == bounds.right) {
			path.lineTo(bounds.right, bounds.top);
			path.lineTo(mPointF.x, mPointF.y);
		} else {
			path.lineTo(mPointF.x, mPointF.y);
		}
		path.lineTo(bounds.exactCenterX(), bounds.exactCenterY());
		path.close();
		if (w > 0 && h > 0) {
			canvas.save();
			canvas.clipPath(path);
			mClipState.mDrawable.draw(canvas);
			canvas.restore();
		}
	}

	/**
	 * 正弦函数0-90°的值是依次递增的
	 * 
	 * @param rect
	 * @param angle
	 */
	public PointF testcal(Rect rect, double angle) {
		double x = 0;
		double y = 0;
		if (angle < 90) {
			double tan = Math.tan(angle * Math.PI / 180);
			double w = rect.width() / 2.0;
			double h = rect.height() / 2.0;
			if (tan <= ((rect.width() + 0.0) / rect.height())) {
				x = w + (h * tan);
				y = rect.top;
			} else {
				y = w / tan;
				x = rect.right;

			}
		}else if(angle==90){
			return new PointF(rect.right, rect.top+rect.height()/2.0f);
		}
		return new PointF((float) x, (float) y);
	}

	@Override
	public int getIntrinsicWidth() {
		return mClipState.mDrawable.getIntrinsicWidth();
	}

	@Override
	public int getIntrinsicHeight() {
		return mClipState.mDrawable.getIntrinsicHeight();
	}

	@Override
	public ConstantState getConstantState() {
		if (mClipState.canConstantState()) {
			mClipState.mChangingConfigurations = getChangingConfigurations();
			return mClipState;
		}
		return null;
	}

	final static class ClipState extends ConstantState {
		Drawable mDrawable;
		int mChangingConfigurations;
		int mOrientation;
		int mGravity;

		private boolean mCheckedConstantState;
		private boolean mCanConstantState;

		@TargetApi(5)
		ClipState(ClipState orig, RoudShape owner, Resources res) {
			if (orig != null) {
				if (res != null) {
					mDrawable = orig.mDrawable.getConstantState().newDrawable(
							res);
				} else {
					mDrawable = orig.mDrawable.getConstantState().newDrawable();
				}
				mDrawable.setCallback(owner);
				mOrientation = orig.mOrientation;
				mGravity = orig.mGravity;
				mCheckedConstantState = mCanConstantState = true;
			}
		}

		@Override
		public Drawable newDrawable() {
			return new RoudShape(this, null);
		}

		@Override
		public Drawable newDrawable(Resources res) {
			return new RoudShape(this, res);
		}

		@Override
		public int getChangingConfigurations() {
			return mChangingConfigurations;
		}

		boolean canConstantState() {
			if (!mCheckedConstantState) {
				mCanConstantState = mDrawable.getConstantState() != null;
				mCheckedConstantState = true;
			}

			return mCanConstantState;
		}
	}

	private RoudShape(ClipState state, Resources res) {
		mClipState = new ClipState(state, this, res);
	}
}
