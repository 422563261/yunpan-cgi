package com.yunpan.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject;
import com.yunpan.bean.Document;
import com.yunpan.bean.User;
import com.yunpan.bean.UserFile;
import com.yunpan.dao.FileDao;
import com.yunpan.dao.UserDao;
import com.yunpan.dao.UserFileDao;
import com.yunpan.service.FileClassifyService;

/**
 * 
 * @author lon �ϴ��ļ�
 *
 */
@SuppressWarnings("serial")
public class UploadFile extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		UserFileDao userFileDao = new UserFileDao();
		FileClassifyService fileCS = new FileClassifyService();
		JSONObject json = new JSONObject();
		PrintWriter output = resp.getWriter();
		HttpSession session = req.getSession();
		// ��ȡ�û������ļ�·���������ĸ��ļ����£�

		String path = null;
		path = req.getParameter("path");
		String username = (String) session.getAttribute("user");
		// ��������

		// �õ��ϴ��ļ��е�·��
		// /upload/tom
		User user = userDao.queryUser(username);
		Document doc = new Document();
		UserFile userFile = new UserFile();
		int fileId = 0;
		String fileType = null;
		String kinds = null;
		String fileName = null;
		//String filePath = "/" + username + "/" + path;
		String filePath = "/" + username;
		System.out.println(filePath);
		String systemPath = null;
		System.out.println(path);
		if (path == null || path.equals("/")) {
			System.out.println("11111");
			systemPath = req.getServletContext().getRealPath("/upload/") + username;
		} else{
			System.out.println("2222");
			filePath = "/" + username + "/" + path;
			systemPath = req.getServletContext().getRealPath("/upload/") + username + "/" + path;
			
		}
		System.out.println(systemPath);
		File file = new File(systemPath);
		// �ж��ϴ��ļ����Ƿ����
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(systemPath + "Ŀ¼�����ڣ���Ҫ����");
			file.mkdir();
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// ����һ���ļ��ϴ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("utf-8");

		if (!ServletFileUpload.isMultipartContent(req)) {
			System.out.println("isMultipartContent");
			return;
		}
		try {
			List<FileItem> list = upload.parseRequest(req);
			for (FileItem item : list) {
				// ���fileitem�з�װ������ͨ�����������
				if (item.isFormField()) {
					String name = item.getFieldName();
					// �����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				} else {// ���fileitem�з�װ�����ϴ��ļ�
					// �õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					// �õ��ļ������� ������ ��
					System.out.println("91:" + filename.length());
					System.out.println("92:" + filename.substring(filename.lastIndexOf(".")));
					fileName = filename.substring(0, filename.lastIndexOf("."));
					fileType = filename.substring(filename.lastIndexOf(".") + 1);
					kinds = fileCS.fileclassify(fileType);
					System.out.println(fileName + "    " + fileType);
					// �ɹ��ϴ��� �����ļ���Ϣ�����ݿ�
					doc.setFileName(fileName);
					doc.setFileType(fileType);
					doc.setFilePath(filePath);
					doc.setKinds(kinds);
					// �ж��Ƿ���ڴ��ļ��������ͬһ��Ŀ¼�´���һ�����ļ�������и��ǣ����ǲ��������ݿ��޸�
					String ff = systemPath + "/" + fileName + "." + fileType;
					System.out.println(ff);
					File f = new File(ff);
					if (!f.exists()) {
						fileId = fileDao.uploadFile(doc);
						Document document = fileDao.selectFile(String.valueOf(fileId));
						userFile.setFileId(document);
						userFile.setUserId(user);
						if (fileId != 0)
							fileId = userFileDao.insertUserFile(userFile);
						else
							break;
					}

					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺
					// c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					// ������ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("/") + 1);
					// ��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					// ����һ���ļ������
					FileOutputStream out = new FileOutputStream(systemPath + "/" + filename);
					// ����һ��������
					byte buffer[] = new byte[1024];
					// �ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while ((len = in.read(buffer)) > 0) {
						// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\"
						// + filename)����
						out.write(buffer, 0, len);
					}
					// �ر�������
					in.close();
					// �ر������
					out.close();
					// ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();

				}
			}

		} catch (FileUploadException e) {
			e.printStackTrace();
			json.put("status", 0);

		} finally {
			if (fileId != 0) {
				json.put("status", 1);
			} else
				json.put("status", 0);
			output.write(json.toString());
			output.close();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}