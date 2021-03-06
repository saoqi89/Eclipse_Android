/**
 * 
 */
package com.MoneyCharge.DAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.MoneyCharge.Main.PIData;
import com.MoneyCharge.Model.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


/**
 * @author cwpcc
 *
 */
public class IncomeDAO {

	private DBOpenHelper helper;// 创建DBOpenHelper对象
	private SQLiteDatabase db;// 创建SQLiteDatabase对象
	private int no=1;//编号
	private int userid=100000001;
	
	public IncomeDAO(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DBOpenHelper(context);// 初始化DBOpenHelper对象
	}
	/**
	 * 添加收入信息
	 * 
	 * @param tb_income
	 */
	public void add(TB_Income tb_income) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 执行添加收入信息操作
		db.execSQL(
				"insert into tb_income (_id,no,money,time,type,handler,mark) values (?,?,?,?,?,?,?)",
				new Object[] { tb_income.get_id(),tb_income.getNo(), tb_income.getMoney(),
						tb_income.getTime(), tb_income.getType(),
						tb_income.getHandler(), tb_income.getMark() });
	}
	/**
	 * 更新收入信息
	 * 
	 * @param TB_Income
	 */
	public void update(TB_Income tb_income) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 执行修改收入信息操作
		db.execSQL(
				"update Tb_income set money = ?,time = ?,type = ?,handler = ?,mark = ? where _id = ? and no=?",
				new Object[] { tb_income.getMoney(), tb_income.getTime(),
						tb_income.getType(), tb_income.getHandler(),
						tb_income.getMark(), tb_income.get_id(),tb_income.getNo() });
	}

	/**
	 * 查找收入信息
	 * 
	 * @param id，no
	 * @return
	 */
	public TB_Income find(int id,int no) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db
				.rawQuery(
						"select _id, no, money,time,type,handler,mark from Tb_income where _id = ? and no=?",
						new String[] { String.valueOf(id) , String.valueOf(no) });// 根据用户和编号查找收入信息，并存储到Cursor类中
		if (cursor.moveToFirst())// 遍历查找到的收入信息
		{
			// 将遍历到的收入信息存储到Tb_income类中
			return new TB_Income(
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getInt(cursor.getColumnIndex("no")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getInt(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("handler")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		return null;// 如果没有信息，则返回null
	}

	/**
	 * 刪除收入信息
	 * 
	 * @param ids
	 */
	public void detele(Integer... ids) {
		if (ids.length > 0)// 判断是否存在要删除的id
		{
			StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
			for (int i = 0; i < ids.length-1; i++)// 遍历要删除的id集合
			{
				sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
			}
			sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
			// 执行删除收入信息操作
			db.execSQL("delete from Tb_income where _id in (?) and no in  (" + sb + ")",
					(Object[]) ids);
		}
	}
 
	/**
	 * 获取收入信息
	 * 
	 * @param start
	 *            起始位置
	 * @param count
	 *            每页显示数量
	 * @return
	 */
	public List<TB_Income> getScrollData(int id,int start, int count) {
		List<TB_Income> tb_income = new ArrayList<TB_Income>();// 创建集合对象
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		// 获取所有收入信息
		Cursor cursor = db.rawQuery("select * from Tb_income where _id=? order by no limit ?,?",
				new String[] { String.valueOf(id),String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext())// 遍历所有的收入信息
		{
			// 将遍历到的收入信息添加到集合中
			tb_income.add(new TB_Income(
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getInt(cursor.getColumnIndex("no")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")), 
					cursor.getInt(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("handler")), 
					cursor.getString(cursor.getColumnIndex("mark"))));
		}
		return tb_income;// 返回集合
	}
	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public long getCount() {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db
				.rawQuery("select count(no) from tb_income", null);// 获取收入信息的记录数
		if (cursor.moveToNext())// 判断Cursor中是否有数据
		{
			return cursor.getLong(0);// 返回总记录数
		}
		return 0;// 如果没有数据，则返回0
	}
	public long getCount(int id) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db
				.rawQuery("select count(no) from tb_income where _id=?", new String[]{String.valueOf(id)});// 获取收入信息的记录数
		if (cursor.moveToNext())// 判断Cursor中是否有数据
		{
			return cursor.getLong(0);// 返回总记录数
		}
		return 0;// 如果没有数据，则返回0
	}
	/**
	 * 获取收入最大编号
	 * 
	 * @return
	 */
	public int getMaxNo(int id) {
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
		Cursor cursor = db.rawQuery("select max(no) from tb_income where _id=?",new String[]{String.valueOf(id)} );// 获取收入信息表中的最大编号
		while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		return 0;// 如果没有数据，则返回0
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public String addDays(String sdate, int days)
    {
    	SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy-MM-dd" ); 
    	Date date=new Date();
    	try {
			date=(Date)sdf.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        date=cal.getTime();
        String result=sdf.format(date);
        return result;
    }
	
	public Datapicker getDataOnDay(String date1,String date2){
		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象 
		Double data=0.0; 
		Datapicker datapicker;
		Cursor cursor = db.rawQuery("select total(money) as tmoney from tb_income where time >= ? and time <= ? and  _id =?",new String[]{date1,date2,String.valueOf(userid)} );// 获取支出信息表中的最大编号
		while (cursor.moveToNext())// 遍历所有的支出信息
		{		
		 
			// 将遍历到的支出信息添加到集合中
				  data=data+cursor.getDouble(cursor.getColumnIndex("tmoney"))	;
		}
		cursor.close();
		datapicker=new Datapicker(no,data,date1 ); 
		no++;
		return datapicker;// 返回集合
			// 将遍历到的支出信息添加到集合中
			 
		} // 返回集合
	 
		public List<Datapicker> getDataMonth(int id,int year,int month){
			String d1,d2;
			d1=String.valueOf(year)+"-" ;
			d2=String.valueOf(year)+"-" ;
			userid=id;
			no=1;
			switch(month){
				case 1:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 2:
						if(year%4==0 && year%100!=0 || year%400==0)
							{d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-29";
							}else{d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-28";}
						break;
				case 3:d1+="0"+String.valueOf(month)+"-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 4:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 5:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 6:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 7:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 8:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 9:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 10:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-31";break;
				case 11:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-30";break;
				case 12:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-31";break;
				
			}
 
			List<Datapicker> datapickerlist = new ArrayList<Datapicker>();
			Datapicker datapicker;
			for(String temp=d1;temp.compareTo(d2)<=0;){
				// 创建集合对象 
				datapicker=getDataOnDay(temp,temp);
				datapickerlist.add(datapicker);
				Log.i("cwp","===========pay"+temp+datapicker.getMoney().toString());
				temp=addDays(temp, 1);
			
			}
			
			return datapickerlist;
		} 


		public List<Datapicker> getDataAnytime(int id,String date1,String date2){
			 
			userid=id;
			no=1; 
			 
			List<Datapicker> datapickerlist = new ArrayList<Datapicker>();
			Datapicker datapicker;
			for(String temp=date1;temp.compareTo(date2)<=0;){
				// 创建集合对象  
				datapicker=getDataOnDay(temp,temp);
				datapickerlist.add(datapicker);
				temp=addDays(temp, 1);
			}
			
			return datapickerlist;
		}
		public void deleteUserData(int id){//清空用户数据
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象 
			db.execSQL("delete from tb_income where _id=?",new Object[]{id});
		}
		
		
		public List<KindData> getKDataOnDay(int id,String date1,String date2){
			db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象  
			List<KindData> kinddatalist=new ArrayList<KindData>();
			Cursor cursor = db.rawQuery("select type,  total(money) as tmoney from tb_income  where time >= ? and time <= ? and  _id =? group by type",new String[]{date1,date2,String.valueOf(id)} );// 获取支出信息表中的最大编号
			while (cursor.moveToNext())// 遍历所有的支出信息
			{		
			 
				// 将遍历到的支出信息添加到集合中
				kinddatalist.add(new KindData(
						cursor.getInt(cursor.getColumnIndex("type")), 
						cursor.getDouble(cursor.getColumnIndex("tmoney"))));
			}
			cursor.close();
			 
			 
			return kinddatalist;// 返回集合
				// 将遍历到的支出信息添加到集合中
				 
			} // 返回集合
		public List<KindData> getKDataOnMonth(int id,int year,int month){
			String d1,d2;
			d1=String.valueOf(year)+"-" ;
			d2=String.valueOf(year)+"-" ; 
			switch(month){
				case 1:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 2:
						if(year%4==0 && year%100!=0 || year%400==0)
							{d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-29";
							}else{d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-28";}
						break;
				case 3:d1+="0"+String.valueOf(month)+"-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 4:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 5:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 6:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 7:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 8:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-31";break;
				case 9:d1+="0"+String.valueOf(month)+ "-01";d2+="0"+String.valueOf(month)+ "-30";break;
				case 10:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-31";break;
				case 11:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-30";break;
				case 12:d1+=String.valueOf(month)+ "-01";d2+=String.valueOf(month)+ "-31";break;
				
			}
			return getKDataOnDay(id,d1,d2);
		}
}
