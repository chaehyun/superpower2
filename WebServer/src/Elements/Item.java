package Elements;

public class Item {
	
	private String i_code;
	private String major; // 대분류
	private String middle; //중분류
	private String minor; //소분류
	private int sales_volume; // 판매량
	private int total_stock; // 재고량
	private int price;
	private String image; 
	
	public String geti_code() {
		return i_code;
	}
	public void seti_code(String i_code) {
		this.i_code = i_code;
	}
	
	public String getmajor() {
		return major;
	}
	public void setmajor(String major) {
		this.major = major;
	}
	
	public String getmiddle() {
		return middle;
	}
	public void setmiddle(String middle) {
		this.middle = middle;
	}
	
	public String getminor() {
		return minor;
	}
	public void setminor(String minor) {
		this.minor = minor;
	}
	
	public int getsales_volume() {
		return sales_volume;
	}
	public void setsales_volume(int sales_volume) {
		this.sales_volume = sales_volume;
	}
	
	public int gettotal_stock() {
		return total_stock;
	}
	public void settotal_stock(int total_stock) {
		this.total_stock = total_stock;
	}
	
	public int getprice() {
		return price;
	}
	public void setprice(int price) {
		this.price = price;
	}
	
	public String getimage() {
		return image;
	}
	public void setimage(String image) {
		this.image = image;
	}
}
