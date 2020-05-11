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

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Component("fileconfig")
public class FileConfig {

	public List<Map<String, Object>> fileInfo(ProductRegister productregister, MultipartHttpServletRequest mtreq,HttpServletRequest req){

		Iterator<String> iterator = mtreq.getFileNames();//files
		MultipartFile mpFile = null; 
		String file_name = null; 
		String fileextensionOrigin = null;
		String file_name_real = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;
		
		String filepath = req.getSession().getServletContext().getRealPath("/");
		
		
		File file = new File(filepath);
		log.info("저장경로: " + filepath);
	
		if(file.exists() == false) { 
			file.mkdirs();	
		}
					
		while (iterator.hasNext()) {
			mpFile = mtreq.getFile(iterator.next());
			if(mpFile.isEmpty() == false) {
				file_name = mpFile.getOriginalFilename();
				fileextensionOrigin = file_name.substring(file_name.lastIndexOf('.'));
				UUID uuid = UUID.randomUUID(); 
				file_name_real = uuid+fileextensionOrigin;
				
				file = new File(filepath + file_name_real);
				
				try {
					mpFile.transferTo(file); 
					listMap = new HashMap<String, Object>();
					listMap.put("product_name", productregister.getProduct_name());
					listMap.put("product_category", productregister.getProduct_category());
					listMap.put("product_price", productregister.getProduct_price());
					listMap.put("file_name", file_name);
					listMap.put("file_name_real",file_name_real);
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
