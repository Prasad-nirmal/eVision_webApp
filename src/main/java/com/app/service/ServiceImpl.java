package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Data;
import com.app.entity.User;
import com.app.exception.ResourceNotFoundException;
import com.app.repo.DataRepository;
import com.app.repo.PasswordResetTokenRepo;
import com.app.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ServiceImpl implements IService {

	@Autowired
	DataRepository datarepo;
	@Autowired
	UserRepository userrepo;
	@Autowired
	PasswordResetTokenRepo passwordrepo;
	@Autowired
	JavaMailSender javamailsender;

	private final String PATH = "C:\\meg-nxt\\evision_image_data\\";

	@Override
	public Data uploadImage(MultipartFile cImageFile, MultipartFile pImageFile, @RequestParam("result") String result)
			throws IOException {

		String fileName = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss-SSSS ").format(new Date());
		String fullPathC = PATH + fileName + cImageFile.getOriginalFilename();
		String fullPathP = PATH + fileName + pImageFile.getOriginalFilename();
		Data d = new Data();
		d.setcImage(fullPathC);
		d.setpImage(fullPathP);
		d.setResult(result);
		cImageFile.transferTo(new File(fullPathC));
		pImageFile.transferTo(new File(fullPathP));
		return datarepo.save(d);
	}

	@Override
	public List<Object> displayById(int id) throws IOException {
		Optional<Data> imageObject = datarepo.findById(id);
		List<Object> list = new ArrayList<>();

		int Id = imageObject.get().getId();
		Timestamp ts = imageObject.get().getTimeStamp();
		String res = imageObject.get().getResult();

		String fullPathC = imageObject.get().getcImage();
		String fullPathP = imageObject.get().getpImage();

		list.add(Id);
		list.add(ts);
		list.add(res);
		list.add(Files.readAllBytes(new File(fullPathC).toPath()));
		list.add(Files.readAllBytes(new File(fullPathP).toPath()));
		return list;
	}

	@Override
	public List<ArrayList<Object>> displayAll() throws IOException {
		List<Integer> idList = datarepo.Id();

		List<ArrayList<Object>> list1 = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list;

		for (Integer id : idList) {
			Optional<Data> imageObject = datarepo.findById(id);
			int Id = imageObject.get().getId();
			Timestamp ts = imageObject.get().getTimeStamp();
			String res = imageObject.get().getResult();

			String fullPathC = imageObject.get().getcImage();
			String fullPathP = imageObject.get().getpImage();

			list = new ArrayList<Object>();
			list.add(Id);
			list.add(ts);
			list.add(res);
			list.add(Files.readAllBytes(new File(fullPathC).toPath()));
			list.add(Files.readAllBytes(new File(fullPathP).toPath()));

			list1.add(list);
		}

		return list1;
	}

	@Override
	public List<ArrayList<Object>> displayByIdRange(int id1, int id2) throws IOException {

		List<ArrayList<Object>> list1 = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list = new ArrayList<>();

		Integer a = id1;
		Integer b = id2;

		for (Integer i = a; i <= b; i++) {
			Optional<Data> imageObject = datarepo.findById(i);

			int Id = imageObject.get().getId();
			Timestamp ts = imageObject.get().getTimeStamp();
			String res = imageObject.get().getResult();

			String fullPathC = imageObject.get().getcImage();
			String fullPathP = imageObject.get().getpImage();

			list = new ArrayList<>();
			list.add(Id);
			list.add(ts);
			list.add(res);
			list.add(Files.readAllBytes(new File(fullPathC).toPath()));
			list.add(Files.readAllBytes(new File(fullPathP).toPath()));
			list1.add(list);
		}

		return list1;
	}

	@Override
	public User fetchUserByEmailIdAndPassword(User request) throws ResourceNotFoundException {
		PasswordEncoder encodePass = new BCryptPasswordEncoder();
		User user = userrepo.findUserByEmailIdAndRole(request.getEmailId());
		if (encodePass.matches(request.getPassword(), user.getPassword())) {
			return user;
		} else {
			throw new ResourceNotFoundException("Invalid Credentials");
		}
	}

	@Override
	public User addUser(User user) {
		User u = new User();
		PasswordEncoder encodePass = new BCryptPasswordEncoder();
		String pass = encodePass.encode(user.getPassword());
		u.setEmailId(user.getEmailId());
		u.setPassword(pass);
		u.setRole("user");
		return userrepo.save(u);
	}

	@Override
	public List<ArrayList<Object>> filterDataByResult(String result) throws IOException {
		List<Integer> idList = datarepo.filterByResult(result);
		List<ArrayList<Object>> list1 = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list ;
		
		for (Integer id:idList) {
			Optional<Data> imageObject = datarepo.findById(id);

			int Id = imageObject.get().getId();
			Timestamp ts = imageObject.get().getTimeStamp();
			String res = imageObject.get().getResult();

			String fullPathC = imageObject.get().getcImage();
			String fullPathP = imageObject.get().getpImage();

			list = new ArrayList<>();
			list.add(Id);
			list.add(ts);
			list.add(res);
			list.add(Files.readAllBytes(new File(fullPathC).toPath()));
			list.add(Files.readAllBytes(new File(fullPathP).toPath()));
			list1.add(list);
		}
		return list1;
	}

	@Override
	public List<ArrayList<Object>> filterDataByDate(String start, String end) throws IOException {
		List<Integer> idList = datarepo.filterByDate(start, end);
		List<ArrayList<Object>> list1 = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list ;
		
		for (Integer id:idList) {
			Optional<Data> imageObject = datarepo.findById(id);

			int Id = imageObject.get().getId();
			Timestamp ts = imageObject.get().getTimeStamp();
			String res = imageObject.get().getResult();

			String fullPathC = imageObject.get().getcImage();
			String fullPathP = imageObject.get().getpImage();

			list = new ArrayList<>();
			list.add(Id);
			list.add(ts);
			list.add(res);
			list.add(Files.readAllBytes(new File(fullPathC).toPath()));
			list.add(Files.readAllBytes(new File(fullPathP).toPath()));
			list1.add(list);
		}
		return list1;
	}

	// DashBoard API's
	
	@Override
	public int totalCount() {
		int count = datarepo.totalCount();
		return count;
	}

	@Override
	public int passCount() {
		int count = datarepo.passCount();
		return count;
	}

	@Override
	public int failCount() {
		int count = datarepo.failCount();
		return count;
	}

	@Override
	public int totalCountByDate(String startDate, String endDate) {
		int count = datarepo.totalCountByDate(startDate, endDate);
		return count;
	}

	@Override
	public int passCountByDate(String startDate, String endDate) {
		int count = datarepo.passCountByDate(startDate, endDate);
		return count;
	}

	@Override
	public int failCountByDate(String startDate, String endDate) {
		int count = datarepo.failCountByDate(startDate, endDate);
		return count;
	}

	//================Reset Password====================//
	@Override
	public User findByEmail(String email) {
		User user = passwordrepo.findByEmail(email);
		return user;
	}

	@Override
	public void createPasswordResetToken(String token , User user) {
		User passwordresettoken = passwordrepo.findByEmail(user.getEmailId());
		passwordresettoken.setToken(token);
		passwordrepo.save(passwordresettoken);
	}
	
	@Override
	public void sendREsetPasswordEmail(String toEmailId, String resetPasswordUrl) {
		SimpleMailMessage message = new SimpleMailMessage();
//		message.setFrom("eVisionSupport");
		message.setTo(toEmailId);
		message.setSubject("Reset Password");
		String msg = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + resetPasswordUrl + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
		message.setText(msg);
		javamailsender.send(message);
	}

	@Override
	public User findByToken(String token) {
		User user = passwordrepo.findByToken(token);
		return user;
	}
	
	@Override
	public void updatePassword(String email, String password) {
		User user = passwordrepo.findByEmail(email);
		user.setPassword(password);
		userrepo.save(user);
	}
	
	// notification -- count
	public int getNotificationCount() {
		int count = datarepo.getNotificationCount();
		return count;	
	}
	
	// notification -- display
	public ArrayList<String> getNotification() {
		List<Integer> data = datarepo.getNotification();
		String str="";
		ArrayList<String> s1 = new ArrayList<>();
		for(Integer i:data) {
			Optional<Data> d = datarepo.findById(i);
			int id = d.get().getId();
			String result = d.get().getResult();
			str=  "Image Has Been Uploaded With ID:"+id+" And Result:"+result;
			s1.add(str);
		}
		return s1;
	}
}
