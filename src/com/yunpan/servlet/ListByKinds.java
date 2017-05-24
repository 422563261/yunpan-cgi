package com.yunpan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserFile;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;

/**
 * 
 * @author lon�����ѯ
 *
 */
public class ListByKinds extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		PrintWriter out = resp.getWriter();
		//��ȡ���ݿ�
		UserFileDao userFileDao = new UserFileDao();
		UserDao userDao = new UserDao();
		//��ȡkinds
		String kinds = req.getParameter("kinds");
		try {
			//�õ�user����
			User user = userDao.queryUser(username);
			//��ȡfile����
			Document doc = new Document();
			doc.setKinds(kinds);
			//��װ
			UserFile userFile = new UserFile();
			userFile.setUserId(user);
			userFile.setFileId(doc);
			//���ݿ��ѯ
			List<Document> list = userFileDao.listByKinds(userFile);
			json.put("data", list);
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
