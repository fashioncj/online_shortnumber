package com.online.shortphonenumshow;


import java.io.IOException;
import java.text.SimpleDateFormat;



import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;


import android.R.id;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;

import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Weibo mWeibo;
	private static final String CONSUMER_KEY = "2791011232";
    private static final String REDIRECT_URL = "http://www.sina.com";
    public static Oauth2AccessToken accessToken;
    public static final String TAG = "sinasdk";
    Boolean hasver=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//service
				Intent intent=new Intent(MainActivity.this,Online_shortservise.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startService(intent);
				init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void init() {
		ImageButton sinashare=(ImageButton) findViewById(R.id.sinashare);
		sinashare.setOnClickListener(sharesina);
		ImageButton newsButton=(ImageButton) findViewById(R.id.news);
		newsButton.setOnClickListener(newsClickListener);
		ImageButton busButton=(ImageButton)findViewById(R.id.bus);
		busButton.setOnClickListener(busClickListener);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.about:
			openOptionsDialog();
			break;
		case R.id.quit:
			finish();
			break;
		case R.id.accept:
			weibo();
			break;
		default:
			//openOptionsDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void openOptionsDialog(){
		 new AlertDialog.Builder(MainActivity.this)
		 .setTitle("关于学生在线短号显示")
		 .setMessage("1\n" +
		 		     "2\n" +
		 		     "3\n" +
		 		     "版本号：0.9.5\n" +
		 		     "新浪微博：@fashioncj耳东人土土").show();
	 }
	
	private void weibo(){
		 mWeibo = Weibo.getInstance(CONSUMER_KEY, REDIRECT_URL); 
		 mWeibo.authorize(MainActivity.this, new AuthDialogListener());
		 //BMI.accessToken = AccessTokenKeeper.readAccessToken(this);
	 }
	
	class AuthDialogListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            MainActivity.accessToken = new Oauth2AccessToken(token, expires_in);
            if (MainActivity.accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(MainActivity.accessToken
                                .getExpiresTime()));
                
                try {
                    Class sso = Class
                            .forName("com.weibo.sdk.android.api.WeiboAPI");// 如果支持weiboapi的话，显示api功能演示入口按钮
               //     apiBtn.setVisibility(View.VISIBLE);
                } catch (ClassNotFoundException e) {
                    // e.printStackTrace();
                    Log.i(TAG, "com.weibo.sdk.android.api.WeiboAPI not found");

                }
              //  cancelBtn.setVisibility(View.VISIBLE);
               // AccessTokenKeeper.keepAccessToken(BMI.this,
                //        accessToken);
                Toast.makeText(MainActivity.this, "认证成功，再次点击分享到微博了~", Toast.LENGTH_SHORT)
                        .show();
                hasver=true;
            }
        }
        @Override
        public void onError(WeiboDialogError e) {
            Toast.makeText(getApplicationContext(),
                    "认证出错，错误信息为: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "认证取消",
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(getApplicationContext(),
                    "认证出现异常，错误信息为 : " + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }

    }

	public void share() {
		StatusesAPI api=new StatusesAPI(this.accessToken);
    	
    		api.update("#Online_短号显示#share", "", "", new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				//Toast.makeText(BMI.this, "网络问题，分享失败~", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onError(WeiboException arg0) {
				//Toast.makeText(BMI.this, "分享微博失败~TAT", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onComplete(String arg0) {
				//Toast.makeText(BMI.this, "分享微博成功~", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	
	private OnClickListener sharesina =new OnClickListener(){
	    public void onClick(View v){
	    	if(hasver){
	    		share();
	    	}
	    	else{
	    		weibo();
	    		if(hasver){
	    			
	    			share();
	    		}
	    	}
	    	
	    	
	    }};
	    
	  private OnClickListener newsClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!packageisexist("online.news")){
				 Uri uri = Uri.parse("http://www.online.sdu.edu.cn/app/upload/OnlineNews2.1.2.apk");  
				 Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				 startActivity(it);
			}
			else {
				Intent intent = new Intent();
		        intent.setComponent(new ComponentName("online.news", "online.news.SplashActivity"));
		        intent.setAction(Intent.ACTION_VIEW);
		        startActivity(intent);
			}
			
			
		}
	};
	
	 private OnClickListener busClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!packageisexist("com.sdu.online.schoolbus")){
				 Uri uri = Uri.parse("online.sdu.edu.cn/bus");  
				 Intent it = new Intent(Intent.ACTION_VIEW, uri);  
				 startActivity(it);
			}
			else {
				Intent intent = new Intent();
		        intent.setComponent(new ComponentName("com.sdu.online.schoolbus", "com.sdu.online.schoolbus.SplashActivity"));
		        intent.setAction(Intent.ACTION_VIEW);
		        startActivity(intent);
			}
			
		}
	};
	    
	  public boolean packageisexist(String packageName) {
		  PackageInfo packageInfo;
	         try {
	               packageInfo = this.getPackageManager().getPackageInfo(packageName, 0);
	 
	          } catch (NameNotFoundException e) {
	               packageInfo = null;
	               e.printStackTrace();
	          }
	         if(packageInfo == null) {
	            // System.out.println("not installed");
	             return false;
	         } else {
	             //System.out.println("is installed");
	             return true;
	         }
	      
	         }
}
