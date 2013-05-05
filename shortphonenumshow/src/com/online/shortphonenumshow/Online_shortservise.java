package com.online.shortphonenumshow;




import android.R.menu;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

public class Online_shortservise extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override  
    public void onCreate() {  
        Log.i("tag", "onCreate");
        
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter); 
        
    }  
  
    @Override  
    public void onDestroy() {  
        Log.i("TAG", "onDestroy"); 
        super.onDestroy();
        unregisterReceiver(mReceiver);

        //Toast.makeText(this, "stop media player", Toast.LENGTH_SHORT);  
        
    }  
  
    @Override  
    public void onStart(Intent intent, int startId) {  
        Log.i("TAG", "onStart");  
       
        
    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
        	TelephonyManager telM = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  
        	telM.listen(new TelListener(context), PhoneStateListener.LISTEN_CALL_STATE);
        	context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);


        }

	
    };
    
    public class TelListener extends PhoneStateListener implements OnClickListener {
    	
    	
    	private Context context;
    	private WindowManager wm;
    	private TextView tv;
    	//---
        private MyTextView mv;
        boolean flag=true;
    	
    	public TelListener(Context context){
    		this.context = context;
    	}

    	@Override
    	public void onCallStateChanged(int state, String incomingNumber) {
    		// TODO Auto-generated method stub
    		//super.onCallStateChanged(state, incomingNumber);
    		
    		if(state == TelephonyManager.CALL_STATE_RINGING){
    			Log.i("tel","2");
    			wm = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);  
    			WindowManager.LayoutParams params = new WindowManager.LayoutParams(); 
    			//islock
    			if(islock()){
    				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;  
    				params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; 
    				  
    				params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
    				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    				Log.i("tel",incomingNumber); 
        			if(incomingNumber.length()>1){
        			tv = new TextView(context); 
        			tv.setTextSize(20);
        			tv.setTextColor(getResources().getColor(com.online.shortphonenumshow.R.color.shandahong));
        			tv.setBackgroundColor(getResources().getColor(com.online.shortphonenumshow.R.color.cornsilk));
        			String ansString=shornum(incomingNumber);
        			Log.i("1", ansString);
        			tv.setText("短号提示：\n" + ansString);
        			flag=true;
        			wm.addView(tv, params);
        			}
    			}
    			else{
    			//params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;  
    			
 //   			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR; 
 //   			params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; 
    			//params.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    			//params.flags=WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
    			
    			//moveable
 
    			params = ((FloatApplication) getApplication()).getWindowParams(); 
    			//params.format=1;
   			    params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
 //   			params.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//    			params.flags = params.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
    			params.flags=params.flags|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;//yes
//    			params.alpha = 1.0f;  
//    	        
    		   //  params.gravity=Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;   //调整悬浮窗口至左上角   

    			params.gravity=Gravity.LEFT|Gravity.TOP;
    			//    		        //以屏幕左上角为原点，设置x、y初始值   
    		    // params.flags=WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
    		     params.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//yes
    		     params.y=Gravity.CENTER_VERTICAL;  
    		     params.x=Gravity.CENTER_HORIZONTAL; 
//    		     params.width=140;
//    		     params.height=140;
    			
    		
    			params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
    			params.height = WindowManager.LayoutParams.WRAP_CONTENT; 
    			//params.width=WindowManager.LayoutParams.FILL_PARENT;
    			//params.height=WindowManager.LayoutParams.FILL_PARENT;
                //params.format = PixelFormat.RGBA_8888;
    			
    			//gongneng
    			
    			Log.i("tel",incomingNumber); 
    			if(incomingNumber.length()>1){
//    			tv = new TextView(context); 
//    			//tv.setOnTouchListener(new move());
//    			//tv.onTouchEvent();
//    			tv.setTextSize(20);
//    			tv.setTextColor(getResources().getColor(com.online.shortphonenumshow.R.color.shandahong));
//    			tv.setBackgroundColor(getResources().getColor(com.online.shortphonenumshow.R.color.cornsilk));
    			String ansString=shornum(incomingNumber);
    			Log.i("1", ansString);
//    			tv.setText("短号提示：\n" + ansString);
//    			
//    			wm.addView(tv, params);
    			
//    			dView=new MyTextView(context, null);
//    			dView.setOnTouchListener(null);
//    			dView.setTextSize(20);
//    			String ansString=shornum(incomingNumber);
//    			Log.i("1", ansString);
//    			dView.setText("短号提示：\n" + ansString);
//    			
//    			wm.addView(dView, params);
    				mv=new MyTextView(getApplicationContext());
    				mv.setOnClickListener(this);
    				mv.setBackgroundColor(getResources().getColor(com.online.shortphonenumshow.R.color.cornsilk));
    				mv.setText("短号提示：\n" + ansString);
    				mv.setTextColor(getResources().getColor(com.online.shortphonenumshow.R.color.shandahong));
    				mv.setTextSize(20);
    				flag=false;
    				wm.addView(mv, params);

    			}
    			}
    				
    			
    					
    		}
    		else if(state == TelephonyManager.CALL_STATE_IDLE){
    			if(wm != null){
    				Log.i("tel","3");
    				if(flag){
    		    wm.removeView(tv);}
    				else{
   				wm.removeView(mv);}
    			}
    		}
    	}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
    	
    	//==
    	
    }
    
    
    
    
  public String shornum(String num) {
	  String num1 = null;
	  if(num.length()==6){
		  //短号
		  String a=num.substring(2);
		  Log.i("short", a);
		  //num=getContactNameByPhoneNumber(this.getBaseContext(), a);
		  num1=getPeople(a);
		  Log.i("short", num1);
	  }
	  else{
		  num1=getPeople(num);
		  Log.i("short", num1);
		  //num1=getContactNameByPhoneNumber(this.getBaseContext(), num);
	  }
	  
	if(num1=="")return "<未知号码>";  
	return num1;
}  

  /*  
   * 根据电话号码取得联系人姓名  
   */   
  public String getPeople(String num) {   
      String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,   
                              ContactsContract.CommonDataKinds.Phone.NUMBER};   
 
      Log.d("tag", "getPeople ---------");   
         
      // 将自己添加到 msPeers 中   
      Cursor cursor = this.getContentResolver().query(   
              ContactsContract.CommonDataKinds.Phone.CONTENT_URI,   
              projection,    // Which columns to return.   
             // ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + num + "'", // WHERE clause.   
              ContactsContract.CommonDataKinds.Phone.NUMBER + " like '%" + num + "'", // WHERE clause.   
              null,          // WHERE clause value substitution   
              null);   // Sort order.   
 
      if( cursor == null ) {   
          Log.d("tag", "getPeople null");   
          return "";   
      }   
      Log.d("tag", "getPeople cursor.getCount() = " + cursor.getCount());   
      for( int i = 0; i < cursor.getCount(); i++ )   
      {   
          cursor.moveToPosition(i);   
             
          // 取得联系人名字   
          int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME); 
          int numberindex=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
          String name = cursor.getString(nameFieldColumnIndex);  
          String number=cursor.getString(numberindex);
          if(name==null)name="<未知>";
          Log.i("Contacts", "" + name + " .... " + number+".."+nameFieldColumnIndex); // 这里提示 force close   
         // m_TextView.setText("联系人姓名：" + name); 
          String ansString=name+":"+number;
          return ansString;
      }   
      return null;
  } 
  
   public boolean islock(){
	   KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);  
	     
	    if (mKeyguardManager.inKeyguardRestrictedInputMode()) { 
	        // keyguard on 
	    	return true;
	    } 
	    return false;

   }
}
