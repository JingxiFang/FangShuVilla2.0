package cn.sdcet.project.dao;

import java.util.List;

import cn.sdcet.project.domain.AgentInfo;
import cn.sdcet.project.domain.Users;

public interface AgentInfoDao {

	/**
	 * 添加信息
	 * @param agentid 代理人id
	 * @return
	 */
	public boolean setAgentInfo(Users agentid);
	/**
	 * 修改代理人状态
	 * @param agentid 代理人id
	 * @return
	 */
	public boolean setAgentInfoByUpdate(int agentId);
	
	/**
	 * 获取全部代理人id
	 * @return 全部代理人id
	 */
	public List<AgentInfo> selectAgentId();
	
	/**
	 * 检测是否为代理人
	 * @param userId
	 * @return 为代理人返回真
	 */
	public boolean isAgent(int userId);
	/**
	 * 获取最热代理人信息
	 * @return
	 */
	public AgentInfo getAgentOfHot();
	
	/**
	 * 获取获得点赞数目最多的前五个代理人
	 * @return
	 */
	public List<AgentInfo> getAgentOfTopFive();
	
	/**
	 * 代理人点赞数加1
	 * @param agentid
	 * @return
	 */
	public boolean addThumbCount(int agentid);
	
	/**
	 * 该代理人是否为该用户服务过
	 * @param agentid
	 * @param userid
	 * @return
	 */
	public boolean isServiceTheUser(int agentid,int userid);
	
	
	/**
	 * 删除代理人信息
	 * @param agentId
	 * @return
	 */
	public boolean deleteAgentById(int agentId);
	
	
	/**
	 * 查看该用户以前是否是代理人
	 * @param agentId
	 * @return
	 */
	public boolean  isAgentAgo(int agentId);
	
	/**
	 * 查看是否还有该代理人代理的别墅
	 * @param agentid
	 * @return
	 */
	public boolean haveHouse(int agentid);
	
}
