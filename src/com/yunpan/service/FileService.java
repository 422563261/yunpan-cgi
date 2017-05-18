package com.yunpan.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yunpan.bean.Document;
import com.yunpan.bean.UserFile;
/**
 * 
 * @author lon
 *				����һ��Ŀ¼��ɸѡ�����Ŀ¼�µ������ļ�
 */
public class FileService {
public List<Document> dealFile(List<UserFile> userFileList,String path){
	List<Document> doclist = new ArrayList<Document>();
	Iterator<UserFile> it = userFileList.iterator();
	Document doc = new Document();
	while(it.hasNext()){
		
		if(doc.getFilePath().equals(path)){
			doclist.add(doc);
		}
	}
	return doclist; 
}
}
