package Elements;

import java.util.Date;

public class Purchase {
	
	private String id;
	private String i_code;
	private int count;
	private Date pur_date;

	public String getId() {
		return id;
	}
	public String getI_code() {
		return i_code;
	}
	public int getCount() {
		return count;
	}
	public Date getPur_date() {
		return pur_date;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setI_code(String i_code) {
		this.i_code = i_code;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setPur_date(Date pur_date) {
		this.pur_date = pur_date;
	}
	
}
