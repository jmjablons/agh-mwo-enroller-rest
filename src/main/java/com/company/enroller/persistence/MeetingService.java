package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	@SuppressWarnings("unchecked")
	public Collection<Meeting> getAll() {
//		String hql = "FROM Meeting";
//		Query query = connector.getSession().createQuery(hql);
//		return query.list();
		return connector.getSession().createCriteria(Meeting.class).list();
	}
	
	public Meeting findById(long id){
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}
	
	public void add(Meeting meeting) {
		org.hibernate.Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting) {
		org.hibernate.Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void update(Meeting meeting) {
		org.hibernate.Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().update(meeting);
		transaction.commit();
	}

	public Meeting findByTitle(String title) {
		return (Meeting) connector.getSession().get(Meeting.class, (String) title);
	}

}
