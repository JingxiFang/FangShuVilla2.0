package cn.sdcet.project.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.sdcet.project.service.AgentInfoService;
import cn.sdcet.project.service.HouseInfoService;

public class DelAgentInfo extends HttpServlet {

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
		response.setCharacterEncoding("utf-8");
		int ids=Integer.parseInt( request.getParameter("agentId"));
		AgentInfoService agentInfoService=new AgentInfoService();
		if(agentInfoService.deleteAgentInfo(ids)==1){
			response.getWriter().append("还存在该代理人代理的房屋，请修改再继续");
			
		}
		else if(agentInfoService.deleteAgentInfo(ids)==2){
			response.getWriter().append("成功");
		}
		else{
			response.getWriter().append("失败");
		}
	}

}