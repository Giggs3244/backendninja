package com.giggs.service;

import java.util.List;

import com.giggs.entity.Contact;
import com.giggs.model.ContactModel;

public interface ContactService {
	
	public abstract ContactModel addContact(ContactModel contactModel);
	public abstract List<ContactModel> listAllContacts();
	public abstract Contact findContactById(int id);
	public abstract void removeContact(int id);
	public abstract ContactModel findContactModelById(int id);	

}
