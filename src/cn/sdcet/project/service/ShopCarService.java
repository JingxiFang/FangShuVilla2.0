package cn.sdcet.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sdcet.project.dao.PictureDao;
import cn.sdcet.project.dao.jdbc.HouseInfoJDBCImpl;
import cn.sdcet.project.dao.jdbc.PictureDaoJDBCImpl;
import cn.sdcet.project.dao.jdbc.ShopCarDaoJDBCImpl;
import cn.sdcet.project.domain.House;
import cn.sdcet.project.domain.HouseDetailInfo;
import cn.sdcet.project.util.SqlHelper;

public class ShopCarService {
	/**
	 * 获取购物车里边的房子信息
	 * @param userid 用户名
	 * @return 房子列表
	 */
	public List<House> getShopCar(int userid,int status) {
		
		List<House> shopCarList=new ArrayList<House>();
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		shopCarList=shopCarDao.getShopCar(userid,status);
		return shopCarList;
	}
	
	/**
	 * 添加到购物车中
	 * @param houseid 房子id
	 * @param userid  用户id
	 * @return 是否添加成功
	 */
	public boolean addShopCar(int houseid, int userid) {
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		HouseInfoJDBCImpl hd= new HouseInfoJDBCImpl();
		if( hd.addHouseOrderCount(houseid)&&shopCarDao.addShopCar(houseid, userid))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * 删除一条购物车条目
	 * @param userid 用户编号
	 * @return 是否成功
	 */
	public boolean delShopCar(int userid, int houseid) {
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		
		if(shopCarDao.delShopCar(userid, houseid)){
			return true;
		
		}
		else{
			return false;
		}
	}
	
	/**
	 * 是不是已经预约了
	 * @param userid
	 * @param houseid
	 * @return 预约返回真
	 */
	public boolean isOrder(int userid, int houseid) {
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		return shopCarDao.isOrder(userid, houseid);
	}
	
	public boolean endOrder(int userid, int houseid) {
		// TODO Auto-generated method stub
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		return shopCarDao.endOrder(userid, houseid);
	}
	
	/**
	 * 获取代理人当前未处理的订单
	 * @param agentid
	 * @return
	 */
	public List<Map<String,Object>> getHouseInfoOfOrdered(int agentid,int status){
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		List<Map<String,Object>> listReturn =shopCarDao.getHouseInfoOfOrdered(agentid,status);
		HouseDetailInfo house=new HouseDetailInfo();
		PictureService picService=new PictureService();
		int houseid;
		if(listReturn.size()>0)
		{
			for(Map<String,Object> map:listReturn)
			{
				
				house=(HouseDetailInfo)map.get("HouseDetailInfo");
				String pic=picService.selectPictureFirst(house.getHouse().getId());
				map.put("pic", pic);
			}
		}
		return listReturn ;
	}
	
	
}
