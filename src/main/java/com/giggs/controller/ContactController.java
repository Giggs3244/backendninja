package com.giggs.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.giggs.constant.ViewConstant;
import com.giggs.model.ContactModel;
import com.giggs.service.ContactService;

@Controller
@RequestMapping("contacts")
public class ContactController {

	private static final Log LOGGER = LogFactory.getLog(ContactController.class);

	@Autowired
	@Qualifier("contactServiceImpl")
	private ContactService contactService;

	@GetMapping("/cancel")
	public String cancel() {
		return "redirect:/contacts/showcontacts";
	}

	@PreAuthorize("hasRole('ROLE') or hasRole('ROLE_ADMIN')")
	@GetMapping("/contactform")
	public String redirectContactForm(@RequestParam(name = "id", required = false, defaultValue = "0") int id,
			Model model) {
		ContactModel contact = null;
		if (id != 0) {
			contact = contactService.findContactModelById(id);
		} else {
			contact = new ContactModel();
		}
		model.addAttribute("contactModel", contact);
		return ViewConstant.CONTACT_FORM;
	}

	@PostMapping("/addcontact")
	public String addContact(@ModelAttribute("contactModel") ContactModel contactModel, Model model) {
		LOGGER.info("METHOD: addContact() -- PARAMS: " + contactModel);
		String redirect = "redirect:/contacts/showcontacts?result=";
		if (contactService.addContact(contactModel) != null) {
			redirect += 1;
		} else {
			redirect += 0;
		}
		return redirect;
	}

	@GetMapping("/showcontacts")
	public ModelAndView showContacts(@RequestParam(name = "result", required = false, defaultValue = "4") int result) {
		ModelAndView model = new ModelAndView(ViewConstant.CONTACTS);

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addObject("username", user.getUsername());
		model.addObject("result", result);
		model.addObject("contacts", contactService.listAllContacts());
		return model;
	}

	@GetMapping("/removecontact")
	public ModelAndView removeContact(@RequestParam(name = "id", required = true) int id) {
		contactService.removeContact(id);
		return showContacts(2);
	}

}
