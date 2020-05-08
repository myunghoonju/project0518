package com.ddc2.project0518.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ddc2.project0518.model.ProductRegister;

@Component("fileconfig")
public class FileConfig {

	public List<Map<String, Object>> fileInfo(ProductRegister productregister, MultipartHttpServletRequest mtpreq,HttpServletRequest req){
	
		Iterator<String> iterator = mtpreq.getFileNames();
		
		MultipartFile mpFile = null; 
		String filenameOrigin = null; 
		String fileextensionOrigin = null;
		String savedfilename = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String filepath = req.getSession().getServletContext().getRealPath("/");
		File file = new File(filepath); 
		if(file.exists() == false) { 
			file.mkdirs(); 
		}
		
		while (iterator.hasNext()) {
			mpFile = mtpreq.getFile(iterator.next());
			if(mpFile.isEmpty() == false) {
				filenameOrigin = mpFile.getOriginalFilename();
				fileextensionOrigin = filenameOrigin.substring(filenameOrigin.lastIndexOf('.'));
				UUID uuid = UUID.randomUUID(); 
				savedfilename = uuid+fileextensionOrigin;
				
				file = new File(filepath + savedfilename);
				
				//여기 틀렸음 고쳐야함
				ry {
					mpFile.transferTo(file); 
					listMap = new HashMap<String, Object>();
					listMap.put("product_name", productregister.getProduct_name());
					listMap.put("product_price", productregister.getProduct_price());
					listMap.put("file_name", filenameOrigin);
					listMap.put("file_name_real",savedfilename);
					listMap.put("file_size",mpFile.getSize());
					list.add(listMap);
				}catch (Exception e) {
					e.printStackTrace();
					
				}	
				
			}//if
			
		}//while
		
	return list;
		
	}//fileInfo
	
}
