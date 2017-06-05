package com.yunpan.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// JSONObject json = new JSONObject();
		// PrintWriter out = resp.getWriter();

		// ��ȡ���ݿ����
		FileDao fileDao = new FileDao();
		// ��ȡ���ص��ļ�id
		String id = req.getParameter("id");
		Document doc = new Document();
		String userAgent = req.getHeader("User-Agent");
		try {
			// ��ȡ���ݿ��е��ļ���չ�����ļ�·��
			doc = fileDao.selectFile(id);
			String fileType = doc.getFileType();
			String filePath = doc.getFilePath();
			String fileName = doc.getFileName();
			// ��ȡ/upload/ϵͳ·��
			String ss = fileName + "." + fileType;
			String systemPath = "E:\\upload" + filePath + "/" + ss;
			File file = new File(systemPath);
			// �ж��ļ��Ƿ����,���������أ��������򷵻�
			//String f = new String(ss.getBytes("UTF-8"), "UTF-8");
			InputStream in = new FileInputStream(file);
			OutputStream output = resp.getOutputStream();
			//
			byte[] bytes = userAgent.contains("MSIE") ? ss.getBytes() : fileName.getBytes("UTF-8");
			ss = new String(bytes, "ISO-8859-1");
			fileName = new String(bytes, "ISO-8859-1");
			if (file.exists()) {
				// �����ļ�MIME����
				resp.setContentType("multipart/form-data");
				// ����Content-Disposition
				resp.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",fileName));

				byte[] b = new byte[1024];
				while (in.read(b) != -1) {
					output.write(b);
				}

			}
			in.close();
			output.close();

		} catch (Exception e) {

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
}
