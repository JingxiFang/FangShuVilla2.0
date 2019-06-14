package cn.sdcet.project.dao.jdbc;

import java.util.*;

import cn.sdcet.project.dao.ShopCarDao;
import cn.sdcet.project.domain.House;
import cn.sdcet.project.domain.HouseDetailInfo;
import cn.sdcet.project.domain.ShopCar;
import cn.sdcet.project.domain.UserInfo;
import cn.sdcet.project.domain.Users;

import cn.sdcet.project.util.SqlHelper;

public class ShopCarDaoJDBCImpl implements ShopCarDao {

	@Override
	public boolean delShopCar(int userid, int houseid) {
		// TODO Auto-generated method stub
		String sql="delete from shopcar where houseid=? and userid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(houseid);
		params.add(userid);
		
		return SqlHelper.Execute(sql, params);
	}
	

	@Override
	public boolean addShopCar(int houseid, int userid) {
		// TODO Auto-generated method stub
		String sql="insert into shopcar(houseid,userid) values(?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(houseid);
		params.add(userid);
		return SqlHelper.Execute(sql, params);
		
	}

	@Override
	public List<House> getShopCar(int userid,int status) {
		// TODO Auto-generated method stub
		List<House> shopCarList=new ArrayList<House>();
		HouseInfoJDBCImpl houseDao=new HouseInfoJDBCImpl();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql="select HOUSEID,STATUS,ORDERTIME from shopcar where userid=? and status=?";
		List<Object> params=new ArrayList<Object>();
		params.add(userid);
		params.add(status);
		list=SqlHelper.returnMultipleResult(sql, params);
		if(list.size()>0)
		{
			for(Map<String,Object> map:list)
			{
				House house=new House();
				
				house = houseDao.getHouseInfoRoughById(Integer.valueOf(map.get("HOUSEID").toString()));
				shopCarList.add(house);
			}
			
		}
		return shopCarList;
	}

	@Override
	public boolean isOrder(int userid, int houseid) {
		// TODO Auto-generated method stub
		List<Object> params =new ArrayList<Object>();
		String  sql="select count(*) as COUNT from ShopCar  where userid=? and houseid=?";
		boolean flag=false;
		params.add(userid);
		params.add(houseid);
		Map<String,Object> count=SqlHelper.returnSimpleResult(sql, params);
		if(count.size()>0){
			if(Integer.parseInt(count.get("COUNT").toString())==1){
				flag=true;
			}
		}
		return flag;
		
	}


	@Override
	public boolean delShopCar(int houseid) {
		// TODO Auto-generated method stub
		String sql="delete from shopcar where houseid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(houseid);
		return SqlHelper.Execute(sql, params);
	}


	@Override
	public boolean endOrder(int userid, int houseid) {
		// TODO Auto-generated method stub
		String sql="update shopcar set status=1 where houseid=? and userid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(houseid);
		params.add(userid);
	
		
		return SqlHelper.Execute(sql, params);
	}


	@Override
	public List<Map<String,Object>> getHouseInfoOfOrdered(int agentid,int status) {
		String sql="exec proc_agent_house_order ?,?";
		List<Object> params=new ArrayList<Object>();
		params.add(agentid);
		params.add(status);
		List<Map<String,Object>> listReturn= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list=SqlHelper.returnMultipleResult(sql, params);
		if(list.size()>0)
		{
			for(Map<String,Object> map:list)
			{
				Map<String,Object> mapReturn =new HashMap<String,Object> ();
				
				HouseDetailInfo houseDetail=new HouseDetailInfo();
				House house=new House();
				house.setId(Integer.parseInt( map.get("ID").toString()));
				house.setName( map.get("HOUSENAME").toString());
				house.setFineFlag( Integer.parseInt( map.get("FINEFLAG").toString()));
				houseDetail.setHouse(house);
				houseDetail.setH_addressdel( map.get("H_ADDRESSDEL").toString());
				
				mapReturn.put("HouseDetailInfo",houseDetail);
				
				UserInfo ui=new UserInfo();
				ui.setGender(map.get("GENDER").toString());
				ui.setName(map.get("USERNAME").toString());
				ui.setContact(map.get("CONTACT").toString());
				ui.setId(Integer.parseInt(map.get("USERID").toString()));
				mapReturn.put("UserInfo",ui);
				
				ShopCar sc=new ShopCar();
				sc.setStatus(Integer.parseInt( map.get("STATUS").toString()));
				sc.setOrderTime(map.get("OrderTime").toString());
				mapReturn.put("ShopCar",sc);
				
				
				listReturn.add(mapReturn);	
			}	
		}
		return listReturn;
	}

}
