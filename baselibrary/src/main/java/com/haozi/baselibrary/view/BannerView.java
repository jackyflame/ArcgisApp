/**
 * 
 */
package com.haozi.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozi.baselibrary.R;
import com.haozi.baselibrary.utils.ImageLoaderUtils;
import com.haozi.baselibrary.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 类名：BannerView
 * 
 * @author YH 创建日期：2015年11月3日 [修改者，修改日期，修改内容]
 */
public class BannerView extends FrameLayout {
	
	//放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	//放圆点的View的list
	private List<ImageView> dotViewsList;
	//圆点区域
	private LinearLayout linearLayoutDotArea;
	
	//自动轮播启用开关  
    @SuppressWarnings("unused")
	private final static boolean isAutoPlay = true;  
	//文字标题
	private TextView txv_banner_title;
	//文字标题时间
	private TextView txv_banner_time;
	//内容pager
	private ViewPager viewPager;
	//定时任务 
	private ScheduledExecutorService scheduledExecutorService;
	//当前轮播页  
    private int currentItem  = 0;

	/**默认新闻图片*/
	private int[] imagesDefault = {R.mipmap.banner_news_1,R.mipmap.banner_news_2,R.mipmap.banner_news_3};
	private String[] banerTitles = {"","",""};
	private String[] banerTimes = {"","",""};
	
	/**点击监听*/
	private BannerItemAOnClick mListener;
	
