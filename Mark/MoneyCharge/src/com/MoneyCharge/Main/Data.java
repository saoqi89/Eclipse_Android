package com.MoneyCharge.Main;
 
import com.MoneyCharge.Model.ActivityManager;
import com.cwp.cmoneycharge.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Data extends Activity {
	Button paydata,incomedata,pidata; 
	int userid;
	public Data() {
		// TODO Auto-generated constructor stub
	}
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);// ���ò����ļ�
		
		ActivityManager.getInstance().addActivity(this); //����Activity,�˳���ť���ʱ����
		paydata=(Button)findViewById(R.id.paydata);
		incomedata=(Button)findViewById(R.id.incomedata);
		pidata=(Button)findViewById(R.id.pidata); 
		
	}
	@Override
	public void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001); 
		paydata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Data.this, PayData.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		incomedata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Data.this, IncomeData.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		pidata.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Data.this, PIData.class);
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
		 
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //���/����/���η��ؼ�
	    	Intent intent=new Intent(Data.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
