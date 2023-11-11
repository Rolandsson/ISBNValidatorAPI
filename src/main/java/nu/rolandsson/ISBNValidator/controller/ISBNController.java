package nu.rolandsson.ISBNValidator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import nu.rolandsson.ISBNValidator.model.ISBN;
import nu.rolandsson.ISBNValidator.service.ISBNService;

@RestController
public class ISBNController {
	
	Logger logger = LoggerFactory.getLogger(ISBNController.class);

	@Autowired
	private ISBNService isbnService;
	
	@GetMapping(value = "/isbn/{sequence}")
	@CrossOrigin(origins = "*")
	public ResponseEntity<ISBN> validateISBN(@PathVariable String sequence) {
		logger.info("New ISBN validation request for sequence {}", sequence);
		
		ISBN isbn = isbnService.validate(sequence);
		
		logger.info("Sequence {} validated as {}", sequence, isbn);
		return ResponseEntity.ok(isbn);
	}
}
