/**
 * 
 */
package com.haozi.baselibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.haozi.baselibrary.R;

/**
 * 类名：CircleImageViewEx
 * @author yinhao
 * @功能
 * @创建日期 2016年3月15日 下午2:23:14
 * @备注 [修改者，修改日期，修改内容]
 */
public class CircleImageViewEx extends CircleImageView {

	/**图片外框尺寸画笔*/
	private final Paint mImageBorderPaint = new Paint();
	/**图片边框内容*/
	private Bitmap mImageBorderBitmap;
	/**图片边框内容*/
	private BitmapShader mImageBorderBitmapShader;
	/**图片边框矩阵*/
	protected final Matrix mImageBorderMatrix = new Matrix();
	/**图片边框上部图片偏移量*/
	private int mImageBorderDy = 0;
	/**图片边框上部图片偏移量占框图的百分比*/
	private final static float mDyPercent = 0.10f;
	
	public CircleImageViewEx(Context context) {
		super(context);
	}

	public CircleImageViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CircleImageViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
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

	public void setBorderImage(int resId) {
		Drawable borderImg = getResources().getDrawable(resId);
		mImageBorderBitmap = getBitmapFromDrawable(borderImg);
		setup();
	}
	
	public void setBorderImage(Bitmap bm) {
		super.setImageBitmap(bm);
		mImageBorderBitmap = bm;
		setup();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			return;
		}
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,mBitmapPaint);
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius,mBorderPaint);

		//(getWidth() / 2, getHeight() / 2, mDrawableRadius,mImageBorderPaint);
		if(mImageBorderBitmap != null){
			canvas.drawBitmap(mImageBorderBitmap, mImageBorderMatrix, mImageBorderPaint);
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
		//初始化图片整体矩阵
		mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
		//设置主体图片矩阵
		mBitmapPaint.setAntiAlias(true);
		mBitmapPaint.setShader(mBitmapShader);

		//设置图片边框矩阵
		if(mImageBorderBitmap != null){
			mImageBorderBitmapShader = new BitmapShader(mImageBorderBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
			mImageBorderPaint.setAntiAlias(true);
			mImageBorderPaint.setShader(mImageBorderBitmapShader);
			//计算新的边框宽度
			mImageBorderDy = (int) (mImageBorderBitmap.getHeight()*mDyPercent+0.5f);
			if(mBorderWidth <= 0){
				mBorderWidth = mImageBorderDy/5;
			}
		}

		//设置边框矩阵
		mBorderPaint.setStyle(Paint.Style.STROKE);
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);

		//获取图片大小
		mBitmapHeight = mBitmap.getHeight();
		mBitmapWidth = mBitmap.getWidth();
		//计算边框矩阵
		mBorderRect.set(0, 0, getWidth(), getHeight());
		//计算边框圆角度
		mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
				(mBorderRect.width() - mBorderWidth) / 2);
		
		//更新内容矩阵
		mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width()
				- mBorderWidth, mBorderRect.height() - mBorderWidth);
		//更新内容圆角矩阵
		mDrawableRadius = Math.min(mDrawableRect.height() / 2,
				mDrawableRect.width() / 2);

		updateShaderMatrix();
		invalidate();
	}
	
	protected void updateShaderMatrix() {
		//更新内容图片
		super.updateShaderMatrix();
		//更新边框图片
		if(mImageBorderBitmapShader != null && mImageBorderBitmap != null){
			float scale;
			float dx = 0;
			float dy = 0;
			mImageBorderMatrix.set(null);
			//获取边框图片大小
			int mBitmapHeight = mImageBorderBitmap.getHeight();
			int mBitmapWidth = mImageBorderBitmap.getWidth();
			if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width()* mBitmapHeight) {
				scale = mDrawableRect.height() / (float) mBitmapHeight;
				dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
			} else {
				scale = mDrawableRect.width() / (float) mBitmapWidth;
				dy = (mDrawableRect.height() - (mBitmapHeight) * scale) * 0.5f - mImageBorderDy*scale*0.5f;
			}
			mImageBorderMatrix.setScale(scale, scale);
			mImageBorderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth,(int) (dy + 0.5f) + mBorderWidth);
			mImageBorderBitmapShader.setLocalMatrix(mImageBorderMatrix);
		}
	}
}
