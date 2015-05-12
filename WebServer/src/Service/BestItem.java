package Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

import Database.GetAllItems;
import Elements.Item;

public class BestItem {
	
	public static List<JSONObject> getBestItems() throws JSONException{
		
		List<Item> itemList = new ArrayList<Item>();
		List<JSONObject> bestlist = new ArrayList<JSONObject>();
		
		try {
			
			// item�޾ƿͼ� total_stock������ ����
			itemList = GetAllItems.doAction();
			Collections.sort(itemList, new DecCompare());
			
			// top 10���� ����
			for(int i=0; i<10; i++){
				JSONObject best = new JSONObject();
				best.put("MessageType", "res_best_list");
				best.put("Minor", itemList.get(i).getminor());
				best.put("Price", itemList.get(i).getprice());
				// 
				//
				// �̹��� ���� �κ�
				
				bestlist.add(best);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bestlist;
	}
}


// ��ǰ �������� ����

class DecCompare implements Comparator<Item>{
	
	public int compare(Item arg0, Item arg1){
		return arg0.getsales_volume() > arg1.getsales_volume() ? 
				-1 : arg0.getsales_volume() < arg1.getsales_volume() ? 1:0;
	}
}