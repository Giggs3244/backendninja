package com.giggs.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.giggs.entity.Contact;
import com.giggs.model.ContactModel;

@Component("contactConverter")
public class ContactConverter {

	public Contact converterModel2Entity(ContactModel contactModel) {
		if (contactModel != null) {
			Contact contact = new Contact();
			contact.setId(contactModel.getId());
			contact.setFirstName(contactModel.getFirstName());
			contact.setLastName(contactModel.getLastName());
			contact.setTelephone(contactModel.getTelephone());
			contact.setCity(contactModel.getCity());
			return contact;
		}
		return null;
	}

	public ContactModel converterEntity2Model(Contact contact) {
		if (contact != null) {
			ContactModel contactModel = new ContactModel();
			contactModel.setId(contact.getId());
			contactModel.setFirstName(contact.getFirstName());
			contactModel.setLastName(contact.getLastName());
			contactModel.setTelephone(contact.getTelephone());
			contactModel.setCity(contact.getCity());
			return contactModel;
		}
		return null;
	}

	public List<ContactModel> converterEntities2Models(List<Contact> contacts) {
		if (contacts != null) {
			List<ContactModel> models = new ArrayList<>();
			for (Contact contact : contacts) {
				models.add(converterEntity2Model(contact));
			}
			return models;
		}
		return null;
	}

	public List<Contact> converterModels2Entities(List<ContactModel> contactsModel) {
		if (contactsModel != null) {
			List<Contact> contacts = new ArrayList<>();
			for (ContactModel contactModel : contactsModel) {
				contacts.add(converterModel2Entity(contactModel));
			}
			return contacts;
		}
		return null;
	}

	public List<ContactModel> converterEntities2ModelsJ8(List<Contact> contacts) {
		if (contacts != null) {
			return contacts.stream().map(c -> converterEntity2Model(c)).collect(Collectors.toList());
		}
		return null;
	}

	public List<Contact> converterModels2EntitiesJ8(List<ContactModel> contactsModel) {
		if (contactsModel != null) {
			return contactsModel.stream().map(cm -> converterModel2Entity(cm)).collect(Collectors.toList());
		}
		return null;
	}

}
