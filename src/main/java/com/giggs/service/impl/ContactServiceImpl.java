package com.giggs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.giggs.component.ContactConverter;
import com.giggs.entity.Contact;
import com.giggs.model.ContactModel;
import com.giggs.repository.ContactRepository;
import com.giggs.service.ContactService;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {

	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;

	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;

	@Override
	public ContactModel addContact(ContactModel contactModel) {
		Contact contact = contactRepository.save(contactConverter.converterModel2Entity(contactModel));
		return contactConverter.converterEntity2Model(contact);
	}

	@Override
	public List<ContactModel> listAllContacts() {
		// List<Contact> contacts = contactRepository.findAll();
		// return contactConverter.converterEntities2Models(contacts);
		return contactConverter.converterEntities2ModelsJ8(contactRepository.findAll());
	}

	@Override
	public Contact findContactById(int id) {
		return contactRepository.findById(id);
	}
	
	@Override
	public ContactModel findContactModelById(int id) {
		return contactConverter.converterEntity2Model(findContactById(id));
	}

	@Override
	public void removeContact(int id) {
		Contact contact = findContactById(id);
		if(contact != null) {
			contactRepository.delete(contact);			
		}
	}
	
	

}
