package com.yunpan.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserFileDao;
/**
 * 
 * @author lonɾ���ļ�
 *
 */
public class DeleteFile extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		//��ȡ���ݿ�
		FileDao fileDao = new FileDao();
		UserFileDao userFileDao = new UserFileDao();
		//��ȡҪɾ�����ļ������֣�·��
		
	}@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
