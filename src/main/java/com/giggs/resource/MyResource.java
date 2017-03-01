package com.giggs.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giggs.model.ContactModel;

@RestController
@RequestMapping("/rest")
public class MyResource {

	@GetMapping("/myresource")
	public ResponseEntity<ContactModel> getMyResource() {
		ContactModel cm = new ContactModel(10, "Bryan", "Stevens", "4365823", "Medellín");
		return new ResponseEntity<ContactModel>(cm , HttpStatus.OK);
	}

}
