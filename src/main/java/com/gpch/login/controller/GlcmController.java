package com.gpch.login.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gpch.login.model.Ekstraksi;
import com.gpch.login.service.EkstraksiFiturService;
import com.gpch.login.service.EkstraksiService;


@RequestMapping("glcm")
@Controller
public class GlcmController {
	
	
	@Autowired 
	private EkstraksiService ekstraksiService;
	
	@GetMapping("")
	public String index() {
		return "glcm/list";
	}

	
	@RequestMapping(value = "/ekstraksi",method=RequestMethod.POST)
	public String ekstraksi(Ekstraksi ekstraksi,@RequestParam("file") MultipartFile[] files,RedirectAttributes ra) {
		EkstraksiFiturService glcmfe;
		ModelAndView mv = new ModelAndView();
		try {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				
				Ekstraksi dt =new Ekstraksi();
					byte[] bytes = file.getBytes();

					// Creating the directory to store file
					String rootDirectory=System.getProperty("user.dir");
					
					String resourceDirectory=rootDirectory+"/src/main/resources/static";
					String folderName = "Ekstraksi/";
					File dir = new File(resourceDirectory  + "/upload/"+folderName);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					File serverFile = new File(dir.getAbsolutePath()+"/"+files[i].getOriginalFilename());
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					
				
					dt.setFileName(folderName+"/"+files[i].getOriginalFilename());
					dt.setIsActive(true);
					dt.setIsDeleted(false);
					
					ekstraksiService.save(dt);
			}
			
			String rootDirectory=System.getProperty("user.dir");
			String resourceDirectory=rootDirectory+"/src/main/resources/static";
			File dir = new File(resourceDirectory  + "/upload");
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			List<Ekstraksi> getList = ekstraksiService.getAll();
			
			
			for (Ekstraksi eks : getList) {
				glcmfe = new EkstraksiFiturService(new File(dir.getAbsolutePath()+"/"+eks.getFileName()), 50);
				glcmfe.extract();
				Ekstraksi dt = ekstraksiService.findByFileName(eks.getFileName());
				dt.setContrast(glcmfe.getContrast());
				dt.setDissimilarity(glcmfe.getDissimilarity());
				dt.setHomogenity(glcmfe.getHomogenity());
				dt.setEntropy(glcmfe.getEntropy());
				dt.setEnergy(glcmfe.getEnergy());
				dt.setUpdateAt(new Date());
				dt.setUpdatedBy("System");
				ekstraksiService.save(dt);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(GlcmController.class.getName()).log(Level.SEVERE, null, e);
		}
		return "redirect:/glcm";
	}
	
}
