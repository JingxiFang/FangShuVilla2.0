package cn.sdcet.project.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import cn.sdcet.project.service.PictureService;
public class deletePic extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deletePic() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

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

		response.setContentType("text/html");
	//	del("D:\\Java\\TomCat\\apache-tomcat-7.0.76\\webapps\\project\\upload\\userheader\\9de551ac-746c-409e-80d9-472aeff784ab.jpeg");
		
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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

		response.setContentType("text/html");
		String houseId=request.getParameter("houseid");
		//获取房屋图片列表
		List<String> listPic=new ArrayList<String>();
		PictureService ps=new PictureService();
		listPic=ps.selectPictures(Integer.parseInt(houseId));
		//挨个删除别墅图片
		ServletContext sc = getServletContext();
		//File file;
		for(String path:listPic){
			del(sc.getRealPath(path));
		}
		//String path=sc.getRealPath("upload/HousePic/")
		
		
		//del("D:\\Java\\TomCat\\apache-tomcat-7.0.76\\webapps\\project\\upload\\userheader\\9de551ac-746c-409e-80d9-472aeff784ab.jpeg");
	}

	
	
	
	
	
	
	
	
	
	
	private  void del(String path) {
		
			File fileTemp = new File(path);
			// 判断文件是否存在
			boolean falg = false;
			falg = fileTemp.exists();
			if(falg){
				File file = new File(path);
				if (true==file.isFile()) {
					boolean flag = false;
					flag = file.delete();
					if (flag) {
						System.out.print("成功");
					}
				}
			}
			else {
				System.out.print("失败");
				}
			
	}

	
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
