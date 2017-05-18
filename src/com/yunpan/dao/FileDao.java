package com.yunpan.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.yunpan.bean.Document;
import com.yunpan.db.DBAccess;

public class FileDao {
/**
 * 上传新文件，保存文件路径到数据库 并同时更新
 */
	@SuppressWarnings("null")
	public int uploadFile(Document doc){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		int id =0;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.insert("Document.insertFile",doc);
			id = doc.getId();
			sqlSession.commit();
			
		} catch (IOException e) {
			sqlSession.rollback();
			return 0;
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return id;
		
	}
	/**
	 * 根据id查找file
	 */
	public Document selectFile(int fileId){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		Document file =null;
		try {
			sqlSession = db.getSqlSession();
			file = sqlSession.selectOne("Document.selectFile",fileId);
			sqlSession.commit();
			
		} catch (IOException e) {
			return file;
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return file;
		
	}
	/**
	 * 根据指定的filePath查找文件
	 */
	public List<Document> listFile(String filePath){
		DBAccess db = new DBAccess();
		List<Document> list = new ArrayList<Document>();
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			System.out.println("fileDao:"+filePath);
			list = sqlSession.selectList("Document.listFile",filePath);
			sqlSession.commit();
			
		} catch (IOException e) {
			return 	list;
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;
	}
	/**
	 * 查找单个文件
	 */
	public Document selectOne(String fileName,String filePath){
		DBAccess db = new DBAccess();
		Document doc = new Document();
		doc.setFileName(fileName);
		doc.setFilePath(filePath);
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			doc = sqlSession.selectOne("Document.selectOne",doc);
			sqlSession.commit();
		} catch (Exception e) {
			return 	doc;
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return doc;
	}
}
