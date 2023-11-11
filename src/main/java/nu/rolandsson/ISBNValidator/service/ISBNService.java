package nu.rolandsson.ISBNValidator.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.rolandsson.ISBNValidator.exception.BadISBNRequestException;
import nu.rolandsson.ISBNValidator.model.ISBN;
import nu.rolandsson.ISBNValidator.model.ISBN10;
import nu.rolandsson.ISBNValidator.model.ISBN13;

@Service("isbnService")
public class ISBNService {
	
	Logger logger = LoggerFactory.getLogger(ISBNService.class);
	
	@Autowired
	private Pattern isbnRedudantPattern;
	
	@Autowired
	private Pattern validFormattedISBN10Pattern;
	
	@Autowired
	private Pattern validFormattedISBN13Pattern;

	public ISBN validate(String sequence) {
		String reducedSequence = isbnRedudantPattern.matcher(sequence).replaceAll("");
		
		if(!reducedSequence.equals(sequence)) {
			logger.debug("Sequence {} reduced to {}", sequence, reducedSequence);
		}
		
		if(validFormattedISBN10Pattern.matcher(reducedSequence).find()) {
			return new ISBN10(reducedSequence);
		} else if(validFormattedISBN13Pattern.matcher(reducedSequence).find()) {
			return new ISBN13(reducedSequence);
		} else {
			throw new BadISBNRequestException(reducedSequence, isbnRedudantPattern, new Pattern[] {validFormattedISBN10Pattern, validFormattedISBN13Pattern});
		}
		
	}
}
