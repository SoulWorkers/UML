package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.textbookDao;
import entity.textbook;

public class textbookService {
	private textbookDao textdao=new textbookDao();
	
	public List<textbook> getAll(){
		try {
			return textdao.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public textbook queryByid(int id){
		return textdao.queryById(id);
	}
	
	public int del(int id){
		try {
			return textdao.del(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean addBook(textbook book){
		return textdao.add(book);
	}
	
	public boolean updateBook(textbook book){
		return textdao.updateBook(book);
	}
	public textbook doupLoad(HttpServletRequest request) {
		//���ϴ���ͼƬ���浽imagesĿ¼�У�����request�е����������װ��Book��
		textbook book = new textbook();
		String savePath = request.getSession().getServletContext().getRealPath("/resource");
		try {
            File file = new File(savePath);
            //�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
            if (!file.exists() && !file.isDirectory()) {
                System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ����");
                //����Ŀ¼
                file.mkdir();
            }
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					BeanUtils.setProperty(book, name, value);
				}else{
					String filename = item.getName();
					String savefilename = makeFileName(filename);//�õ�������Ӳ�̵��ļ���
					InputStream in = item.getInputStream();
					FileOutputStream out = new FileOutputStream(savePath + "\\" + savefilename);
					int len = 0;
					byte buffer[] = new byte[1024];
					while((len = in.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					in.close();
					out.close();
					item.delete();
					book.setDownloadlink(savefilename);
				}
			}
			return book;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String makeFileName(String filename){
		String ext = filename.substring(filename.lastIndexOf(".") + 1);//lastIndexOf("\\.")����д����
		return UUID.randomUUID().toString() + "." + ext;
	}
}
