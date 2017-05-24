package com.yunpan.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.dao.FileDao;

/**
 * 
 * �ļ�����
 *
 */
@SuppressWarnings("serial")
public class DownLoadFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
//		JSONObject json = new JSONObject();
//		PrintWriter out = resp.getWriter();
	
		// ��ȡ���ݿ����
		FileDao fileDao = new FileDao();
		// ��ȡ���ص��ļ�id
		String id = req.getParameter("id");
		Document doc = new Document();
		try {
			//��ȡ���ݿ��е��ļ���չ�����ļ�·��
			doc = fileDao.selectFile(id);
			String fileType = doc.getFileType();
			String filePath = doc.getFilePath();
			String fileName  = doc.getFileName();
			// ��ȡ/upload/ϵͳ·��
			String systemPath = "E:\\upload"   +filePath+"/"+fileName+"."+fileType;
			File file = new File(systemPath);
			//�ж��ļ��Ƿ����,���������أ��������򷵻�
			String f = fileName+"."+fileType;
			
			if(file.exists()){
				//�����ļ�MIME����
				resp.setContentType(getServletContext().getMimeType(f));
				//����Content-Disposition 
				resp.setHeader("Content-Disposition", "attachment;filename="+f);
				InputStream in = new FileInputStream(file);
				OutputStream output = resp.getOutputStream();
				byte []b = new byte[1024];
				while(in.read(b)!=-1){
					output.write(b);
				}
				in.close();
				output.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
