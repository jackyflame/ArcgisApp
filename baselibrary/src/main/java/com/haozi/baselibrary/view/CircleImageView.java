/**
 * 
 */
package com.haozi.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.haozi.baselibrary.R;

/**
 * 类名：CircleImageView
 * 
 * @author yinhao
 * @功能
 * @创建日期 2015年11月19日 下午3:26:37
 * @备注 [修改者，修改日期，修改内容]
 */
@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

	/**限定支持的图片填充类型*/
	protected static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
	/**BITMAP编码*/
	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 1;

	/**默认边框宽度：0*/
	protected static final int DEFAULT_BORDER_WIDTH = 0;
	/**默认边框颜色：透明*/
	protected static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;

	/**图片内容尺寸矩阵*/
	protected final RectF mDrawableRect = new RectF();
	/**图片边框矩阵*/
	protected final RectF mBorderRect = new RectF();

	protected final Matrix mShaderMatrix = new Matrix();
	/**图片内容尺寸画笔*/
	protected final Paint mBitmapPaint = new Paint();
	/**图片边框画笔*/
	protected final Paint mBorderPaint = new Paint();

	/**边框颜色*/
	protected int mBorderColor = DEFAULT_BORDER_COLOR;
	/**边框宽度*/
	protected int mBorderWidth = DEFAULT_BORDER_WIDTH;

	protected Bitmap mBitmap;
	protected BitmapShader mBitmapShader;
	protected int mBitmapWidth;
	protected int mBitmapHeight;

	protected float mDrawableRadius;
	protected float mBorderRadius;

	protected boolean mReady;
	protected boolean mSetupPending;

	/**
	 * @param context
	 */
	public CircleImageView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		//调用父类方法
		super(context, attrs, defStyle);
		initView(context, attrs, defStyle);
	}

	protected void initView(Context context,AttributeSet attrs,int defStyle){
		super.setScaleType(SCALE_TYPE);
		//获取自定义attr值
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0);
		//边框宽度
		mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH);
		//边框颜色
		mBorderColor = a.getColor(R.styleable.CircleImageView_border_color,DEFAULT_BORDER_COLOR);
		//回收属性值
		a.recycle();
		//设置准备标记状态
		mReady = true;
		//绘制
		if (mSetupPending) {
			setup();
			mSetupPending = false;
		}
	}
	
	@Override
	public ScaleType getScaleType() {
		return SCALE_TYPE;
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (scaleType != SCALE_TYPE) {
			throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,mBitmapPaint);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius,mBorderPaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		setup();
	}

	public int getBorderColor() {
		return mBorderColor;
	}

	public void setBorderColor(int borderColor) {
		if (borderColor == mBorderColor) {
			return;
		}
		mBorderColor = borderColor;
		mBorderPaint.setColor(mBorderColor);
		invalidate();
	}

	public int getBorderWidth() {
		return mBorderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		if (borderWidth == mBorderWidth) {
			return;
		}
		mBorderWidth = borderWidth;
		setup();
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		mBitmap = bm;
		setup();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		mBitmap = getBitmapFromDrawable(drawable);
		setup();
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}

	protected Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}
		try {
			Bitmap bitmap;
			if (drawable instanceof ColorDrawable) {
				bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
						COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
			} else {
				bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;
		} catch (OutOfMemoryError e) {
			return null;
		}
	}

	/**
	 * 开始绘制圆形边框图片内容
	 * */
	protected void setup() {
		if (!mReady) {
			mSetupPending = true;
			return;
		}
		if (mBitmap == null) {
			return;
		}
		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
		mBitmapPaint.setAntiAlias(true);
		mBitmapPaint.setShader(mBitmapShader);

		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);

		mBitmapHeight = mBitmap.getHeight();
		mBitmapWidth = mBitmap.getWidth();
		mBorderRect.set(0, 0, getWidth(), getHeight());
		
		mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
				(mBorderRect.width() - mBorderWidth) / 2);

		mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width()
				- mBorderWidth, mBorderRect.height() - mBorderWidth);
		
		mDrawableRadius = Math.min(mDrawableRect.height() / 2,
				mDrawableRect.width() / 2);

		updateShaderMatrix();
		invalidate();
	}

	protected void updateShaderMatrix() {
		float scale;
		float dx = 0;
		float dy = 0;
		mShaderMatrix.set(null);
		if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()* mBitmapHeight) {
			scale = mDrawableRect.height() / (float) mBitmapHeight;
			dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
		} else {
			scale = mDrawableRect.width() / (float) mBitmapWidth;
			dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
		}

		mShaderMatrix.setScale(scale, scale);
		mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth,(int) (dy + 0.5f) + mBorderWidth);
		mBitmapShader.setLocalMatrix(mShaderMatrix);
	}
}
