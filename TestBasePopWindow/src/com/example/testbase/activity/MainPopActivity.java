package com.example.testbase.activity;


import com.example.testbase.popwindow.R;



import com.example.testbase.utils.ScreenUtils;
import com.example.testbase.utils.T;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainPopActivity extends Activity implements OnClickListener {
	private String TAG = MainPopActivity.class.getName();
	private Context context;
	private Button btn;
	private TextView tv_state;
	private RelativeLayout rel_titlebar;
	private PopupWindow mPopTop;
	private PopupWindow mPopBottom;
	
	private boolean hasMeasured = false;
	private int height = 0;
	
	private static final String[] strs = new String[] {
		    "11", "22", "33", "44", "55", "66", "77", "88", "99", "10"
		    };//定义一个String数组用来显示ListView的内容
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setupView();
		initValue();
		setLinstener();
		fillData();

	}

	private void setupView() {
		rel_titlebar = (RelativeLayout) this.findViewById(R.id.ll_title);
		tv_state = (TextView) this.findViewById(R.id.tv_state);
		 btn = (Button) this.findViewById(R.id.btn);
		 lv = (ListView) findViewById(R.id.lv);
	

	}

	private void initValue() {
		context = this;
		
		//测量titleBar高度
		ViewTreeObserver vto = rel_titlebar.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				if (hasMeasured == false) {

					height = rel_titlebar.getMeasuredHeight();
				//	int width = rel_titlebar.getMeasuredWidth();
					// 获取到宽度和高度后，可用于计算

					hasMeasured = true;

				}
				return true;
			}
		});
		lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));
		 setMyPop();
	}

	private void setLinstener() {
		 tv_state.setOnClickListener(this);
		 btn.setOnClickListener(this);
		
		 lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				int[] location = new int[2];
				 view.getLocationOnScreen(location);
				
				 mPopTop.showAtLocation(view, Gravity.NO_GRAVITY, ScreenUtils.getScreenWidth(context) / 4, location[1]);
				// mPopTop.showAtLocation(view, Gravity.NO_GRAVITY,ScreenUtils.getScreenWidth(context) / 4, location[1]);
							
			}
		});
	}

	private void fillData() {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		 case R.id.tv_state:
		//	 setMyPop();
			 mPopTop.showAtLocation(rel_titlebar, Gravity.TOP, 0, height +ScreenUtils.getStatusHeight(context)); //titleBar 正下方中间位置
		//	 mPopTop.showAsDropDown(rel_titlebar);//titleBar 正左下方
		//	 mPopTop.showAsDropDown(rel_titlebar, ScreenUtils.getScreenWidth(context)/4, 0); //titleBar 正下方中间位置
			
			 break;
		 case R.id.btn:
			 showFullPop();
			 break;
	
		default:
			break;
		}

	}
	
private void setMyPop(){
	mPopTop = new PopupWindow(context);
	int w = ScreenUtils.getScreenWidth(context);
	int h = ScreenUtils.getScreenHeight(context);
	mPopTop.setWidth(w / 2);
	mPopTop.setHeight(LayoutParams.WRAP_CONTENT);
	mPopTop.setFocusable(true);////获取焦点    
	mPopTop.setTouchable(true);
	mPopTop.setOutsideTouchable(true);//设置popupwindow外部可点击    
  //	mPopTop.update();// 刷新状态	
	ColorDrawable dw = new ColorDrawable(0000000000);// 实例化一个ColorDrawable颜色为半透明	
	mPopTop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
	mPopTop.setAnimationStyle(R.style.AnimationPreview);//设置显示和消失动画
	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	View conentView = inflater.inflate(R.layout.pop_top, null);
	setContentViewClickListener(conentView);
	mPopTop.setContentView(conentView);
	
}

private void setContentViewClickListener(View conentView){
	LinearLayout lin_car_full = (LinearLayout) conentView
			.findViewById(R.id.lin_car_full);
	LinearLayout lin_car_empty = (LinearLayout) conentView
			.findViewById(R.id.lin_car_empty);

	LinearLayout lin_car_half = (LinearLayout) conentView
			.findViewById(R.id.lin_car_half);
	LinearLayout lin_car_rest = (LinearLayout) conentView
			.findViewById(R.id.lin_car_rest);
	lin_car_empty.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			
			  
			   T.showLong(context, "空车");
			   mPopTop.dismiss();
		}
	});

	lin_car_full.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			
          
           T.showLong(context, "满载");
           mPopTop.dismiss();
		}
	});

	lin_car_half.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
		
			 
			   T.showLong(context, "半载");
			   mPopTop.dismiss();
		}
	});

	lin_car_rest.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			 
			   T.showLong(context,"勿扰");
			   mPopTop.dismiss();
		}
	});
}


private void showFullPop() {

		View view = LayoutInflater.from(this).inflate(R.layout.pop_full,
				null);
		LinearLayout layout_all;
		RelativeLayout layout_choose;
		RelativeLayout layout_photo;
		RelativeLayout layout_cancel;
		layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
		layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
		layout_cancel=(RelativeLayout) view.findViewById(R.id.layout_cancel);
		layout_photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				   T.showLong(context,"点击拍照");
				   mPopBottom.dismiss();
				
			}
		});
		layout_choose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			
				 T.showLong(context,"点击相册");
				 mPopBottom.dismiss();
			}
		});
		
		
		layout_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				 T.showLong(context,"点击取消");
				mPopBottom.dismiss();
			
			}
		});

		mPopBottom = new PopupWindow(view);
//		mPopBottom.setTouchInterceptor(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//					mPopBottom.dismiss();
//					return true;
//				}
//				return false;
//			}
//		});

		mPopBottom.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		mPopBottom.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
		mPopBottom.setTouchable(true);
		mPopBottom.setFocusable(true);
		mPopBottom.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0000000000);
		mPopBottom.setBackgroundDrawable(dw);
		// 动画效果 从底部弹起
		mPopBottom.setAnimationStyle(R.style.Animations_GrowFromBottom);

		mPopBottom.showAtLocation(rel_titlebar, Gravity.BOTTOM, 0, 0);//parent view随意

	}



}
