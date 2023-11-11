package nu.rolandsson.ISBNValidator.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Configuration class for pattern matching
 * Provides patterns for redundancy matching and valid ISBN10 and ISBN13 formats
 * 
 * Currently only supports ISBN of length 10 (9 excluded) and 13
 */
@Configuration
public class PatternConfiguration {
	@Bean
	public Pattern isbnRedudantPattern() {
		return Pattern.compile("([ \\-:]|ISBN 13|ISBN 10|ISBN)");
	}
	
	@Bean
	public Pattern validFormattedISBN10Pattern() {
		return Pattern.compile("^[0-9Xx]{10}$");
	}
	
	@Bean
	public Pattern validFormattedISBN13Pattern() {
		return Pattern.compile("^[0-9]{13}$");
	}
}
