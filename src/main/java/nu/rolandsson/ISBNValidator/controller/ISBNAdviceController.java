package nu.rolandsson.ISBNValidator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import nu.rolandsson.ISBNValidator.exception.BadISBNRequestException;
import nu.rolandsson.ISBNValidator.model.BadSequenceError;
import nu.rolandsson.ISBNValidator.model.ISBN10;
import nu.rolandsson.ISBNValidator.model.ISBN13;

@ControllerAdvice
public class ISBNAdviceController {
	
	Logger logger = LoggerFactory.getLogger(ISBNAdviceController.class);
	
	@ExceptionHandler(BadISBNRequestException.class)
	public ResponseEntity<BadSequenceError> handleBadSequenceException(BadISBNRequestException ex) {
		BadSequenceError error = new BadSequenceError(ex);
		
		if(error.getSequenceLength() != ISBN10.EXPECTED_SEQUENCE_LENGTH && error.getSequenceLength() != ISBN13.EXPECTED_SEQUENCE_LENGTH) {
			error.setMessage("Sequence does not match length of ISBN10 or ISBN13, verify sequence length");
		} else {
			error.setMessage("Submitted sequence does not adhere to permitted API isbn patterns");
		}
		
		logger.debug("Sequence {} failed to validate with error {}", ex.getFormattedSequence(), error);
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(error);
	}
}
