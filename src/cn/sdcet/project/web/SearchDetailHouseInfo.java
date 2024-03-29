package cn.sdcet.project.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sdcet.project.domain.HouseDetailInfo;
import cn.sdcet.project.service.HouseDetailInfoService;
import cn.sdcet.project.service.PictureService;

public class SearchDetailHouseInfo extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HouseDetailInfo houseDet=new HouseDetailInfo();
		//HouseDetailInfo houseDetc=new HouseDetailInfo();
		PictureService picService = new PictureService();
		List<String> listPic = new ArrayList<String>();
		String ids=request.getParameter("houseid");
		
    	int id=0;
    	if(!"".equals(ids))
    	{
    		id=Integer.parseInt(ids);
    	
    		HouseDetailInfoService userinfoService=new HouseDetailInfoService();
    		houseDet=userinfoService.getHouseInfoDetail(id);
    		request.getSession().setAttribute("houseInfo", houseDet);
    		//request.setAttribute("houseInfo", houseDet);	
    		listPic = picService.selectPictures(id);
    		//request.setAttribute("listPic", listPic);
    		request.getSession().setAttribute("listPic", listPic);
    	}
    	
    	//RequestDispatcher dispatcher=request.getRequestDispatcher("/ManageNew/updatevilla.jsp");
		//dispatcher.forward(request, response);
    	response.sendRedirect("ManageNew/updatevilla.jsp");
	}

}
