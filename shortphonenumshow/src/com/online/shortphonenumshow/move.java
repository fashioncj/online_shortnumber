package com.online.shortphonenumshow;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class move implements OnTouchListener  {
	private static final String TAG = "qt";  
	private int mPreviousx = 0;
	private int mPreviousy = 0;
    private int[] mCurrentLayout = new int[4] ;
    TextView textView = null;  

	
	public int[] getCurrentLayout(){
		return mCurrentLayout ;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent event) {
		 int iAction = event.getAction();
		 int iCurrentx = (int)event.getX();
		 int iCurrenty = (int)event.getY();
		textView = (TextView) arg0;
		switch(iAction)
		{
		case MotionEvent.ACTION_DOWN:
			mPreviousx = iCurrentx;
			mPreviousy = iCurrenty;
			break;
		case MotionEvent.ACTION_MOVE:
			int iDeltx = iCurrentx - mPreviousx;
			int iDelty = iCurrenty - mPreviousy;
			final int iLeft = textView.getLeft();
			final int iTop = textView.getTop();
			if(iDeltx != 0 || iDelty != 0)
				textView.layout(iLeft + iDeltx, 
						iTop + iDelty, 
						iLeft + iDeltx + textView.getWidth(), 
						iTop + iDelty + textView.getHeight());
			
			mCurrentLayout[0] = iLeft + iDeltx ;
			mCurrentLayout[1] = iTop + iDelty ;
			mCurrentLayout[2] = iLeft + iDeltx + textView.getWidth() ;
			mCurrentLayout[3] = iTop + iDelty + textView.getHeight() ;
			
			mPreviousx = iCurrentx - iDeltx;
			mPreviousy = iCurrenty - iDelty;
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}

}
