package Elements;

import java.util.Date;

public class Coupon {
	
	private String c_code; // 상품코드
	private String i_code; // 아이템코드
	private int discount;
	private Date begin_date;
	private Date end_date;
	
	public void setc_code(String c_code){
		this.c_code = c_code;
	}
	
	public void seti_code(String i_code){
		this.i_code = i_code;
	}
	
	public void setdiscount(int discount){
		this.discount = discount;
	}
	
	public void setbegin_date(Date begin_date){
		this.begin_date = begin_date;
	}
	
	public void setend_date(Date end_date){
		this.end_date = end_date;
	}
	
	public String getc_code(){
		return this.c_code;
	}
	
	public String geti_code(){
		return this.i_code;
	}
	
	public int getdiscount(){
		return this.discount;
	}
	
	public Date getbegin_date(){
		return this.begin_date;
	}
	
	public Date getend_date(){
		return this.end_date;
	}
}
