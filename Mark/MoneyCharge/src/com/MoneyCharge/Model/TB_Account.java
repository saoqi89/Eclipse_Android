package com.MoneyCharge.Model;

public class TB_Account {
//_id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT ,username varchar(20),pwd varchar(50)
	private int _id ;
	private String username;
	private String pwd ; 
	public TB_Account( int id ,String username, String pwd ) {
		// TODO Auto-generated constructor stub
		super();
		this._id=id;
		this.pwd=pwd;
		this.username=username;
	}
	public TB_Account(){
		super();
	}
	public int get_id() {
		return _id;
	} 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	

}
