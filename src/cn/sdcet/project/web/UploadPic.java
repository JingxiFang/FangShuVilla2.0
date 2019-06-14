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
		//����ҳ�治����
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		//�����ϴ�״̬��Ϣ
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
		//1.�ļ��ϴ�Ŀ¼����ʱ�ļ�Ŀ¼
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

		//2.�����ļ��ϴ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1*1024*1024);//�趨��ֵ������1M�Ľ���д�뵽��ʱ�ļ�
		factory.setRepository(tempFile);//������ʱĿ¼

		//3.�����ϴ��ļ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");//ָ����ȡʱʹ�õ��ַ���
		upload.setSizeMax(100 * 1024 * 1024);//ָ�������ϴ�����
		upload.setFileSizeMax(20*1024*1024);//�趨�����ļ�����ϴ�����

		//����ϴ����ȼ�����
		UploadStatus status = new UploadStatus();
		status.setCode(UploadStatus.STATUS_UPLOADING);
		HttpSession session = request.getSession();
		session.setAttribute("status", status);
		UploadListener listener = new UploadListener(status);
		upload.setProgressListener(listener);
		
		
		List<String> fileNameList =new ArrayList<String>();
		String fileName ="";
		//4.��������
		try {
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) { //��ͨ����
					String name = item.getFieldName(); //������
					String value = item.getString("UTF-8"); //��Ҫָ���ַ�����Ӧ�����ύʱһ��
					System.out.println("[��ͨ����] " + name + "=" + value);
				} else {
					//�ϴ��ļ�
					//String fileName = item.getName(); //�ϴ��ļ�������

					String fileContentType = item.getContentType(); //�ϴ��ļ�������
					String fileType=fileContentType.substring(fileContentType.indexOf("/")+1);
					fileName = UUID.randomUUID().toString()+"."+fileType;
					fileNameList.add(fileName);
					long fileSize = item.getSize(); //�ϴ��ļ��Ĵ�С
					System.out.println("[�ϴ��ļ�]" + fileName + ",������" + fileContentType + ",��СΪ" + fileSize + "��");

					//�����ļ�
					//TODO: 1. �����ļ����� �� 2.�ļ��Ĵ�ţ�����ͬһĿ¼�´�Ų�Ҫ����500���ļ�
					File target = new File(uploadFile, fileName);
					item.write(target);
					//�ϴ��ɹ�
					status.setCode(UploadStatus.STATUS_UPLOAD_SUCCESS);
					

				}
			}
			if("pic".equals(action)){
				//ɾ��ԭ�������Ե����ڸ÷��ݵ�ͼƬ
				seletePicPath(request,houseid);
				//ɾ�����ݿ��е����ڸ÷��ݵ�ͼƬ�ļ�¼
				delPicOfSql(houseid);
				for(String fName:fileNameList){
					//���ϴ��ɹ���ͼƬ��ַ�ϴ������ݿ�
					updatePicSql(houseid, fName);
				}
				request.getSession().removeAttribute("houseInfo");
				response.setStatus(HttpServletResponse.SC_OK);
				response.sendRedirect("ManageNew/checkallvilla.jsp");
			}
			else {
				//�޸����ݿ��û�ͷ����Ϣ
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
			throw new RuntimeException("�ϴ��ļ�ʧ�ܣ�" + e.getMessage());
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
		//��ȡ����ͼƬ�б�
		List<String> listPic=new ArrayList<String>();
		PictureService ps=new PictureService();
		listPic=ps.selectPictures(Integer.parseInt(houseId));
		//����ɾ������ͼƬ
		ServletContext sc = getServletContext();
		//File file;
		for(String path:listPic){
			delFile(sc.getRealPath(path));
		}
	}
	private  void delFile(String path) {
		
		File fileTemp = new File(path);
		// �ж��ļ��Ƿ����
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
			System.out.print("ʧ��");
			}
		
}
	
	    protected void uploadPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        //�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
	        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	        //�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
	        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	        File file = new File(tempPath);
	        if(!file.exists()&&!file.isDirectory()){
	            System.out.println("Ŀ¼���ļ������ڣ�");
	            file.mkdir();
	        }
	        //��Ϣ��ʾ
	        String message = "";
	        try {
	            //ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
	            //1������һ��DiskFileItemFactory����
	            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	            //���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
	            diskFileItemFactory.setSizeThreshold(1024*100);
	            //�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
	            diskFileItemFactory.setRepository(file);
	            //2������һ���ļ��ϴ�������
	            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
	            //����ϴ��ļ�������������
	            fileUpload.setHeaderEncoding("UTF-8");
	            //�����ļ��ϴ�����
	            fileUpload.setProgressListener(new ProgressListener(){
	                public void update(long pBytesRead, long pContentLength, int arg2) {
	                    System.out.println("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���" + pBytesRead);
	                }
	            });
	            //3���ж��ύ�����������Ƿ����ϴ���������
	            if(!fileUpload.isMultipartContent(request)){
	                //���մ�ͳ��ʽ��ȡ����
	                return;
	            }
	            //�����ϴ������ļ��Ĵ�С�����ֵ��Ŀǰ������Ϊ1024*1024�ֽڣ�Ҳ����1MB
	            fileUpload.setFileSizeMax(1024*1024);
	            //�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ10MB
	            fileUpload.setSizeMax(1024*1024*10);
	            //4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
	            List<FileItem> list = fileUpload.parseRequest(request);
	            for (FileItem item : list) {
	                //���fileitem�з�װ������ͨ�����������
	                if(item.isFormField()){
	                    String name = item.getFieldName();
	                    //�����ͨ����������ݵ�������������
	                    String value = item.getString("UTF-8");
	                    String value1 = new String(name.getBytes("iso8859-1"),"UTF-8");
	                    System.out.println(name+"  "+value);
	                    System.out.println(name+"  "+value1);
	                }else{
	                    //���fileitem�з�װ�����ϴ��ļ����õ��ϴ����ļ����ƣ�
	                    String fileName = item.getName();
	                    System.out.println(fileName);
	                    if(fileName==null||fileName.trim().equals("")){
	                        continue;
	                    }
	                    //ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
	                    //�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
	                    fileName = fileName.substring(fileName.lastIndexOf(File.separator)+1);
	                    //�õ��ϴ��ļ�����չ��
	                    String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
	                    if("zip".equals(fileExtName)||"rar".equals(fileExtName)||"tar".equals(fileExtName)||"jar".equals(fileExtName)){
	                        request.setAttribute("message", "�ϴ��ļ������Ͳ����ϣ�����");
	                        request.getRequestDispatcher("/message.jsp").forward(request, response);
	                        return;
	                    }
	                    //�����Ҫ�����ϴ����ļ����ͣ���ô����ͨ���ļ�����չ�����ж��ϴ����ļ������Ƿ�Ϸ�
	                    System.out.println("�ϴ��ļ�����չ��Ϊ:"+fileExtName);
	                    //��ȡitem�е��ϴ��ļ���������
	                    InputStream fis = item.getInputStream();
	                    //�õ��ļ����������
	                    fileName = mkFileName(fileName);
	                    //�õ��ļ������·��
	                    String savePathStr = mkFilePath(savePath, fileName);
	                    System.out.println("����·��Ϊ:"+savePathStr);
	                    //����һ���ļ������
	                    FileOutputStream fos = new FileOutputStream(savePathStr+File.separator+fileName);
	                    //��ȡ��ͨ��
	                    FileChannel readChannel = ((FileInputStream)fis).getChannel();
	                    //��ȡ��ͨ��
	                    FileChannel writeChannel = fos.getChannel();
	                    //����һ��������
	                    ByteBuffer buffer = ByteBuffer.allocate(1024);
	                    //�ж��������е������Ƿ��Ѿ�����ı�ʶ
	                    int length = 0;
	                    //ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
	                    while(true){
	                        buffer.clear();
	                        int len = readChannel.read(buffer);//��������
	                        if(len < 0){
	                            break;//��ȡ��� 
	                        }
	                        buffer.flip();
	                        writeChannel.write(buffer);//д������
	                    }
	                    //�ر�������
	                    fis.close();
	                    //�ر������
	                    fos.close();
	                    //ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
	                    item.delete();
	                    message = "�ļ��ϴ��ɹ�";
	                }
	            }
	        } catch (FileUploadBase.FileSizeLimitExceededException e) {
	            e.printStackTrace();
	            request.setAttribute("message", "�����ļ��������ֵ������");
	            request.getRequestDispatcher("/message.jsp").forward(request, response);
	            return;
	        }catch (FileUploadBase.SizeLimitExceededException e) {
	            e.printStackTrace();
	            request.setAttribute("message", "�ϴ��ļ����ܵĴ�С�������Ƶ����ֵ������");
	            request.getRequestDispatcher("/message.jsp").forward(request, response);
	            return;
	        }catch (FileUploadException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            message = "�ļ��ϴ�ʧ��";
	        }
	        request.setAttribute("message",message);
	        request.getRequestDispatcher("/message.jsp").forward(request, response);
	    }

	   
	    //�����ϴ��ļ����ļ������ļ����ԣ�uuid+"_"+�ļ���ԭʼ����
	    public String mkFileName(String fileName){
	        return UUID.randomUUID().toString()+"_"+fileName;
	    }
	    public String mkFilePath(String savePath,String fileName){
	        //�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
	        int hashcode = fileName.hashCode();
	        int dir1 = hashcode&0xf;
	        int dir2 = (hashcode&0xf0)>>4;
	        //�����µı���Ŀ¼
	        String dir = savePath + "\\" + dir1 + "\\" + dir2;
	        //File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
	        File file = new File(dir);
	        if(!file.exists()){
	            file.mkdirs();
	        }
	        return dir;
	    }
	

}

