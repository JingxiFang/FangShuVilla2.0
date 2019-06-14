package cn.sdcet.project.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.sdcet.project.domain.Users;
import cn.sdcet.project.service.AgentInfoService;
import cn.sdcet.project.service.UsersService;

public class AddAgent extends HttpServlet {

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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String action=request.getParameter("action");
		if("getThumb".equals(action)){
			String agentid=request.getParameter("agentid");
			String userid=request.getSession().getAttribute("userid").toString();
			AgentInfoService as=new AgentInfoService();
			if(as.addThumbCount(Integer.parseInt(agentid),Integer.parseInt( userid))){
				response.getWriter().append("���޳ɹ�");
			}
			else{
				response.getWriter().append("����ʧ��");
			}
		}else
		{
			//String agname=request.getParameter("agname");
			String phonenum=request.getParameter("phonenum");
			
			Users user=new Users();
			user.setLoginid(phonenum);
			
			AgentInfoService agentinfo=new AgentInfoService();
			String registinfo=agentinfo.addagentinfo(user);
			if("���û��������볢����������".equals(registinfo)){
				response.getWriter().append("���û��������볢����������");
				//response.sendRedirect("Manager/addagent.jsp?info=1");
			}else if("��ӳɹ�".equals(registinfo)){
				response.getWriter().append("��ӳɹ�");
				//response.sendRedirect("Manager/addagent.jsp?info=2");
			}else if("���ʧ��".equals(registinfo)){
				response.getWriter().append("���ʧ��");
				//response.sendRedirect("Manager/addagent.jsp?info=1");
				
			}else{
				System.out.println(registinfo);
			}
			}
		}
		
		
	

}
