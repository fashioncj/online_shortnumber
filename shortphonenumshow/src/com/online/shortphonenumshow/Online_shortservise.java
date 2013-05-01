package com.online.shortphonenumshow;



import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class Online_shortservise extends Service{

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
    
    public class TelListener extends PhoneStateListener {
    	
    	
    	private Context context;
    	private WindowManager wm;
    	private TextView tv;
    	
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
    			params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;  
    			//params.type = WindowManager.LayoutParams.TYPE_PHONE; 
    			params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; 
    			Log.i("tel",incomingNumber); 
    			params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
    			params.height = WindowManager.LayoutParams.WRAP_CONTENT;  
                //params.format = PixelFormat.RGBA_8888;
    			
    			if(incomingNumber.length()>1){
    			tv = new TextView(context); 
    			tv.setOnTouchListener(new move());
    			tv.setTextSize(20);
    			String ansString=shornum(incomingNumber);
    			Log.i("1", ansString);
    			tv.setText("短号提示：\n" + ansString);
    			
    			wm.addView(tv, params);}
    			
    					
    		}else if(state == TelephonyManager.CALL_STATE_IDLE){
    			if(wm != null){
    				Log.i("tel","3");
    				wm.removeView(tv);
    			}
    		}
    	}
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
}
