package com.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Data;
import com.app.entity.User;
import com.app.repo.DataRepository;
import com.app.service.IService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DataController {

	@Autowired
	IService iservice;
	
	@PostMapping("/upload")
	public String uploadData(@RequestParam("cImage") MultipartFile file1, @RequestParam("pImage") MultipartFile file2, @RequestParam("result") String result) throws IOException {
		iservice.uploadImage(file1, file2, result);
		return "Successfully Uploaded:"+"\n cImage: "+file1.getOriginalFilename()+"\n pImage: "+file2.getOriginalFilename()+"\n result: "+result ;
	}
	
	@GetMapping("/download/{id}")
	public List<Object> downloadData(@PathVariable int id) throws IOException{
		List<Object> list = iservice.displayById(id);
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(list);
		return list;
	}
	
	@GetMapping("/downloadall")
	public List<ArrayList<Object>> downloadAll() throws IOException{
		List<ArrayList<Object>> list = iservice.displayAll();
//		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(list);
		return list;
	}
	
	@GetMapping("/downloadbyrange/{id1}-{id2}")
	public List<ArrayList<Object>> displayByIdRange(@PathVariable int id1,@PathVariable int id2) throws IOException{
		List<ArrayList<Object>> list = iservice.displayByIdRange(id1, id2);
		return list;
	}
	
	@GetMapping("/filterbyresult/{result}")
	public List<ArrayList<Object>> filterDataByResult(@PathVariable String result) throws IOException {
		List<ArrayList<Object>> data = iservice.filterDataByResult(result);
		return data;
	}
	
	@GetMapping("/filterbydate/{start}_{end}")
	public List<ArrayList<Object>> filteDataByDate(@PathVariable String start,@PathVariable String end) throws IOException{ 
		List<ArrayList<Object>> data = iservice.filterDataByDate(start, end);
		return data;
	}

	// DashBoard API's
	
	@GetMapping("/totalcount")
	public int totalCount() {
		int count = iservice.totalCount();
		return count;
	}
	
	@GetMapping("/totalpasscount")
	public int passCount() {
	int count = iservice.passCount();
	return count;
	}
		
	@GetMapping("/totalfailcount")
	public int failCount() {
	int count = iservice.failCount();
	return count;
	}
	
	@GetMapping("/totalcountbydate/{startDate}_{endDate}")
	public int totalCountByDate(@PathVariable String startDate,@PathVariable String endDate) {
		int count = iservice.totalCountByDate(startDate, endDate);
		return count;
	}
	
	@GetMapping("/totalpasscountbydate/{startDate}_{endDate}")
	public int passCountByDate(@PathVariable String startDate,@PathVariable String endDate) {
	int count = iservice.passCountByDate(startDate, endDate);
	return count;
	}
		
	@GetMapping("/totalfailcountbydate/{startDate}_{endDate}")
	public int failCountByDate(@PathVariable String startDate,@PathVariable String endDate) {
	int count = iservice.failCountByDate(startDate, endDate);
	return count;
	}
	
	// notification
	@GetMapping("/notification_count")
	public int getNotificationCount() {
		int count = iservice.getNotificationCount();
		return count;
	}
	
	@GetMapping("/notification")
	public ArrayList<String> getNotification() {	
	return iservice.getNotification();	
	}
	
}
