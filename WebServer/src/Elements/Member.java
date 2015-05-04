package Elements;

/**
 * È¸¿ø °´Ã¼
 * 
 * @author Minji, Seongjun
 * @since 2015/5/4
 * @version 2015/5/4
 */
public class Member {
	private String id;
	private String password;
	private String name;
	private char sex;
	private int age;
	private String favorite;
	private int enterCount;
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	
	public void setEnterCount(int enterCount) {
		this.enterCount = enterCount;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getName() {
		return this.name;
	}
	
	public char getSex() {
		return this.sex;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getFavorite() {
		return this.favorite;
	}
	
	public int getEnterCount() {
		return this.enterCount;
	}
}
