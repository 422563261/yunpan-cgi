package com.yunpan.dao;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.User;
import com.yunpan.bean.UserRoom;
import com.yunpan.db.DBAccess;

public class UserRoomDao {
	/**
	 * �����û�
	 */
	public int insertRoom(String userId) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("UserRoom.insertUserSize", userId);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * �����û��������û��ռ��С
	 */
	public float selectRoom(String userId) {
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		float room = 0;
		try {
			sqlSession = db.getSqlSession();
			room = sqlSession.selectOne("UserRoom.selectUserSize", userId);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return room;
	}

	/**
	 * �����û��ռ��С
	 * 
	 */
	public String updateRoom(User user, float size) {
		DBAccess db = new DBAccess();
		UserRoom userRoom = new UserRoom();
		userRoom.setUser(user);
		userRoom.setSize(size);
		SqlSession sqlSession = null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("UserRoom.updateUserSize", userRoom);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
