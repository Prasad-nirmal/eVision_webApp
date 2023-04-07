package com.app.repo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entity.Data;
import com.app.entity.User;

public interface DataRepository extends JpaRepository<Data, Integer> {
	
	// get list of id's only
	@Query(value = "select id from data", nativeQuery = true )
	List<Integer> Id();
	
	//get the count of data from the particular period
	@Query(value = "select * from data where date(time_stamp) between ?1 and ?2", nativeQuery = true)
	List<Integer> filterByDate(String start, String end);
	
	// filter data by the Result
	@Query(value = "select id from data where result=?1", nativeQuery = true)
	List<Integer> filterByResult(String result);
	
	//Total Count Of Image Data
	@Query(value = "select count(id) from data", nativeQuery = true)
	int totalCount();
	
	//Total Count Of Pass Images
	@Query(value = "select count(id) from data where result='pass'",nativeQuery = true)
	int passCount();
	
	//Total Count Of Fail Images
	@Query(value = "select count(id) from data where result='fail'",nativeQuery = true)
	int failCount();
	
	//Total Count Of Image Data By Date
		@Query(value = "select count(id) from data where date(time_stamp) between ?1 and ?2", nativeQuery = true)
		int totalCountByDate(String startDate, String endDate);
		
		//Total Count Of Pass Images By Date
		@Query(value = "select count(id) from data where date(time_stamp) between ?1 and ?2 and result='pass'",nativeQuery = true)
		int passCountByDate(String startDate, String endDate);
		
		//Total Count Of Fail Images By Date 
		@Query(value = "select count(id) from data where date(time_stamp) between ?1 and ?2 and result='fail'",nativeQuery = true)
		int failCountByDate(String startDate, String endDate);

		//Notification
		@Query(value = "select id from data where result='pass' order by date(time_stamp) desc",nativeQuery = true)
		List<Integer> getNotification();
		
		//Notification
		@Query(value = "select count(id) from data where result='pass' ",nativeQuery = true)
		int getNotificationCount();
}
