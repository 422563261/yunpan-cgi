package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.dao.FileDao;


/**
 * 
 * �ļ�������
 *
 */
@SuppressWarnings("serial")
public class FileRename extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		// ��ȡ���ݿ�
		FileDao fileDao = new FileDao();
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		// ��ȡҪ���������ļ�Ψһid
		String newFileName = req.getParameter("newFileName");
		String id = req.getParameter("id");
		String filePath = null;
		String fileType = null;
		String systemPath = null;
		String oldFileName = null;
		try {
			Document doc = fileDao.selectFile(id);
			fileType = doc.getFileType();
			filePath = doc.getFilePath();
			oldFileName = doc.getFileName();
			// �ļ�����Ŀ¼
			systemPath = req.getServletContext().getRealPath("/upload") + filePath+"/";
			File oldFile = new File(systemPath + oldFileName + "." + fileType);
			File newFile = new File(systemPath + newFileName + "." + fileType);
			if (oldFile.exists()) {
				if (!newFile.exists()) {
					oldFile.renameTo(newFile);
					fileDao.fileRename(newFileName, id);
					json.put("status", 1);
				} else
					json.put("status", 0);
			} else
				json.put("status", 0);
		} catch (Exception e) {
				e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
