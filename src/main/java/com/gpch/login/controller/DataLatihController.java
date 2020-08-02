package com.gpch.login.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gpch.login.model.DataLatih;
import com.gpch.login.service.DataLatihService;
import com.gpch.login.service.EkstraksiFiturService;

@RequestMapping("datalatih")
@Controller
public class DataLatihController {
	private static final Logger logger = LoggerFactory
			.getLogger(DataLatihController.class);

	@Autowired
	private DataLatihService dataLatihService;
	
	 @GetMapping(value = "/{disease}/{pageNumber}")
	    public String list(@PathVariable String disease,@PathVariable Integer pageNumber, Model model) {
	        Page<DataLatih> page = dataLatihService.getList(disease, pageNumber);

	        int current = page.getNumber() + 1;
	        int begin = Math.max(1, current - 5);
	        int end = Math.min(begin + 10, page.getTotalPages());

	        model.addAttribute("list", page);
	        model.addAttribute("beginIndex", begin);
	        model.addAttribute("endIndex", end);
	        model.addAttribute("currentIndex", current);

	        return "dataLatih/list";

	    }

	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("datalatih", new DataLatih());
		mv.setViewName("dataLatih/form");
		return mv;
	}
	
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("datalatih", dataLatihService.findById(id));
		mv.setViewName("dataLatih/form");
		return mv;
	}
		
	@RequestMapping(value = "/merge",method = RequestMethod.POST)
	public String merge(DataLatih dataLatih,@RequestParam("file") MultipartFile[] files,RedirectAttributes ra) {

		String message = "";
		try {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			
			DataLatih dt =new DataLatih();
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootDirectory=System.getProperty("user.dir");
				
				String resourceDirectory=rootDirectory+"/src/main/resources/static";
				String folderName = "dataLatih/"+dataLatih.getNamaPenyakit().replaceAll(" ", "");
				File dir = new File(resourceDirectory  + "/upload/"+folderName);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()+"/"+files[i].getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				logger.info("Server File Location="
						+ serverFile.getAbsolutePath());
			
				dt.setFileName(folderName+"/"+files[i].getOriginalFilename());
				dt.setGejala(dataLatih.getGejala());
				dt.setNamaPenyakit(dataLatih.getNamaPenyakit());
				dt.setIsActive(true);
				dt.setIsDeleted(false);
				
				dataLatihService.save(dt);

			
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/datalatih/1";
	}
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String delete(@PathVariable long id,RedirectAttributes ra) {
		dataLatihService.delete(id);
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/datalatih/1";
	}
	
	@RequestMapping(value = "/deleteAll",method = RequestMethod.GET)
	public String delete(RedirectAttributes ra) {
		dataLatihService.deleteAll();
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/datalatih/1";
	}
	
	@RequestMapping(value = "/ekstraksi",method=RequestMethod.GET)
	public String ekstraksi() {
		EkstraksiFiturService glcmfe;
		ModelAndView mv = new ModelAndView();
		try {
			String rootDirectory=System.getProperty("user.dir");
			String resourceDirectory=rootDirectory+"/src/main/resources/static";
			File dir = new File(resourceDirectory  + "/upload");
			if (!dir.exists())
				dir.mkdirs();

			// Create the file on server
			List<DataLatih> getList = dataLatihService.getAll();
			
			
			for (DataLatih dataLatih : getList) {
				DataLatih dt = dataLatihService.findByFileName(dataLatih.getFileName());
				
				if(dt.getContrast().equals(null) || dt.getHomogenity().equals(null) || dt.getEnergy().equals(null)) {
					glcmfe = new EkstraksiFiturService(new File(dir.getAbsolutePath()+"/"+dataLatih.getFileName()), 50);
					glcmfe.extract();
					dt.setContrast(glcmfe.getContrast());
					dt.setDissimilarity(glcmfe.getDissimilarity());
					dt.setHomogenity(glcmfe.getHomogenity());
					dt.setEntropy(glcmfe.getEntropy());
					dt.setEnergy(glcmfe.getEnergy());
					dt.setUpdateAt(new Date());
					dt.setUpdatedBy("System");
					dataLatihService.save(dt);	
				}
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/datalatih/1";
	}

	

}
