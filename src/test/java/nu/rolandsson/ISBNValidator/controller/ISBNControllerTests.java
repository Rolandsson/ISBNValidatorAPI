package nu.rolandsson.ISBNValidator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test; 
/**
 * End to end tests for ISBN rest API
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("controller-test")
public class ISBNControllerTests {

	@Autowired
	private MockMvc mockMvc;
	
	static List<String> validISBNs;
	static List<String> invalidISBNs;
	
	@BeforeAll
	static void prepareTestData() {
		
		// Change this to validate different ISBNs, accepts 10 or 13 digit sequences and with whitespaces, dashes as well as isbns prefixes
		// Currently not supporting 9 digit sequences
		validISBNs = List.of(
				"9185057819",
				"9783161484100",
				"1111111111",
				"1111111111116"
		);

		// Change this to invalidate different ISBNs, accepts 10 or 13 digit sequences and with whitespaces, dashes as well as isbns prefixes
		// Observe that it's intended to be used for invalid sequences, thus it must still adhere to the format standards provided by the International ISBN Agency
		invalidISBNs = List.of(
				"9185057818",
				"9783161484101",
				"1111111112",
				"1111111111115"
		);
		
	}
	
	/**
	 * General test for valid IBNS
	 * @throws Exception
	 */
	@Test
	void shouldReturnTrueForAllValidISBNTestData() throws Exception {
		
		for(String isbn : validISBNs) {
			this.mockMvc
				.perform(get("/isbn/" + isbn))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.valid").value(true));
		}
	}
	
	/**
	 * General test for invalid IBNS
	 * @throws Exception
	 */
	@Test
	void shouldReturnFalseForAllInvalidISBNTestData() throws Exception {
		for(String isbn : invalidISBNs) {
			this.mockMvc
				.perform(get("/isbn/" + isbn))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.valid").value(false));
		}
	}
	
	/**
	 * Ensures that response contains proper isbn parts for isbn10 as well as a true valid flag
	 * @throws Exception
	 */
	@Test
	void shouldReturnValidISBN10() throws Exception {
		String isbn10 = "1111111111";
		
		this.mockMvc
			.perform(get("/isbn/" + isbn10))
			.andDo(log())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.type").value("ISBN10"))
			.andExpect(jsonPath("$.valid").value(true))
			.andExpect(jsonPath("$.parts.group").value("11"))
			.andExpect(jsonPath("$.parts.publisher").value("1111"))
			.andExpect(jsonPath("$.parts.title").value("111"))
			.andExpect(jsonPath("$.parts.checkDigit").value("1"));
	}
	
	/**
	 * Ensures that response contains for invalid isbn10s contains a false valid flag
	 * @throws Exception
	 */
	@Test
	void shouldReturnInvalidISBN10() throws Exception {
		String isbn10 = "1111111112";
		
		this.mockMvc
			.perform(get("/isbn/" + isbn10))
			.andDo(log())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.valid").value(false));
	}
	
	/**
	 * Ensures that response contains proper isbn parts for isbn13 as well as a true valid flag
	 * @throws Exception
	 */
	@Test
	void shouldReturnValidISBN13() throws Exception {
		String isbn13 = "1111111111116";
		
		this.mockMvc
			.perform(get("/isbn/" + isbn13))
			.andDo(log())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.valid").value(true))
			.andExpect(jsonPath("$.parts.ean").value("111"))
			.andExpect(jsonPath("$.parts.group").value("11"))
			.andExpect(jsonPath("$.parts.publisher").value("1111"))
			.andExpect(jsonPath("$.parts.title").value("111"))
			.andExpect(jsonPath("$.parts.checkDigit").value("6"));
	}
	
	/**
	 * Ensures that response contains for invalid isbn13s contains a false valid flag
	 * @throws Exception
	 */
	@Test
	void shouldReturnInvalidISBN13() throws Exception {
		String isbn13 = "1111111111115";
		
		this.mockMvc
			.perform(get("/isbn/" + isbn13))
			.andDo(log())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.valid").value(false));
	}
	
	/**
	 * Ensures that API returns bad request (400) if message is too long or short
	 * @throws Exception
	 */
	@Test
	void shouldThrowBadRequestForInvalidISBNLength() throws Exception {
		String invalidLongLengthISBN = "11111111111115";
		
		this.mockMvc
			.perform(get("/isbn/" + invalidLongLengthISBN))
			.andDo(log())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.sequenceLength").value(14));
		

		String invalidShortLengthISBN = "111111111115";
		
		this.mockMvc
			.perform(get("/isbn/" + invalidShortLengthISBN))
			.andDo(log())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.sequenceLength").value(12));
	}
	
	/**
	 * Ensures that API returns bad request for invalid characters (non digits (excluding "x" and "X"))
	 * API accepts whitespaces, dashes and ISBN prefixes, including ":" delimiter
	 * @throws Exception
	 */
	@Test
	void shouldThrowBadCharacterForInvalidInputCharacters() throws Exception {
		String invalidCharacterISBN = "1111111A11115";
		
		this.mockMvc
			.perform(get("/isbn/" + invalidCharacterISBN))
			.andDo(log())
			.andExpect(status().isBadRequest());
	}
}
