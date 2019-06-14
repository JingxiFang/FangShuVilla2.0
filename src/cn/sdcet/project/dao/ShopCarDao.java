package cn.sdcet.project.dao;

import java.util.List;
import java.util.Map;

import cn.sdcet.project.domain.*;

public interface ShopCarDao {
	
	/**
	 * ɾ�����ﳵ��ָ����Ϣ\���㹺�ﳵ
	 * @param userid �û���(��½�˺�)
	 * @param houseid
	 * @return �Ƿ�ɾ���ɹ�
	 */
	public boolean delShopCar(int userid,int houseid);
	
	
	public boolean delShopCar(int houseid);
	/**
	 * ��ӵ����ﳵ
	 * @param houseid ����id
	 * @param userid �û���(��½�˺�)
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addShopCar(int houseid,int userid);
	
	
	/**
	 * ��ȡ���ﳵ��Ϣ
	 * @param userid �û���(��½�˺�)
	 * @return ���ﳵ��Ϣ
	 */
	public List<House> getShopCar(int userid,int status);
	
	
	public boolean isOrder(int userid,int houseid);
	
	public boolean endOrder(int userid,int houseid);
	
	/***
	 * /**
	 * ��ȡ�����˵�ǰδ������Ѿ�������Ķ���
	 * @param agentid  �û����
	 * @param status   ״̬ 1�����Ѿ���ɣ�0����δ���
	 * @return
	 */
	public List<Map<String,Object>> getHouseInfoOfOrdered(int agentid,int status);
}
