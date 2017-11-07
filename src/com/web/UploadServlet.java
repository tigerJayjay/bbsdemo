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
		//get请求
		//获取上传路径
		String realPath = this.getServletContext().getRealPath("/upload");
		//获取下载文件名
		String fileName = request.getParameter("fileName");
		//创建输入流,从上传路径获取文件
		FileInputStream is = new FileInputStream(new File(realPath,fileName));
		fileName = fileName.substring(fileName.lastIndexOf("#")+1);
        //如果fileName为中文,进行url编码
		fileName = URLEncoder.encode(fileName, "UTF-8");
		//设置下载响应头
		response.setHeader("content-disposition", "attachment;fileName=" + fileName);
		//创建输出流,向浏览器输出
		OutputStream out = response.getOutputStream();
		//创建缓冲
		byte[] bytes = new byte[1024*3];
		int length = -1;
		while((length = is.read(bytes))!=-1){
			out.write(bytes, 0, length);
		}
		is.close();
		out.close();
		response.getWriter().print("<script>alert('下载完成');location.href='bbsdemo/jsp/listfile.jsp'</script>");
	}
	
	private void listFile(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		//创建map保存文件名
		Map<String,String> mapFile = new HashMap<String,String>();
		//获取上传文件路径
		String path = request.getSession().getServletContext().getRealPath("/upload");
		//获取路径下的所有文件名
		File file = new File(path);
		String[] fileNames = file.list();
		
		//遍历数组放入map中
		for(int i = 0; i<fileNames.length; i++){
			//文件完整名
			String longName = fileNames[i];
			//文件简称
			String shortName = longName.substring(longName.lastIndexOf("#")+1);
			//放入map中
			mapFile.put(longName, shortName);
		}
		//放入session中
		session.setAttribute("mapFile", mapFile);
		response.sendRedirect("/bbsdemo/jsp/listfile.jsp");
	}

	private void upload(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		// TODO Auto-generated method stub
		//创建工厂对象
		FileItemFactory factory  = new DiskFileItemFactory();
		//创建核心类对象
		ServletFileUpload sfu = new ServletFileUpload(factory);
		//***设置中文编码
		sfu.setHeaderEncoding("utf-8");
		
		//判断是否是上传表单
		if(sfu.isMultipartContent(request)){
			try {
				//获取FileItem列表
				List<FileItem> items = sfu.parseRequest(request);
				//遍历列表
				for(FileItem f:items){
					//判断是否是普通表单元素
					if(f.isFormField()){
						//获取name值
						String fileName = f.getFieldName();
						//获取value值
						String value = f.getString("UTF-8");
					}else{
						//文件元素
						//获取文件名
						String fileName = f.getName();
						//处理文件名重名问题
						//获取唯一标记uuid
						String id = UUID.randomUUID().toString();
						//重写文件名
						fileName = id+"#"+fileName;
						//获取上传路径
						String parentPath = this.getServletContext().getRealPath("/upload");
						//创建文件对象
						File file = new File(parentPath,fileName);
						//上传
						f.write(file);
						//清理临时文件
						f.delete();
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		session.setAttribute("msg", "上传完成");
		response.sendRedirect("/bbsdemo/jsp/upload.jsp");
	}
}
