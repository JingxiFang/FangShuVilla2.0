package cn.sdcet.project.domain;

public class UserInfo {
	private int id;
	private Users user;
	private String gender;
	private String head;
	private String name;
	private String contact;
	private String cardid;
	private String email;
	public String getEmail() {
		return email;
	}
	private int isAgent=0;
	public int getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(int isAgent) {
		this.isAgent = isAgent;
	}

	public void setEmail(String email) {
		this.email = email;
		
	}

	public UserInfo(){
		
	}

	public UserInfo(int id, Users user, String gender, String head, String name,String email,
			String contact, String cardid) {
		this.id = id;
		this.user = user;
		this.gender = gender;
		this.head = head;
		this.name = name;
		this.contact = contact;
		this.cardid = cardid;
		this.email=email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if("1".equals(gender))
		{
			gender="ÄÐ";
		}
		else if("0".equals(gender))
		{
			gender="Å®";
		}
		this.gender = gender;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	
	

}
