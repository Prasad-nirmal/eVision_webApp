package com.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.entity.Data;
import com.app.entity.User;
import com.app.exception.ResourceNotFoundException;

public interface IService {
public Data uploadImage(MultipartFile cImage, MultipartFile pImage,String result) throws IOException;
public List<Object> displayById (int id) throws IOException ;
public List<ArrayList<Object>> displayAll() throws IOException ;
public List<ArrayList<Object>> displayByIdRange(int id1, int id2) throws IOException;
public User fetchUserByEmailIdAndPassword(User request) throws ResourceNotFoundException;
public User addUser(User user);
public List<ArrayList<Object>> filterDataByResult(String result) throws IOException;
public List<ArrayList<Object>> filterDataByDate(String start, String end) throws IOException;
public int totalCount();
public int passCount();
public int failCount();
public int totalCountByDate(String startDate, String endDate);
public int passCountByDate(String startDate, String endDate);
public int failCountByDate(String startDate, String endDate);

//======================================//
public User findByEmail(String email);
public void createPasswordResetToken(String token, User user);
public void sendREsetPasswordEmail(String to, String resetPasswordUrl);
public User findByToken(String token);
public void updatePassword(String email, String password);

//==========notification===============//
public ArrayList<String> getNotification();
public int getNotificationCount();
}
