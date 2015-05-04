package Communication;

import java.io.Serializable;

public class Packet implements Serializable {
	public String msg;
	
	public Packet(String msg) {
		this.msg = msg;
	}
}
