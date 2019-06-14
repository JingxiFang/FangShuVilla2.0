package cn.sdcet.project.service;

import java.util.List;

import cn.sdcet.project.dao.PictureDao;
import cn.sdcet.project.dao.jdbc.PictureDaoJDBCImpl;
import cn.sdcet.project.domain.Picture;

public class PictureService {

	/**
	 * ���Ҷ���ͼ
	 * @param houseId
	 * @return 
	 */
	public List<String> selectPictures(int houseId) {
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.selectPictures(houseId);
	}
	/**
	 * �ϴ�ͼƬ
	 * @param picture
	 * @return
	 */
	public boolean setPicture(Picture picture){
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.setPicture(picture);
	}
	
	/**
	 * ���ҵ�һ��ͼƬ
	 * @param houseId
	 * @return
	 */
	public String selectPictureFirst(int houseId) {
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.selectPictureFirst(houseId);
	}
	
	/***
	 * ɾ��ĳ�����ݵ�����ͼƬ
	 * @param houseId
	 * @return
	 */
	public boolean deletePic(int houseId){
		PictureDao picDao=new PictureDaoJDBCImpl();
		return picDao.delPicture(houseId);
	}
}
