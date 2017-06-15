package com.yunpan.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.User;
import com.yunpan.bean.UserShare;
import com.yunpan.db.DBAccess;

public class UserShareDao {
	/**
	 * 
	 * @param userShare�����û������¼
	 */
	public void insertShare(UserShare userShare) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("UserShare.insertShare", userShare);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * ɾ���û������¼
	 */
	public void deleteShare(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.delete("UserShare.deleteShare", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * �鿴�û����еķ���
	 */
	public List<UserShare> selectAllShare(User user) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		List<UserShare> list = new ArrayList<UserShare>();
		try {
			sqlSession = db.getSqlSession();
			list = sqlSession.selectList("UserShare.selectAllShare", user);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;
	}

	/**
	 * ����url���ҷ���������ļ�
	 */
	public UserShare selectShare(String url) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		UserShare userShare = new UserShare();
		try {
			sqlSession = db.getSqlSession();
			userShare = sqlSession.selectOne("UserShare.selectShare", url);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userShare;
	}
	/**
	 * ����id���ҷ���������ļ�
	 */
	public UserShare selectShareById(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		UserShare userShare = new UserShare();
		try {
			sqlSession = db.getSqlSession();
			userShare = sqlSession.selectOne("UserShare.selectShareById", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return userShare;
	}
	/**
	 * ���ش�����1
	 */
	public void updateDLtimes(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("UserShare.updateDLtimes", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**
	 * 
	 * ���������1
	 */
	public void updateStimes(String id) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("UserShare.updateStimes", id);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
