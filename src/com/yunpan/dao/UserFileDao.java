package com.yunpan.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.db.DBAccess;
import com.yunpan.bean.Document;
import com.yunpan.bean.UserFile;

public class UserFileDao {
	/**
	 * 
	 * ���������ṩ��userid���Ҹ��û������ļ����ļ������ļ�·�����ļ�����
	 */
	public List<UserFile> searchAllFile(String userId) {
		DBAccess db = new DBAccess();
		List<UserFile> list = new ArrayList<UserFile>();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			list = sqlSession.selectList("UserFile.selectUserFile", userId);
			sqlSession.commit();
		} catch (IOException e) {
			return list;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;

	}

	/**
	 * �����û��ļ���ϵ
	 */
	public int insertUserFile(UserFile userFile) {
		int id = 0;
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("UserFile.insertUserFile", userFile);
			id = userFile.getId();
			sqlSession.commit();
		} catch (IOException e) {

			return 0;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return id;
	}
	/**
	 * �����ѯ
	 */
	public List<Document> listByKinds(UserFile userFile){
		DBAccess db = new DBAccess();
		List<Document> list = new ArrayList<Document>();
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			list = sqlSession.selectList("UserFile.selectFileByKinds",userFile);
			sqlSession.commit();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;
		
	}
}
