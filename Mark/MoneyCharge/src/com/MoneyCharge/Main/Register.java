
package com.MoneyCharge.Main;

import com.MoneyCharge.DAO.AccountDAO;
import com.MoneyCharge.DAO.ItypeDAO;
import com.MoneyCharge.DAO.PtypeDAO;
import com.MoneyCharge.Model.ActivityManager;
import com.MoneyCharge.Model.TB_Account;
/**
 * @author cwpcc
 *
 */
import com.cwp.cmoneycharge.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
public class Register extends Activity{
	EditText tusername;
	EditText tpwd,ttpwd;
	Button bregister,bcancle;
	Intent intentr;
	int userid;
	public Register() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);// 设置布局文件

		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		tusername=(EditText)findViewById(R.id.rusername);
		tpwd=(EditText)findViewById(R.id.rpassword);
		ttpwd=(EditText)findViewById(R.id.rrpassword);
		bregister=(Button)findViewById(R.id.btnrRegister);
		bcancle=(Button)findViewById(R.id.btnrCancle);
		tusername.setText("");
		tpwd.setText("");
		ttpwd.setText("");
		
	}
	@Override
	protected void onStart(){
		super.onStart();
		intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		bregister.setOnClickListener(new OnClickListener(){
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Register.this, Index.class);// 创建Intent对象
				AccountDAO accountDAO = new AccountDAO(Register.this);// 创建AccountDAO对象
				// 判断是否有密码及是否输入了密码
				if(tusername.getText().toString().isEmpty()||tpwd.getText().toString().isEmpty() ||ttpwd.getText().toString().isEmpty() ){
					// 弹出信息提示
					Toast.makeText(Register.this, "请将信息填写完整！",
							Toast.LENGTH_LONG).show(); 
				} else if(tpwd.getText().toString().equals(ttpwd.getText().toString())){
						userid=accountDAO.add(new TB_Account(0,tusername.getText().toString(),tpwd.getText().toString()));
						ItypeDAO itypedao=new ItypeDAO(Register.this);
						PtypeDAO ptypedao=new PtypeDAO(Register.this);
						itypedao.initData(userid);
						ptypedao.initData(userid);
						Toast.makeText(Register.this, "已登录："+tusername.getText().toString(),
								Toast.LENGTH_LONG).show();  
						intent.putExtra("cwp.id", userid);
						startActivity(intent);// 启动主Activity
				 }else{ 
						Toast.makeText(Register.this, "两次输入的密码不一致！！",
								Toast.LENGTH_LONG).show();
						tpwd.setText("");
						ttpwd.setText("");
					}
				
			}
			
		});
        bcancle.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
        	@Override
        	public void onClick(View arg0){
				Intent intent = new Intent(Register.this, Account.class);// 创建Intent对象
				intent.putExtra("cwp.id",userid);
				startActivity(intent);// 启动主Activity
        	}
        });
	}
}
