package nu.rolandsson.ISBNValidator.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;

/**
 * Unit tests for ISBN model
 */

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ISBNTests {

	/**
	 * Ensures that ISBN10 format returns proper structure with corresponding parts
	 * ean is set to null since isbn10 does not contain an ean.
	 */
	@Test
	void setISBNPartsForISBN10() {
		String sequence = "123456789X";
		ISBN isbn = new ISBN10(sequence);
		ISBNParts parts = isbn.getParts();
		
		assertThat(parts.getEan()).isNull();
		assertThat(parts.getGroup()).isEqualTo("12");
		assertThat(parts.getPublisher()).isEqualTo("3456");
		assertThat(parts.getTitle()).isEqualTo("789");
		assertThat(parts.getCheckDigit()).isEqualTo("X");
	}
	
	/**
	 * Ensures that ISBN13 format returns proper structure with corresponding parts
	 */
	@Test
	void setISBNPartsForISBN13() {
		String sequence = "1234567891234";
		ISBN isbn = new ISBN13(sequence);
		ISBNParts parts = isbn.getParts();
		
		assertThat(parts.getEan()).isEqualTo("123");
		assertThat(parts.getGroup()).isEqualTo("45");
		assertThat(parts.getPublisher()).isEqualTo("6789");
		assertThat(parts.getTitle()).isEqualTo("123");
		assertThat(parts.getCheckDigit()).isEqualTo("4");
	}
	
	/**
	 * Ensures that ISBN10 format returns the value of 10 instead of "X" per ISBN agency's specification
	 */
	@Test
	void convertISBN10LastDigitXTo10() {
		String sequence = "123456789X";
		ISBN isbn = new ISBN10(sequence);
		
		assertThat(isbn.getCheckDigit()).isEqualTo(10);
	}
	
	/**
	 * Ensures that isbn10 model can be validated
	 */
	@Test
	void validatesValidISBN10DigitSum() {
		String sequence = "1111111111";
		ISBN isbn = new ISBN10(sequence);
		
		assertThat(isbn.isValid()).isTrue();
	}
	
	/**
	 * Ensures that isbn10 model can be invalidated
	 */
	@Test
	void invalidatesInvalidISBN10DigitSum() {
		String sequence = "1111111112";
		ISBN isbn = new ISBN10(sequence);
		
		assertThat(isbn.isValid()).isFalse();
	}
	
	/**
	 * Ensures that isbn13 model can be validated
	 */
	@Test
	void validatesValidISBN13DigitSum() {
		String sequence = "1111111111116";
		ISBN isbn = new ISBN13(sequence);
		
		assertThat(isbn.isValid()).isTrue();
	}
	
	/**
	 * Ensures that isbn13 model can be invalidated
	 */
	@Test
	void invalidatesInvalidISBN13DigitSum() {
		String sequence = "1111111111115";
		ISBN isbn = new ISBN13(sequence);
		
		assertThat(isbn.isValid()).isFalse();
	}
	
	/**
	 * Added per tech case specification
	 * May be ignored as they are tested again in end to end tests within the controller
	 */
	@Test
	@Disabled
	void shouldReturnTrueForExamplesInISBNAssignmentFile() {
		String isbn10Sequence = "9185057819";
		String isbn13Sequence = "9783161484100";
		
		ISBN isbn10 = new ISBN10(isbn10Sequence);
		ISBN isbn13 = new ISBN13(isbn13Sequence);
		
		assertThat(isbn10.isValid()).isTrue();
		assertThat(isbn13.isValid()).isTrue();
	}
}
