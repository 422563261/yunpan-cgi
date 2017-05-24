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
import com.yunpan.dao.UserRoomDao;
import com.yunpan.service.FileClassifyService;

/**
 * 
 * @author lon �ϴ��ļ�
 *
 */
@SuppressWarnings("serial")
public class UploadFile extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html; charset=utf-8");
		UserRoomDao userRoomDao= new UserRoomDao(); 
		FileDao fileDao = new FileDao();
		UserDao userDao = new UserDao();
		UserFileDao userFileDao = new UserFileDao();
		FileClassifyService fileCS = new FileClassifyService();
		JSONObject json = new JSONObject();
		PrintWriter output = resp.getWriter();
		HttpSession session = req.getSession();
		// ��ȡ�û������ļ�·���������ĸ��ļ����£�

		String path = null;

		String username = (String) session.getAttribute("user");

		User user = userDao.queryUser(username);
		Document doc = new Document();
		UserFile userFile = new UserFile();
		int fileId = 0;
		String fileType = null;
		String kinds = null;
		String fileName = null;
		String filePath = null;
		String systemPath = null;
		//�ϴ��ļ���С
		float fileSize;
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
					// �����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					path = value;
					System.out.println("�õ���path:::" + path);
					if (path.equals("/")) {
						systemPath = "E:\\upload\\" + username;
						filePath="/"+username;

					} else {
						systemPath = "E:\\upload\\"  + username + path;
						filePath = "/" + username + path;
					}

				} else {// ���fileitem�з�װ�����ϴ��ļ�
					// �õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					fileSize = item.getSize()/1048576;
					System.out.println("�ϴ���С��"+fileSize+"Mb");
					//����û�ʣ��ռ�
					float room = userRoomDao.selectRoom(String.valueOf(user.getId()));
					if(room<fileSize){
						break;
					}
					// �õ��ļ������� ������ ��
					fileName = filename.substring(0, filename.lastIndexOf("."));
					fileType = filename.substring(filename.lastIndexOf(".") + 1);
					kinds = fileCS.fileclassify(fileType);

					// �ɹ��ϴ��� �����ļ���Ϣ�����ݿ�
					doc.setFileName(fileName);
					doc.setFileType(fileType);
					doc.setFilePath(filePath);
					doc.setKinds(kinds);
					doc.setSize(fileSize);
					// �ж��Ƿ���ڴ��ļ��������ͬһ��Ŀ¼�´���һ�����ļ�������и��ǣ����ǲ��������ݿ��޸�
					String ff = systemPath + "/" + fileName + "." + fileType;
					System.out.println(ff);
					File f = new File(ff);
					if (!f.exists()) {
						fileId = fileDao.uploadFile(doc);
						Document document = fileDao.selectFile(String.valueOf(fileId));
						userFile.setFileId(document);
						userFile.setUserId(user);
						if (fileId != 0){
							fileId = userFileDao.insertUserFile(userFile);
							userRoomDao.updateRoom(user, room-fileSize);
						}
						else
							break;
					}

					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺
					// c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					// �����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("/") + 1);
					// ��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					// ����һ���ļ������
					System.out.println("·��������::::"+systemPath);
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
}
