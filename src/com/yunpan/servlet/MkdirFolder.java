package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserFile;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;

@SuppressWarnings("serial")
public class MkdirFolder extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		// ��ȡ���ݿ�
		FileDao fileDao = new FileDao();
		UserFileDao userFileDao = new UserFileDao();
		UserDao userDao = new UserDao();
		HttpSession session = req.getSession();
		// ��ȡ�ļ���Ҫ����������·�����ļ��е�����
		String path = req.getParameter("path");
		System.out.println("shoudaode"+path);
		String folderName = req.getParameter("folderName");
		String username = (String) session.getAttribute("user");
		String filePath = null;
		String systemPath = null;
		int id = 0;
		if (path.equals("/")) {
			filePath = "/" + username;
			systemPath = "E:\\upload"  + filePath + "/" + folderName;
		} else {
			filePath = "/" + username + path;
			systemPath = "E:\\upload"  + filePath + "/" + folderName;
		}
		// �ļ�����
		Document doc = new Document();
		doc.setFileName(folderName);
		doc.setFileType("folder");
		doc.setKinds("folder");
		doc.setFilePath(filePath);
		System.out.println("filePath:  " + doc.getFilePath());
		// �û��ļ���ϵ����
		UserFile userFile = new UserFile();
		User user = new User();
		int fileId = 0;
		System.out.println(systemPath);
		File file = new File(systemPath);
	
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(systemPath + "Ŀ¼�����ڣ���Ҫ����");
			// ����Ŀ¼�����ʧ�ܷ��� 0
			try {
				file.mkdir();
				// Ȼ��������ݿ���룬���ʧ�ܣ�����0
				fileId = fileDao.uploadFile(doc);
				// .setFileId(fileId);
				if (fileId != 0) {
					user = userDao.queryUser(username);
					userFile.setUserId(user);
					userFile.setFileId(doc);
					id = userFileDao.insertUserFile(userFile);
					if (id != 0) {
						req.getRequestDispatcher("getallfiles").forward(req, resp);
					} else
						req.getRequestDispatcher("getallfiles").forward(req, resp);
				}

			} catch (Exception e) {
				e.printStackTrace();
				req.getRequestDispatcher("getallfiles").forward(req, resp);
			}

		} else {
			 PrintWriter out = resp.getWriter();
			 out.write("Ŀ¼�Ѵ���");
			 out.flush();
			 out.close();
		}
	
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
