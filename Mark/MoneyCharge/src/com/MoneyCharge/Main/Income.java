package com.MoneyCharge.Main;

import java.util.List;

import com.MoneyCharge.DAO.DBOpenHelper;
import com.MoneyCharge.DAO.IncomeDAO;
import com.MoneyCharge.DAO.ItypeDAO;
import com.MoneyCharge.Model.ActivityManager;
import com.MoneyCharge.Model.TB_Income;
import com.cwp.cmoneycharge.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
public class Income extends Activity {
	int userid;
	Button baddincome; 
	ItypeDAO itypeDAO;
	
	public Income() {
		// TODO Auto-generated constructor stub
	}
	 
	ListView lvinfo;// 创建ListView对象
	String strType = "";// 创建字符串，记录管理类型

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.income);// 设置布局文件  

		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		lvinfo = (ListView) findViewById(R.id.lvinaccountinfo);// 获取布局文件中的ListView组件
		baddincome=(Button)findViewById(R.id.baddincome);//添加按钮
		itypeDAO=new ItypeDAO(Income.this); 
		 		
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();// 实现基类中的方法//  调用自定义方法显示收入信息
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("cwp.id",100000001);
		ShowInfo(R.id.btnininfo);
		lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// 记录收入信息
				//Toast.makeText(Income.this,"strInfo:"+strInfo,Toast.LENGTH_LONG).show();
				String strno = strInfo.substring(0, strInfo.indexOf('|')).trim();// 从收入信息中截取收入编号
				Intent intent = new Intent(Income.this, ModifyInP.class);// 创建Intent对象
				intent.putExtra("cwp.id", userid);
				intent.putExtra("cwp.message", new String[] { strno, strType });// 设置传递数据  
				startActivity(intent);// 执行Intent操作
			}
		});
		baddincome.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(Income.this, AddIncome.class);// 创建Intent对象
				intent.putExtra("cwp.id", userid);
				startActivity(intent);
			}
		});
	}
	private void ShowInfo(int intType) {// 用来根据传入的管理类型，显示相应的信息
		String[] strInfos = null;// 定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象
		strType = "btnininfo";// 为strType变量赋值
		IncomeDAO incomedao = new IncomeDAO(Income.this);// 创建IncomeDAO对象
		// 获取所有收入信息，并存储到List泛型集合中
		List<TB_Income> listinfos = incomedao.getScrollData(userid,0,
				(int) incomedao.getCount(userid));
		strInfos = new String[listinfos.size()];// 设置字符串数组的长度
		int m = 0;// 定义一个开始标识
		for (TB_Income tb_income : listinfos) {// 遍历List泛型集合
			// 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置 
			strInfos[m] =tb_income.getNo()+ " |  " +itypeDAO.getOneName(userid, tb_income.getType())
					+ "   " + String.valueOf(tb_income.getMoney()) + "元           "
					+ tb_income.getTime();
			m++;// 标识加1
		}
		// 使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	    	Intent intent=new Intent(Income.this,Index.class);
			intent.putExtra("cwp.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
