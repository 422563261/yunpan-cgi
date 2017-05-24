package com.yunpan.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserRoom;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserRoomDao;
import com.yunpan.service.FileService;

/**
 * 
 * @author lonɾ���ļ�
 *
 */
public class DeleteFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter out = resp.getWriter();
		JSONObject json = new JSONObject();
		HttpSession session = req.getSession();
		// ��ȡ���ݿ�
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		UserRoomDao userRoomDao = new UserRoomDao();
		FileService fileService = new FileService();
		// ��ȡҪɾ�����ļ�id
		String id = req.getParameter("id");
		String username = (String) session.getAttribute("user");
		String systemPath = null;
		try {
			Document doc = fileDao.selectFile(id);
			User user = userDao.queryUser(username);
			String fileName = doc.getFileName();
			String filePath = doc.getFilePath();
			String fileType = doc.getFileType();
			float fileSize = 0;
			float userSize = 0;
			// �ж��Ƿ�ɾ���ļ���
			systemPath = "E:\\upload"  + filePath;
			if (fileType.equals("folder")) {
				File folder = new File(systemPath + "/" + fileName);
				System.out.println(folder.getPath());
				if (folder.exists()) {
					File[] fs = folder.listFiles();
					if(fs!=null&&fs.length>0){
						fileService.deleteFile(folder);
						filePath = filePath + "/" + fileName;
						// ��ȡɾ���ļ��Ĵ�С
						fileSize = fileDao.countSize(filePath);
						// ��ȡ�û�ʣ��ռ��С
						userSize = userRoomDao.selectRoom(String.valueOf(user.getId()));
						// ��ɾ�ļ���
						fileDao.deleteOne(id);
						// ��ɾ������ļ����������ļ�
						
						fileDao.deleteFolder(filePath);
						userRoomDao.updateRoom(user, userSize+fileSize);
						json.put("status", 1);
					}else{
						fileService.deleteFile(folder);
						fileDao.deleteOne(id);
						json.put("status", 1);
					}
				} else
					json.put("status", 0);
			} else {
				// ���д��̲���
				File file = new File(systemPath + "/" + fileName + "." + fileType);
				if (file.exists()) {
					file.delete();
					// �������ݿ�
					Document document = fileDao.selectFile(id);
					// ��ȡɾ���ļ��Ĵ�С
					fileSize = document.getSize();
					// ��ȡ�û�ʣ��ռ��С
					userSize = userRoomDao.selectRoom(String.valueOf(user.getId()));
					fileDao.deleteOne(id);
					userRoomDao.updateRoom(user, userSize + fileSize);
					json.put("status", 1);
				} else
					json.put("status", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write(json.toString());
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}
