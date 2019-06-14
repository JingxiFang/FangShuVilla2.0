package cn.sdcet.project.domain;

public class ShopCar {
	private int id;
	private Users user;
	private House house;
	private int status;
	private String orderTime;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public ShopCar(){
		
	}

	public ShopCar(int id, Users user, House house , int check) {
		this.id = id;
		this.user = user;
		this.house = house;
		
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

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	


}
