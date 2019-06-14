package cn.sdcet.project.service;

import java.util.List;

import cn.sdcet.project.dao.PictureDao;
import cn.sdcet.project.dao.jdbc.PictureDaoJDBCImpl;
import cn.sdcet.project.domain.Picture;

public class PictureService {

	/**
	 * 查找多张图
	 * @param houseId
	 * @return 
	 */
	public List<String> selectPictures(int houseId) {
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.selectPictures(houseId);
	}
	/**
	 * 上传图片
	 * @param picture
	 * @return
	 */
	public boolean setPicture(Picture picture){
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.setPicture(picture);
	}
	
	/**
	 * 查找第一张图片
	 * @param houseId
	 * @return
	 */
	public String selectPictureFirst(int houseId) {
		PictureDaoJDBCImpl picDao=new PictureDaoJDBCImpl();
		return picDao.selectPictureFirst(houseId);
	}
	
	/***
	 * 删除某个房屋的所有图片
	 * @param houseId
	 * @return
	 */
	public boolean deletePic(int houseId){
		PictureDao picDao=new PictureDaoJDBCImpl();
		return picDao.delPicture(houseId);
	}
}
