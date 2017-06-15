package com.yunpan.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.Report;
import com.yunpan.db.DBAccess;

public class ReportDao {
	/**
	 * �����ٱ���¼
	 */
	public void insertReport(Report report){
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession  = db.getSqlSession();
			sqlSession.insert("Report.insertReport",report);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	/**
	 * ����ٱ���¼
	 */
	public void updateReport(Report report){
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession  = db.getSqlSession();
			sqlSession.update("Report.updateReport",report);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	/**
	 * �鿴δ����ľٱ���¼
	 */
	public List<Report> selectReport(String reportStatus){
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		List<Report> list = new ArrayList<Report>();
		try {
			sqlSession  = db.getSqlSession();
			list = sqlSession.selectList("Report.selectReport",reportStatus);
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
	/**
	 * �鿴���оٱ���¼ 
	 */
	public List<Report> selectAllReport(){
		DBAccess db = new DBAccess();
		SqlSession sqlSession = null;
		List<Report> list = new ArrayList<Report>();
		try {
			sqlSession  = db.getSqlSession();
			list = sqlSession.selectList("Report.selectAllReport");
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
