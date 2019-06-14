package cn.sdcet.project.dao;

import java.util.List;

import cn.sdcet.project.domain.AgentInfo;
import cn.sdcet.project.domain.Users;

public interface AgentInfoDao {

	/**
	 * �����Ϣ
	 * @param agentid ������id
	 * @return
	 */
	public boolean setAgentInfo(Users agentid);
	/**
	 * �޸Ĵ�����״̬
	 * @param agentid ������id
	 * @return
	 */
	public boolean setAgentInfoByUpdate(int agentId);
	
	/**
	 * ��ȡȫ��������id
	 * @return ȫ��������id
	 */
	public List<AgentInfo> selectAgentId();
	
	/**
	 * ����Ƿ�Ϊ������
	 * @param userId
	 * @return Ϊ�����˷�����
	 */
	public boolean isAgent(int userId);
	/**
	 * ��ȡ���ȴ�������Ϣ
	 * @return
	 */
	public AgentInfo getAgentOfHot();
	
	/**
	 * ��ȡ��õ�����Ŀ����ǰ���������
	 * @return
	 */
	public List<AgentInfo> getAgentOfTopFive();
	
	/**
	 * �����˵�������1
	 * @param agentid
	 * @return
	 */
	public boolean addThumbCount(int agentid);
	
	/**
	 * �ô������Ƿ�Ϊ���û������
	 * @param agentid
	 * @param userid
	 * @return
	 */
	public boolean isServiceTheUser(int agentid,int userid);
	
	
	/**
	 * ɾ����������Ϣ
	 * @param agentId
	 * @return
	 */
	public boolean deleteAgentById(int agentId);
	
	
	/**
	 * �鿴���û���ǰ�Ƿ��Ǵ�����
	 * @param agentId
	 * @return
	 */
	public boolean  isAgentAgo(int agentId);
	
	/**
	 * �鿴�Ƿ��иô����˴���ı���
	 * @param agentid
	 * @return
	 */
	public boolean haveHouse(int agentid);
	
}
