/**
 * 
 */
package com.MoneyCharge.Model;

/**
 * @author cwpcc
 *
 */
public class TB_Income {
//_id INTEGER  NOT NULL PRIMARY KEY,no not null integer  ,money decimal,time varchar(10),
	//type integer  ,handler varchar(100),mark varchar(200))
	private int _id;// �洢�û�id
	private int no;//�洢������
	private double money;// �洢������
	private String time;// �洢����ʱ��
	private int type;// �洢�������id
	private String handler;// �洢�����ַ
	private String mark;// �洢���뱸ע
	
	public TB_Income(int id,int no, double money, String time, int type,
			String handler, String mark) {
		// TODO Auto-generated constructor stub
		
	super();
	this._id = id;// Ϊ�û�id
	this.no=no;//Ϊ�����Ÿ�ֵ
	this.money = money;// Ϊ�����ֵ
	this.time = time;// Ϊ����ʱ�丳ֵ
	this.type = type;// Ϊ�������ֵ
	this.handler = handler;// Ϊ���븶���ֵ
	this.mark = mark;// Ϊ���뱸ע��ֵ
	}
	
	public TB_Income(){
		super();
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}
	

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

}