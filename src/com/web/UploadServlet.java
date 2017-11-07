package com.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if("listFile".equals(action)){
			listFile(request,response,session);
		}
		if("upload".equals(action)){
			upload(request,response,session);
		}
		if("load".equals(action)){
			down(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void down(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//get����
		//��ȡ�ϴ�·��
		String realPath = this.getServletContext().getRealPath("/upload");
		//��ȡ�����ļ���
		String fileName = request.getParameter("fileName");
		//����������,���ϴ�·����ȡ�ļ�
		FileInputStream is = new FileInputStream(new File(realPath,fileName));
		fileName = fileName.substring(fileName.lastIndexOf("#")+1);
        //���fileNameΪ����,����url����
		fileName = URLEncoder.encode(fileName, "UTF-8");
		//����������Ӧͷ
		response.setHeader("content-disposition", "attachment;fileName=" + fileName);
		//���������,����������
		OutputStream out = response.getOutputStream();
		//��������
		byte[] bytes = new byte[1024*3];
		int length = -1;
		while((length = is.read(bytes))!=-1){
			out.write(bytes, 0, length);
		}
		is.close();
		out.close();
		response.getWriter().print("<script>alert('�������');location.href='bbsdemo/jsp/listfile.jsp'</script>");
	}
	
	private void listFile(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		//����map�����ļ���
		Map<String,String> mapFile = new HashMap<String,String>();
		//��ȡ�ϴ��ļ�·��
		String path = request.getSession().getServletContext().getRealPath("/upload");
		//��ȡ·���µ������ļ���
		File file = new File(path);
		String[] fileNames = file.list();
		
		//�����������map��
		for(int i = 0; i<fileNames.length; i++){
			//�ļ�������
			String longName = fileNames[i];
			//�ļ����
			String shortName = longName.substring(longName.lastIndexOf("#")+1);
			//����map��
			mapFile.put(longName, shortName);
		}
		//����session��
		session.setAttribute("mapFile", mapFile);
		response.sendRedirect("/bbsdemo/jsp/listfile.jsp");
	}

	private void upload(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		// TODO Auto-generated method stub
		//������������
		FileItemFactory factory  = new DiskFileItemFactory();
		//�������������
		ServletFileUpload sfu = new ServletFileUpload(factory);
		//***�������ı���
		sfu.setHeaderEncoding("utf-8");
		
		//�ж��Ƿ����ϴ���
		if(sfu.isMultipartContent(request)){
			try {
				//��ȡFileItem�б�
				List<FileItem> items = sfu.parseRequest(request);
				//�����б�
				for(FileItem f:items){
					//�ж��Ƿ�����ͨ��Ԫ��
					if(f.isFormField()){
						//��ȡnameֵ
						String fileName = f.getFieldName();
						//��ȡvalueֵ
						String value = f.getString("UTF-8");
					}else{
						//�ļ�Ԫ��
						//��ȡ�ļ���
						String fileName = f.getName();
						//�����ļ�����������
						//��ȡΨһ���uuid
						String id = UUID.randomUUID().toString();
						//��д�ļ���
						fileName = id+"#"+fileName;
						//��ȡ�ϴ�·��
						String parentPath = this.getServletContext().getRealPath("/upload");
						//�����ļ�����
						File file = new File(parentPath,fileName);
						//�ϴ�
						f.write(file);
						//������ʱ�ļ�
						f.delete();
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session.setAttribute("msg", "�ϴ����");
		response.sendRedirect("/bbsdemo/jsp/upload.jsp");
	}
}
