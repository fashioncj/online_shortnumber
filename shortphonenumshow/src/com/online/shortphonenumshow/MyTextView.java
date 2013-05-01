package com.online.shortphonenumshow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyTextView extends TextView{
	//��������״̬ �ƶ���ֹͣ�ƶ�
    private final int STATE_MOVE = 1;
    private final int STATE_STOP = 0;
    //�����¼��ǰ״̬�ı���
    private int mState ;
    //����һ����¼��ǰ�����ı���
    private int mAction ;
    //��ʼ����ǰ�ؼ���λ��
    private int currentX = 0;
    private int currentY = 0;
    //��¼��һ�οؼ���λ��
    private int previousX = 0;
    private int previousY = 0;
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
    //���캯�������õ�ǰ״̬ΪSTATE_STOP����ʾ���ƶ�
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mState = STATE_STOP;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mAction = event.getAction(); //��ȡ��ǰ����
        currentX = (int) event.getX();  //��ȡ��ǰX����
        currentY = (int) event.getY();  //��ȡ��ǰY����
        switch(mAction)
        {
             case MotionEvent.ACTION_DOWN: //���¿ؼ�ʱ
                 mState = STATE_MOVE ;  //״̬����Ϊ���ƶ�
                 previousX = currentX;  //��¼�ϴ�X,Y����
                 previousY = currentY;
                 break;
             case MotionEvent.ACTION_MOVE: //��ʼ�ƶ��ؼ�
                 mState = STATE_MOVE ;
                 int detelX = currentX - previousX ;
                 int detelY = currentY - previousY ;
                 int mTop = getTop();  //�ؼ�δ�ƶ�ǰ���븸���ڶ����ľ���
                 int mLeft = getLeft();  //�ؼ�δ�ƶ�ǰ���븸������ߵľ���
                 if(detelX!=0 || detelY!=0) //�ؼ����ƶ�
                 {
                   //����ָ���ؼ���λ�úʹ�С
                     this.layout(detelX+mLeft, detelY+mTop,detelX+mLeft+getWidth(),  detelY+mTop+getHeight());
                 }
                 previousX = currentX - detelX ; //��¼��һ�ε�X��Y����
                 previousY = currentY - detelY ;
                 break;
 
             case MotionEvent.ACTION_UP: //�����¼������������뿪��Ļ
                 mState = STATE_STOP;
                 break;
   
             case MotionEvent.ACTION_CANCEL: //ȡ�������¼�
                 mState = STATE_STOP;
                 break;
          }
          return true;
      }
}
