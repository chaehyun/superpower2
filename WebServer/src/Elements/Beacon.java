package Elements;

/**
 * ���� Ŭ����. DB ������ �ִ� ���� DTO��
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
