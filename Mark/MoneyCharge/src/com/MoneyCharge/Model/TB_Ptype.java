package com.MoneyCharge.Model;

public class TB_Ptype {
	//_id INTEGER  NOT NULL PRIMARY KEY,no not null integer AUTOINCREMENT ,typename varchar(50) 
		private int  _id;//�û�id
		private int no;//֧������id
		private String typename;//֧����������
		
	public TB_Ptype() {
		// TODO Auto-generated constructor stub
		super();
	}
	public TB_Ptype(int id,int no,String typename) {
		// TODO Auto-generated constructor stub
		super();
		this._id=id;
		this.no=no;
		this.typename=typename;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public void set_No(int no) {
		this.no = no;
	}
	public int getNo() {
		return no;
	} 
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
}