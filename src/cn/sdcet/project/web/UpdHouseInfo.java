package cn.sdcet.project.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sdcet.project.domain.House;
import cn.sdcet.project.domain.HouseDetailInfo;
import cn.sdcet.project.domain.UserInfo;
import cn.sdcet.project.domain.Users;
import cn.sdcet.project.service.HouseDetailInfoService;
import cn.sdcet.project.service.HouseInfoService;

public class UpdHouseInfo extends HttpServlet {

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

		doPost(request,response);
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

		request.setCharacterEncoding("utf-8");
		 response.setContentType("text/html; charset=utf-8");
		String action=request.getParameter("action");
		
		PrintWriter pw=response.getWriter();
		if("setfine".equals(action)){
			int houseid=Integer.parseInt(request.getParameter("houseid"));
			HouseInfoService houseservice=new HouseInfoService();
			pw.append(houseservice.setFineHouse(houseid));
				
			
		}
		else {
			HouseDetailInfo houseinfo=new HouseDetailInfo();
			
			int houseid=Integer.parseInt( request.getParameter("hiddenId"));
			String housename=request.getParameter("housename");
			String address=request.getParameter("address");
			String addressdet=request.getParameter("addressdet");
			String pri=request.getParameter("price");
			double price=Double.parseDouble(pri);
			String housetype=request.getParameter("housetype");
			String are=request.getParameter("area");
			int area=Integer.parseInt(are);
			String fp=request.getParameter("firstpay");
			double firstpay=Double.parseDouble(fp);
			String agid=request.getParameter("agentid");
			int agentid=Integer.parseInt(agid);
			//String uploadimg=request.getParameter("uploadimg");
			String time=request.getParameter("time");
			String resfac=request.getParameter("resfac");
			String decdeg=request.getParameter("decdeg");
			String serviceintro=request.getParameter("serviceintro");
			String corepoint=request.getParameter("corepoint");
			String describe=request.getParameter("describe");
			
			if(housename!=null&&addressdet!=null&&pri!=null&&are!=null){
				House house=new House();
				house.setId(houseid);
				house.setName(housename);
				//house.setOnePicAdd(uploadimg);
				house.setPrice(price);
				house.setHousetype(housetype);
				house.setArea(area);
				house.setH_address(address);
				
				houseinfo.setH_addressdel(addressdet);
				houseinfo.setFristpay(firstpay);
				
				//agentid
				Users users=new Users();
				users.setId(agentid);
				UserInfo userinfo=new UserInfo();
				userinfo.setUser(users);
				houseinfo.setAgent(userinfo);
				
				houseinfo.setTime(time);
				//houseid
				
				houseinfo.setHouse(house);
				houseinfo.setCorepoint(corepoint);
				houseinfo.setDescribe(describe);
				houseinfo.setResfac(resfac);
				houseinfo.setServiceintro(serviceintro);
				houseinfo.setDecdeg(decdeg);
			}
			HouseDetailInfoService houseDetailService=new HouseDetailInfoService();
			houseDetailService.updHouseInfo(houseinfo);
			response.sendRedirect("ManageNew/updatevilla.jsp?info=1&houseid="+houseid);
			
		}
	}

}
