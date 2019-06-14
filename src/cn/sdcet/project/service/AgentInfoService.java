package cn.sdcet.project.service;

import java.util.List;

import cn.sdcet.project.dao.AgentInfoDao;
import cn.sdcet.project.dao.jdbc.AgentInfoDaoJDBCImpl;
import cn.sdcet.project.dao.jdbc.UsersDaoJDBCImpl;
import cn.sdcet.project.domain.AgentInfo;
import cn.sdcet.project.domain.Users;

public class AgentInfoService {
	
	/***
	 * �����û��Ƿ�Ϊ������
	 * @param userId
	 * @return �Ƿ�����
	 */
	public boolean isAgent(int userId) {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.isAgent(userId);
	}
	/**
	 * ��Ӵ�������Ϣ
	 * @param user �û�����
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
					return "��ӳɹ�";
				}
			}
			else{
				if(agentinfo.setAgentInfo(user)){
					return "��ӳɹ�";	
				}
			}
		}else {
			return "���û��������볢����������";
		}
		return "���ʧ��";
			
	}
	
	/**
	 * ��ȡȫ��������id
	 * @return 
	 */
	public List<AgentInfo> selectAgentId() {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.selectAgentId();
	}
	
	/**
	 * ��ȡ���ȴ�����
	 * @return
	 */
	public AgentInfo getAgentOfHot(){
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		
		return agentDao.getAgentOfHot();
		
	}
	/**
	 * ��ȡ�����˵���ǰ����
	 * @return 
	 */
	public List<AgentInfo> selectAgentOfTopFive() {
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		return agentDao.getAgentOfTopFive();
	}
	/**
	 * �������˵���
	 * @param agentid
	 * @param userid
	 * @return
	 */
	public boolean addThumbCount(int agentid,int userid){
		//����˼·��ԤԼ���ô��������Ѿ����ԤԼ���û����ܸ������˵���
		//����1�������û��Ƿ�ԤԼ���ô����˴���ı�����ԤԼ״̬�����
		AgentInfoDao ad=new AgentInfoDaoJDBCImpl();
		if(ad.isServiceTheUser(agentid, userid)){
			//����2��������Ŀ��1
			if(ad.addThumbCount(agentid)){
				//���޳ɹ�
				return true;
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * ɾ�������� ��ɾ��
	 * @param agentId
	 * @returnv 1  ���д���ķ���   2 ɾ���ɹ�  3 ɾ��ʧ��
	 */
	public int deleteAgentInfo(int agentId){
		AgentInfoDaoJDBCImpl agentDao=new AgentInfoDaoJDBCImpl();
		
		//���ô������ǹ����д���ķ���
		if(agentDao.haveHouse(agentId)){
			//����о���ʾ����ɾ��
			return 1;
		}
		else{
			//���û��   
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
