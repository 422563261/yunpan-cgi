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
	public Document selectFile(String fileId){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		Document file =null;
		try {
			sqlSession = db.getSqlSession();
			file = sqlSession.selectOne("Document.selectFile",fileId);
			sqlSession.commit();
			
		} catch (IOException e) {
			e.printStackTrace();
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
	/**
	 * 文件重命名,
	 */
	public int fileRename(String newFileName,String id){
		DBAccess db = new DBAccess();
		Document doc = new Document();
		doc.setFileName(newFileName);
		doc.setId(Integer.parseInt(id));
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("Document.fileRename",doc);
			sqlSession.commit();
		} catch (Exception e) {
			return 	0;
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return 1;
		
	}
	/**
	 * 文件夹重命名,模糊修改 
	 */
	
	public int folderRename(String oldFolderpath,String newFolderpath){
		DBAccess db = new DBAccess();
		Document doc = new Document();
		doc.setFileName(oldFolderpath);
		doc.setFilePath(newFolderpath);
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.update("Document.modifyPath",doc);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return 1;
	}
	/**
	 * 删除单个文件或文件夹
	 */
	public int deleteOne(String fileId){
		
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.delete("Document.deleteFile",fileId);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return 1;
	}
	/**
	 * 删除某个文件夹下所有文件
	 */
	public int deleteFolder(String filePath){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		try {
			sqlSession = db.getSqlSession();
			sqlSession.delete("Document.deleteFolder",filePath);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return 1;
	}
	/**
	 * 获取某个文件夹下所有文件大小
	 */
	public float countSize(String filePath){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		float size =0;
		try {
			sqlSession = db.getSqlSession();
			size = sqlSession.selectOne("Document.countSize", filePath);
			sqlSession.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return size;
	}
	/**
	 * 模糊查找
	 */
	public List<Document> likeQuery(Integer id,String fileName){
		DBAccess db = new DBAccess();
		SqlSession sqlSession=null;
		Document doc = new Document();
		doc.setId(id);
		doc.setFileName(fileName);
		List<Document> list = new ArrayList<Document>();
		try {
			sqlSession = db.getSqlSession();
			list = sqlSession.selectList("Document.likeQuery",doc);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return list;
		
	}
}
