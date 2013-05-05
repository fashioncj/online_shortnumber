package com.online.shortphonenumshow;


import java.io.IOException;
import java.text.SimpleDateFormat;



import com.renren.api.connect.android.AsyncRenren;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.common.AbstractRequestListener;
import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.exception.RenrenError;
import com.renren.api.connect.android.status.StatusSetRequestParam;
import com.renren.api.connect.android.status.StatusSetResponseBean;
import com.renren.api.connect.android.view.RenrenAuthListener;
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
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    //renren
    private ProgressDialog progress;

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
		ImageButton renrenshare=(ImageButton) findViewById(R.id.renrenshare);
		renrenshare.setOnClickListener(sharerenren);
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
	    
	    private OnClickListener sharerenren =new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isOauthRenren()){
					Renren renren=new Renren("ea23b38f2a504d5fa74d7f43c5d04e86", "a7046049f1c34d93b67342dcc153d0d2", "233379", MainActivity.this);
					renren.authorize(MainActivity.this,new MyRenrenListener());
				}else{
					Renren renren=new Renren("ea23b38f2a504d5fa74d7f43c5d04e86", "a7046049f1c34d93b67342dcc153d0d2", "233379", MainActivity.this);
					Toast.makeText(getApplicationContext(), "该用户已经授权", Toast.LENGTH_SHORT).show();
		
				StatusSetRequestParam parm=new StatusSetRequestParam("shre");
//				progress = ProgressDialog.show(MainActivity.this,
//						"提示", "正在发布，请稍候");
//				StatusSetListener listener = new StatusSetListener(
//						MainActivity.this);
				try {
//					AsyncRenren aRenren = new AsyncRenren(renren);
//					aRenren.publishStatus(parm,listener,true);
					//renren.getSessionKey();
					//Log.i("tt", renren.getSessionKey());
					//renren.logout(getApplicationContext());
					renren.publishStatus(MainActivity.this, parm);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
		};
	    
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
	  
	  //---
	  private class StatusSetListener extends
		AbstractRequestListener<StatusSetResponseBean> {

	private Context context;

	private Handler handler;

	public StatusSetListener(Context context) {
		this.context = context;
		this.handler = new Handler(context.getMainLooper());
	}

	@Override
	public void onRenrenError(RenrenError renrenError) {
		final int errorCode = renrenError.getErrorCode();
		final String errorMsg = renrenError.getMessage();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (MainActivity.this != null) {
					//publishButton.setEnabled(true);
					//response.setText(errorMsg);
					if (progress != null) {
						progress.dismiss();
					}
				}
				if (errorCode == RenrenError.ERROR_CODE_OPERATION_CANCELLED) {
					Toast.makeText(context, "发送被取消", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}

	@Override
	public void onFault(Throwable fault) {
		final String errorMsg = fault.toString();
		handler.post(new Runnable() {

			@Override
			public void run() {
				if (MainActivity.this != null) {
					//publishButton.setEnabled(true);
					//response.setText(errorMsg);
					if (progress != null) {
						progress.dismiss();
					}
				}
				Toast.makeText(context, "发送失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onComplete(StatusSetResponseBean bean) {
		final String responseStr = bean.toString();
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (MainActivity.this != null) {
//					response.setText(responseStr);
//					publishButton.setEnabled(true);
					if (progress != null) {
						progress.dismiss();
					}
				}
				Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
	  
	  /**
	     * renren 判断用户是否已经授权
	     * @return
	     */
	    private boolean isOauthRenren(){
	    	boolean b = false;
	    	String token = getSharedPreferences("oauth_renren", Context.MODE_PRIVATE).getString("access_token", "");
	    	if(!"".equals(token)){
	    		b = true;
	    	}
	    	return b;
	    }
	    
	    /**
	     * 人人请求用户授权返回界面
	     * @author yanbin
	     *
	     */
	    private class MyRenrenListener implements RenrenAuthListener{

			@Override
			public void onComplete(Bundle values) {
				//服务器端返回的数据
//				{
//			    "access_token": "10000|5.a6b7dbd428f731035f771b8d15063f61.86400.1292922000-222209506",
//			    "expires_in": 87063,
//			    "refresh_token": "10000|0.385d55f8615fdfd9edb7c4b5ebdc3e39-222209506",
//			    "scope": "read_user_album read_user_feed"
//				}
				Bundle bundle = values;
				String access_token = bundle.getString("access_token");
				int expires_in = bundle.getInt("expires_in");
				String refresh_token = bundle.getString("refresh_token");
				String scope = bundle.getString("scope");
				System.out.println("验证服务器端返回的数据-->" + "access_token-->" + access_token
						+ ",expires_in-->" + expires_in
						+ ",refresh_token-->" + refresh_token
						+ ",scope-->" + scope);
				SharedPreferences sp = getSharedPreferences("oauth_renren", Context.MODE_PRIVATE);
				sp.edit().putString("access_token", access_token).commit();
				Toast.makeText(getApplicationContext(), "用户授权成功", Toast.LENGTH_SHORT).show();
				//结果：
				//验证服务器端返回的数据-->access_token-->206681|6.725b8c8b3457a7d2953868d63aaf4486.2592000.1346828400-473945629
				//,expires_in-->0,refresh_token-->null,
				//scope-->publish_feed create_album photo_upload read_user_album status_update
			}

			@Override
			public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
				Toast.makeText(getApplicationContext(), "异常", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCancelLogin() {
				//未作处理
			}

			@Override
			public void onCancelAuth(Bundle values) {
				//未作处理
			}
	    	
	    }

}
