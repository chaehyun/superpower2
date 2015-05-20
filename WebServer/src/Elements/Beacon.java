package Elements;

/**
 * 비콘 클래스. DB 정보에 있는 비콘 DTO용
 * 
 * @author Minji, Seongjun
 */
public class Beacon {

	private String macAddr;
	private String location;

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMacAddr() {
		return this.macAddr;
	}

	public String getLocation() {
		return this.location;
	}
}
