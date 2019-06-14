package cn.sdcet.project.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.sdcet.project.domain.House;
import cn.sdcet.project.domain.HouseDetailInfo;
import cn.sdcet.project.domain.Picture;
import cn.sdcet.project.service.PictureService;
import cn.sdcet.project.service.UserInfoService;

public class UploadPic extends HttpServlet {
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
		//设置页面不缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		//返回上传状态信息
		String message = "";
		HttpSession session = request.getSession();
		UploadStatus status = (UploadStatus) session.getAttribute("status");
		if(status != null) {
			message = status.getCode() + ";" + status.getDescript() + ";" + status.getProgress();		
		}

		response.setContentType("text/plain;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(message);
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
		//1.文件上传目录和临时文件目录
		File uploadFile;
		ServletContext sc = getServletContext();
		String houseid="";
		
		
		String action=request.getParameter("action");
		if("pic".equals(action)){
			Object houseidObj=request.getSession().getAttribute("houseid");
			//String houseId=request.getParameter("houseid");
			if(houseidObj!=null){
				 houseid=houseidObj.toString();
			}
			else if(request.getSession().getAttribute("houseInfo")!=null){
				
				HouseDetailInfo house=(HouseDetailInfo)request.getSession().getAttribute("houseInfo");
				houseid=String.valueOf(house.getHouse().getId());
			}
			else{
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}
			uploadFile = new File(sc.getRealPath("upload/HousePic/")+"/"+houseid);
		}
		else{
			uploadFile = new File(sc.getRealPath("upload/userheader"));
		}

		if(!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File tempFile = new File(sc.getRealPath("temp"));
		if(!tempFile.exists()) {
			tempFile.mkdirs();
		}

		//2.创建文件上传工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1*1024*1024);//设定阈值，超出1M的将会写入到临时文件
		factory.setRepository(tempFile);//设置临时目录

		//3.创建上传文件处理器
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");//指定读取时使用的字符集
		upload.setSizeMax(100 * 1024 * 1024);//指定最大的上传限制
		upload.setFileSizeMax(20*1024*1024);//设定单个文件最大上传限制

		//添加上传进度监听器
		UploadStatus status = new UploadStatus();
		status.setCode(UploadStatus.STATUS_UPLOADING);
		HttpSession session = request.getSession();
		session.setAttribute("status", status);
		UploadListener listener = new UploadListener(status);
		upload.setProgressListener(listener);
		
		
		List<String> fileNameList =new ArrayList<String>();
		String fileName ="";
		//4.处理请求
		try {
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) { //普通表单项
					String name = item.getFieldName(); //参数名
					String value = item.getString("UTF-8"); //需要指定字符集，应当于提交时一致
					System.out.println("[普通表单项] " + name + "=" + value);
				} else {
					//上传文件
					//String fileName = item.getName(); //上传文件的名称

					String fileContentType = item.getContentType(); //上传文件的类型
					String fileType=fileContentType.substring(fileContentType.indexOf("/")+1);
					fileName = UUID.randomUUID().toString()+"."+fileType;
					fileNameList.add(fileName);
					long fileSize = item.getSize(); //上传文件的大小
					System.out.println("[上传文件]" + fileName + ",类型是" + fileContentType + ",大小为" + fileSize + "。");

					//保存文件
					//TODO: 1. 考虑文件重名 ； 2.文件的存放，建议同一目录下存放不要超过500个文件
					File target = new File(uploadFile, fileName);
					item.write(target);
					//上传成功
					status.setCode(UploadStatus.STATUS_UPLOAD_SUCCESS);
					

				}
			}
			if("pic".equals(action)){
				//删掉原来的所以的属于该房屋的图片
				seletePicPath(request,houseid);
				//删掉数据库中的属于该房屋的图片的记录
				delPicOfSql(houseid);
				for(String fName:fileNameList){
					//将上传成功的图片地址上传到数据库
					updatePicSql(houseid, fName);
				}
				request.getSession().removeAttribute("houseInfo");
				response.setStatus(HttpServletResponse.SC_OK);
				response.sendRedirect("ManageNew/checkallvilla.jsp");
			}
			else {
				//修改数据库用户头像信息
				UserInfoService userinfoService=new UserInfoService();
				int userid=Integer.valueOf( session.getAttribute("userid").toString());
				if(userinfoService.updHead("upload/userheader/"+fileName, userid)){
					response.sendRedirect("DetailInfo/about.jsp");
				}else{
					response.sendRedirect("DetailInfo/about.jsp?msg=0");
				}
			}
		} catch (Exception e) {
			status.setCode(UploadStatus.STATUS_UPLOAD_ERROR);
			status.addMessage(e.getMessage());
			e.printStackTrace();
			throw new RuntimeException("上传文件失败：" + e.getMessage());
		}

	}

	private boolean delPicOfSql(String houseid){
		PictureService ps=new PictureService();
		return ps.deletePic(Integer.parseInt( houseid));
	
	}
	
	private void updatePicSql(String houseid, String fileName) {
		PictureService picService=new PictureService();
		Picture pic=new Picture();
		pic.setPicaddress("upload/HousePic/"+houseid+"/"+fileName);
		House house=new House();
		house.setId(Integer.parseInt(houseid));
		pic.setHouseId(house);
		picService.setPicture(pic);
	}
	
	private void seletePicPath(HttpServletRequest request,String houseId){
		//String houseId=request.getParameter("houseid");
		//获取房屋图片列表
		List<String> listPic=new ArrayList<String>();
		PictureService ps=new PictureService();
		listPic=ps.selectPictures(Integer.parseInt(houseId));
		//挨个删除别墅图片
		ServletContext sc = getServletContext();
		//File file;
		for(String path:listPic){
			delFile(sc.getRealPath(path));
		}
	}
	private  void delFile(String path) {
		
		File fileTemp = new File(path);
		// 判断文件是否存在
		boolean falg = false;
		falg = fileTemp.exists();
		if(falg){
			File file = new File(path);
			if (true==file.isFile()) {
				boolean flag = false;
				flag = file.delete();
				
			}
		}
		else {
			System.out.print("失败");
			}
		
}
	
	    protected void uploadPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
	        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	        //上传时生成的临时文件保存目录
	        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	        File file = new File(tempPath);
	        if(!file.exists()&&!file.isDirectory()){
	            System.out.println("目录或文件不存在！");
	            file.mkdir();
	        }
	        //消息提示
	        String message = "";
	        try {
	            //使用Apache文件上传组件处理文件上传步骤：
	            //1、创建一个DiskFileItemFactory工厂
	            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
	            diskFileItemFactory.setSizeThreshold(1024*100);
	            //设置上传时生成的临时文件的保存目录
	            diskFileItemFactory.setRepository(file);
	            //2、创建一个文件上传解析器
	            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
	            //解决上传文件名的中文乱码
	            fileUpload.setHeaderEncoding("UTF-8");
	            //监听文件上传进度
	            fileUpload.setProgressListener(new ProgressListener(){
	                public void update(long pBytesRead, long pContentLength, int arg2) {
	                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
	                }
	            });
	            //3、判断提交上来的数据是否是上传表单的数据
	            if(!fileUpload.isMultipartContent(request)){
	                //按照传统方式获取数据
	                return;
	            }
	            //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
	            fileUpload.setFileSizeMax(1024*1024);
	            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
	            fileUpload.setSizeMax(1024*1024*10);
	            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	            List<FileItem> list = fileUpload.parseRequest(request);
	            for (FileItem item : list) {
	                //如果fileitem中封装的是普通输入项的数据
	                if(item.isFormField()){
	                    String name = item.getFieldName();
	                    //解决普通输入项的数据的中文乱码问题
	                    String value = item.getString("UTF-8");
	                    String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
	                    System.out.println(name+"  "+value);
	                    System.out.println(name+"  "+value1);
	                }else{
	                    //如果fileitem中封装的是上传文件，得到上传的文件名称，
	                    String fileName = item.getName();
	                    System.out.println(fileName);
	                    if(fileName==null||fileName.trim().equals("")){
	                        continue;
	                    }
	                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
	                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
	                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
	                    //得到上传文件的扩展名
	                    String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
	                    if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
	                        request.setAttribute("message", "上传文件的类型不符合！！！");
	                        request.getRequestDispatcher("/message.jsp").forward(request, response);
	                        return;
	                    }
	                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
	                    System.out.println("上传文件的扩展名为:"+fileExtName);
	                    //获取item中的上传文件的输入流
	                    InputStream fis = item.getInputStream();
	                    //得到文件保存的名称
	                    fileName = mkFileName(fileName);
	                    //得到文件保存的路径
	                    String savePathStr = mkFilePath(savePath, fileName);
	                    System.out.println("保存路径为:"+savePathStr);
	                    //创建一个文件输出流
	                    FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
	                    //获取读通道
	                    FileChannel readChannel = ((FileInputStream)fis).getChannel();
	                    //获取读通道
	                    FileChannel writeChannel = fos.getChannel();
	                    //创建一个缓冲区
	                    ByteBuffer buffer = ByteBuffer.allocate(1024);
	                    //判断输入流中的数据是否已经读完的标识
	                    int length = 0;
	                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	                    while(true){
	                        buffer.clear();
	                        int len = readChannel.read(buffer);//读入数据
	                        if(len < 0){
	                            break;//读取完毕 
	                        }
	                        buffer.flip();
	                        writeChannel.write(buffer);//写入数据
	                    }
	                    //关闭输入流
	                    fis.close();
	                    //关闭输出流
	                    fos.close();
	                    //删除处理文件上传时生成的临时文件
	                    item.delete();
	                    message = "文件上传成功";
	                }
	            }
	        } catch (FileUploadBase.FileSizeLimitExceededException e) {
	            e.printStackTrace();
	            request.setAttribute("message", "单个文件超出最大值！！！");
	            request.getRequestDispatcher("/message.jsp").forward(request, response);
	            return;
	        }catch (FileUploadBase.SizeLimitExceededException e) {
	            e.printStackTrace();
	            request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
	            request.getRequestDispatcher("/message.jsp").forward(request, response);
	            return;
	        }catch (FileUploadException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            message = "文件上传失败";
	        }
	        request.setAttribute("message",message);
	        request.getRequestDispatcher("/message.jsp").forward(request, response);
	    }

	   
	    //生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	    public String mkFileName(String fileName){
	        return UUID.randomUUID().toString()+"_"+fileName;
	    }
	    public String mkFilePath(String savePath,String fileName){
	        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
	        int hashcode = fileName.hashCode();
	        int dir1 = hashcode&0xf;
	        int dir2 = (hashcode&0xf0)>>4;
	        //构造新的保存目录
	        String dir = savePath + "\\" + dir1 + "\\" + dir2;
	        //File既可以代表文件也可以代表目录
	        File file = new File(dir);
	        if(!file.exists()){
	            file.mkdirs();
	        }
	        return dir;
	    }
	

}

