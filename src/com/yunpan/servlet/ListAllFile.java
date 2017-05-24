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
import com.yunpan.bean.UserFile;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;
import com.yunpan.service.FileService;

/**
 * 
 * @author lon ���������ṩ��path���Ҹ��û������ļ����ļ������ļ�·�����ļ�����
 */
@SuppressWarnings("serial")
public class ListAllFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		JSONObject json = new JSONObject();
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("user");
		PrintWriter out = resp.getWriter();
		// req ��ȡ��������
		String fPath = req.getParameter("path");
		FileDao fileDao = new FileDao();
		String path = null;
		if (fPath.equals("/")) {
			path = "/" + username;
		} else
			path = "/" + username + fPath;
		try {
			// ��ȡ���û����Ŀ¼�µ��ļ�
			List<Document> list = fileDao.listFile(path);
			// ����path
			json.put("data", list);

		} catch (Exception e) {
			json.put("status", 0);
		}
		out.write(json.toString());
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
