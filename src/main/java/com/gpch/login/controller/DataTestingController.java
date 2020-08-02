package com.gpch.login.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import com.gpch.login.model.DataTesting;
import com.gpch.login.service.DataLatihService;
import com.gpch.login.service.DataTestingService;
import com.gpch.login.service.EkstraksiFiturService;


@RequestMapping("datatesting")
@Controller
public class DataTestingController {
	private static final Logger logger = LoggerFactory
			.getLogger(DataTestingController.class);

	@Autowired
	private DataTestingService dataTestingService;
	
	 @GetMapping(value = "/{disease}/{pageNumber}")
	    public String list(@PathVariable String disease,@PathVariable Integer pageNumber, Model model) {
	        Page<DataTesting> page = dataTestingService.getList(disease, pageNumber);

	        int current = page.getNumber() + 1;
	        int begin = Math.max(1, current - 5);
	        int end = Math.min(begin + 10, page.getTotalPages());

	        model.addAttribute("list", page);
	        model.addAttribute("beginIndex", begin);
	        model.addAttribute("endIndex", end);
	        model.addAttribute("currentIndex", current);

	        return "dataTesting/list";

	    }

	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("datatesting", new DataLatih());
		mv.setViewName("dataTesting/form");
		return mv;
	}
	
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("datatesting", dataTestingService.findById(id));
		mv.setViewName("dataTesting/form");
		return mv;
	}
		
	@RequestMapping(value = "/merge",method = RequestMethod.POST)
	public String merge(DataTesting dataTesting,@RequestParam("file") MultipartFile[] files,RedirectAttributes ra) {

		String message = "";
		try {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			
			DataTesting dt =new DataTesting();
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootDirectory=System.getProperty("user.dir");
				
				String resourceDirectory=rootDirectory+"/src/main/resources/static";
				String folderName = "dataTesting/"+dataTesting.getNamaPenyakit().replaceAll(" ", "");
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
				dt.setGejala(dataTesting.getGejala());
				dt.setNamaPenyakit(dataTesting.getNamaPenyakit());
				dt.setIsActive(true);
				dt.setIsDeleted(false);
				
				dataTestingService.save(dt);

			
		}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/datatesting/1";
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
			List<DataTesting> getList = dataTestingService.getAll();
			
			
			for (DataTesting dataTesting : getList) {
				glcmfe = new EkstraksiFiturService(new File(dir.getAbsolutePath()+"/"+dataTesting.getFileName()), 50);
				glcmfe.extract();
				DataTesting dt = dataTestingService.findByFileName(dataTesting.getFileName());
				dt.setContrast(glcmfe.getContrast());
				dt.setDissimilarity(glcmfe.getDissimilarity());
				dt.setHomogenity(glcmfe.getHomogenity());
				dt.setEntropy(glcmfe.getEntropy());
				dt.setEnergy(glcmfe.getEnergy());
				dt.setUpdateAt(new Date());
				dt.setUpdatedBy("System");
				dataTestingService.save(dt);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/datatesting/1";
	}

	
	
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String delete(@PathVariable long id,RedirectAttributes ra) {
		dataTestingService.delete(id);
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/datatesting/1";
	}
	@RequestMapping(value = "/deleteAll",method = RequestMethod.GET)
	public String delete(RedirectAttributes ra) {
		dataTestingService.deleteAll();
		ra.addFlashAttribute("successFlash","Success delete data..");
		return "redirect:/datatesting/1";
	}
	

}
