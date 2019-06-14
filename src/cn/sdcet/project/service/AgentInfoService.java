package cn.sdcet.project.service;

import java.util.List;

import cn.sdcet.project.dao.AgentInfoDao;
import cn.sdcet.project.dao.jdbc.AgentInfoDaoJDBCImpl;
import cn.sdcet.project.dao.jdbc.UsersDaoJDBCImpl;
import cn.sdcet.project.domain.AgentInfo;
import cn.sdcet.project.domain.Users;

public class AgentInfoService {
	
	/***
	 * 检测该用户是否为代理人
	 * @param userId
	 * @return 是返回真
	 */
	public boolean isAgent(int userId) {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.isAgent(userId);
	}
	/**
	 * 添加代理人信息
	 * @param user 用户集合
	 * @return
	 */
	public String addagentinfo(Users user){
		
		UsersDaoJDBCImpl userdao=new  UsersDaoJDBCImpl();
		//
		if(userdao.isExist(user.getLoginid())){
			int id=userdao.getNewUserId(user.getLoginid());
			user.setId(id);
			AgentInfoDao agentinfo=new AgentInfoDaoJDBCImpl();
			if(agentinfo.isAgentAgo(id))
			{
				if(agentinfo.setAgentInfoByUpdate(id)){
					return "添加成功";
				}
			}
			else{
				if(agentinfo.setAgentInfo(user)){
					return "添加成功";	
				}
			}
		}else {
			return "该用户不存在请尝试其他号码";
		}
		return "添加失败";
			
	}
	
	/**
	 * 获取全部代理人id
	 * @return 
	 */
	public List<AgentInfo> selectAgentId() {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.selectAgentId();
	}
	
	/**
	 * 获取最热代理人
	 * @return
	 */
	public AgentInfo getAgentOfHot(){
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		
		return agentDao.getAgentOfHot();
		
	}
	/**
	 * 获取代理人点赞前五名
	 * @return 
	 */
	public List<AgentInfo> selectAgentOfTopFive() {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.getAgentOfTopFive();
	}
	/**
	 * 给代理人点赞
	 * @param agentid
	 * @param userid
	 * @return
	 */
	public boolean addThumbCount(int agentid,int userid){
		//总体思路：预约过该代理人且已经完成预约的用户才能给代理人点赞
		//步骤1：检查该用户是否预约过该代理人代理的别墅且预约状态是完成
		AgentInfoDao ad=new AgentInfoDaoJDBCImpl();
		if(ad.isServiceTheUser(agentid, userid)){
			//步骤2：点赞数目加1
			if(ad.addThumbCount(agentid)){
				//点赞成功
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * 删除代理人 假删除
	 * @param agentId
	 * @returnv 1  还有代理的房屋   2 删除成功  3 删除失败
	 */
	public int deleteAgentInfo(int agentId){
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		
		//检查该代理人是够还有代理的房屋
		if(agentDao.haveHouse(agentId)){
			//如果有就提示不能删除
			return 1;
		}
		else{
			//如果没有   
			if(agentDao.deleteAgentById(agentId)){
				return 2;
			
			}
			else
			{
				return 3;

			}
		}
			
		
		
		
	}
	
}
