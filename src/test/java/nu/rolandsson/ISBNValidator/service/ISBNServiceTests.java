package nu.rolandsson.ISBNValidator.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nu.rolandsson.ISBNValidator.exception.BadISBNRequestException;

/**
 * Smokes tests for isbn10 and isbn13 services in relation to their format and exception throwing
 */
@SpringBootTest
public class ISBNServiceTests {
	
	@Autowired
	private ISBNService isbnService;
	
	/**
	 * Tests a variety of different formats for isbn10
	 * Currently allows free usage of whitespaces and dashes as well as isbn prefixes
	 */
	@Test
	void validatesAcceptedFormatsOfISBN10() {
		List<String> acceptedFormats = List.of(
				"0000000000",
				"ISBN 0000000000",
				"ISBN: 0000000000",
				"ISBN 10 0000000000",
				"ISBN 10: 0000000000",
				"000000000-0",
				"00 0000 000-0",
				"ISBN 00 0000 000-0"
		);
		
		
		assertDoesNotThrow(() -> acceptedFormats.forEach(isbn -> isbnService.validate(isbn)));
		assertThrows(BadISBNRequestException.class, () -> isbnService.validate("00000000001"));
	}
	
	/**
	 * Tests a variety of different formats for isbn13
	 * Currently allows free usage of whitespaces and dashes as well as isbn prefixes
	 */
	@Test
	void validatesAcceptedFormatsOfISBN13() {
		List<String> acceptedFormats = List.of(
				"0000000000000",
				"ISBN 0000000000000",
				"ISBN: 0000000000000",
				"ISBN 13 0000000000000",
				"ISBN 13: 0000000000000",
				"000000000000-0",
				"000 00 0000 000-0",
				"ISBN 000 00 0000 000-0"
		);
		
		
		assertDoesNotThrow(() -> acceptedFormats.forEach(isbn -> isbnService.validate(isbn)));
		assertThrows(BadISBNRequestException.class, () -> isbnService.validate("00000000000001"));
	}

}
