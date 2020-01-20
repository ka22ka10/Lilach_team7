package Models;

public class User {
	String Name;
	String id;
	String PhoneNum;
	String UserName;
	private String Password;
	int ShopId;
	
	public User(String name,String ID,String Phone,String username,String Pass,int SHID) {
		Name=name;
		id=ID;
		PhoneNum= Phone;
		UserName=username;
		Password=pass;
		ShopId=SHID;
	}
}