    private static final int SIZE = 3;
	
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public BannerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * @param context
	 */
	public BannerView(Context context) {
		super(context);
		initView(context);
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			viewPager.setCurrentItem(currentItem);
		}
	};

	private void initView(Context context) {
		//绑定内容
		LayoutInflater.from(context).inflate(R.layout.layout_banner_view, this,	true);
		//获取引用
		viewPager = (ViewPager) findViewById(R.id.vp_banner);
		txv_banner_title = (TextView) findViewById(R.id.txv_banner_title);
		txv_banner_time = (TextView) findViewById(R.id.txv_banner_time);
		linearLayoutDotArea = (LinearLayout) findViewById(R.id.lin_dot_area);
	}
	
	public void setView(final int[] imageIds, final String[] titles,final String[] banerTimes) {
		//初始化图片
		initImageViews(imageIds, imageIds.length);
		//初始化点
		initImageViewDots(imageIds.length);
		//设置适配器
		viewPager.setAdapter(new MyPagerAdapter());
		//添加滚动监听
		viewPager.addOnPageChangeListener(new MyPageChangeListener());
		//初始化新闻标题
		if(titles == null || titles.length == 0){
			txv_banner_title.setText("");
		}else{
			txv_banner_title.setText(titles[0]);
		}
		if(banerTimes == null || banerTimes.length == 0){
			txv_banner_time.setText("");
		}else{
			txv_banner_time.setText(banerTimes[0]);
		}
	}

	public void setView(final int[] imageIds) {
		setView(imageIds,null,null);
	}

	private void initImageViews(int[] imageIds, int count) {
		if(imageViewsList == null){
			imageViewsList = new ArrayList<ImageView>();
		}else{
			imageViewsList.clear();
		}
		for (int i = 0; i < count; i++) {
			ImageView imageView = new ImageView(getContext());
			imageView.setImageResource(imageIds[i]);
			imageView.setTag(null);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(imageView);
		}
	}

	public void setView(final String[] imageUrls, final String[] titles, final String[] times) {
		//初始化图片
		initImageViews(imageUrls, imageUrls.length);
		//初始化点
		initImageViewDots(imageUrls.length);
		//设置适配器
		viewPager.setAdapter(new MyPagerAdapter());
		//添加滚动监听
		viewPager.addOnPageChangeListener(new MyPageChangeListener());
		//初始化新闻标题
		if(titles == null || titles.length == 0) {
			txv_banner_title.setText("");
		}else{
			txv_banner_title.setText(titles[0]);
		}
		if(times == null || times.length == 0) {
			txv_banner_time.setText("");
		}else{
			txv_banner_time.setText(times[0]);
		}
		//设置数据
		banerTitles = titles;
		banerTimes = times;
	}

	public void setView(final String[] imageUrls){
		if(imageUrls == null || imageUrls.length == 0){
			setView(imagesDefault);
			return;
		}
		setView(imageUrls,null,null);
	}

	private void initImageViews(String[] imageUrls, int count) {
		if(imageViewsList == null){
			imageViewsList = new ArrayList<ImageView>();
		}else{
			imageViewsList.clear();
		}
		for (int i = 0; i < count; i++) {
			ImageView imageView = new ImageView(getContext());
			imageView.setTag(imageUrls[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(imageView);
		}
	}

	private void initImageViewDots(int count) {
		//初始化缓存
		if(dotViewsList == null){
			dotViewsList = new ArrayList<ImageView>();
		}else{
			dotViewsList.clear();
		}
		//清理子页面
		linearLayoutDotArea.removeAllViews();
		//设置点
		for (int i = 0; i < count; i++) {
			//新建图片
			ImageView imageView = new ImageView(getContext());
			//设置位置和颜色
			if (i == 0) {
				imageView.setImageResource(R.drawable.banner_dot_focused_shap);
				LinearLayout.LayoutParams params =
						new LinearLayout.LayoutParams(20, 20);
				imageView.setLayoutParams(params);
			} else {
				LinearLayout.LayoutParams params =
						new LinearLayout.LayoutParams(20, 20);
				params.setMargins(10, 0, 0, 0);
				imageView.setImageResource(R.drawable.banner_dot_normal_shap);
				imageView.setLayoutParams(params);
			}
			//添加点到linear图里面
			linearLayoutDotArea.addView(imageView);
			//添加引用到缓存
			dotViewsList.add(imageView);
		}
	}
	
	 /** 
     * 开始轮播图切换 
     */  
	public void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ViewPagerAutoScrollTask(), 5, 5, TimeUnit.SECONDS);
	}

	/** 
     * 停止轮播图切换 
     */  
	public void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	private class ViewPagerAutoScrollTask implements Runnable {
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}
	}

	/** 
     * 填充ViewPager的页面适配器 
     */  
    private class MyPagerAdapter  extends PagerAdapter{  
      	
        @Override  
        public void destroyItem(View container, int position, Object object) {  
            //((ViewPag.er)container).removeView((View)object);  
            ((ViewPager)container).removeView(imageViewsList.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(View container, final int position) {
        	
            ImageView imageView = imageViewsList.get(position);
            //使用缓存加载图片
            if(imageView.getTag() != null && !StringUtil.isEmpty(imageView.getTag().toString())){
            	ImageLoaderUtils.getInstance().displayImage(imageView.getTag() + "", imagesDefault[position%SIZE], imageView);
            	//设置点击监听
            	imageView.setOnClickListener(new OnClickListener() {
            		@Override
            		public void onClick(View v) {
            			if(mListener != null){
            				mListener.onclick(position);
            			}
            		}
            	});
            }else{
            	imageView.setImageResource(imagesDefault[position%SIZE]);
            }
            //图片添加到View里
            ((ViewPager)container).addView(imageView);
            //获取内容对象
            return imageViewsList.get(position);
        }  
  
        @Override  
        public int getCount() {  
            return imageViewsList.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
        
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
              
        }  
          
    }  
    
    /** 
     * ViewPager的监听器 
     * 当ViewPager中页面的状态发生改变时调用 
     */  
	private class MyPageChangeListener implements OnPageChangeListener {
		//上次图片位置缓存
		private int oldPosition = 0;
		//自动播放标记
		boolean isAutoPlay = false;
		
		@Override
		public void onPageSelected(int position) {
			dotViewsList.get(position).setImageResource(R.drawable.banner_dot_focused_shap);
			dotViewsList.get(oldPosition).setImageResource(R.drawable.banner_dot_normal_shap);
			oldPosition = position;
			if(banerTitles != null && banerTitles.length > 0 && banerTitles.length > position){
				txv_banner_title.setText(banerTitles[position]);
			}else{
				txv_banner_title.setText("");
			}
			if(banerTimes != null && banerTimes.length > 0 && banerTimes.length > position){
				txv_banner_time.setText(banerTimes[position]);
			}else{
				txv_banner_time.setText("");
			}
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			//手势滑动，空闲中
			case 1:
				isAutoPlay = false;
				break;
			//界面切换中
			case 2:
				isAutoPlay = true;
				break;
			//滑动结束，即切换完毕或者加载完毕	
			case 0:
				//当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				}
				//当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}
	}

	public void setBannerClickListener(BannerItemAOnClick listener){
		mListener = listener;
	}
	
	public interface BannerItemAOnClick{
		void onclick(int position);
	}
}
