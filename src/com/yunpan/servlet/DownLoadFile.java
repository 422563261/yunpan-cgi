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
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		PrintWriter out = resp.getWriter();
		HttpSession session = req.getSession();
		// ��ȡ���ݿ����
		FileDao fileDao = new FileDao();
		// ��ȡ��Ҫ���ص��ļ�����
		String fileName = req.getParameter("fileName");
		// ��ȡ�û�������
		String username = (String) session.getAttribute("user");
		// ��ȡ���ص��ļ�·��
		String path = req.getParameter("filePath");
		// ���ݿ������ļ���·��
		String filePath = "\\" + username + "\\" + path;
		try {
			//��ȡ���ݿ��е��ļ���չ��
//			String fileType = fileDao.listFile(filePath)
			// ��ȡ/upload/ϵͳ·��
			String systemPath = req.getServletContext().getRealPath("/upload/") + filePath + "\\"+fileName;
			File file = new File(systemPath);
			//�ж��ļ��Ƿ����,���������أ��������򷵻�
			if(file.exists()){
				InputStream in = new FileInputStream(file);
				OutputStream output = resp.getOutputStream();
				int b;
				while((b=in.read())!=-1){
					output.write(b);
				}
				in.close();
				output.close();
			}else
			json.put("data", "�ļ�������");
			
		} catch (Exception e) {
			json.put("data", "�ļ�������");
		}finally {
			out.write(json.toString());
			out.close();
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
