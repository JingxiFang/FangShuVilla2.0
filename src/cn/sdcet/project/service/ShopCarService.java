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
	 * ��ȡ���ﳵ��ߵķ�����Ϣ
	 * @param userid �û���
	 * @return �����б�
	 */
	public List<House> getShopCar(int userid,int status) {
		
		List<House> shopCarList=new ArrayList<House>();
		ShopCarDaoJDBCImpl shopCarDao=new ShopCarDaoJDBCImpl();
		shopCarList=shopCarDao.getShopCar(userid,status);
		return shopCarList;
	}
	
	/**
	 * ��ӵ����ﳵ��
	 * @param houseid ����id
	 * @param userid  �û�id
	 * @return �Ƿ���ӳɹ�
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
	 * ɾ��һ�����ﳵ��Ŀ
	 * @param userid �û����
	 * @return �Ƿ�ɹ�
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
	 * �ǲ����Ѿ�ԤԼ��
	 * @param userid
	 * @param houseid
	 * @return ԤԼ������
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
	 * ��ȡ�����˵�ǰδ����Ķ���
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
