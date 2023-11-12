package nu.rolandsson.ISBNValidator.exception;

import java.util.regex.Pattern;

public class BadISBNRequestException extends RuntimeException {
	/**
	 * Implemented serialization in the event of cross network validations
	 */
	private static final long serialVersionUID = 7850635160298338738L;
	
	private String formattedSequence;
	private Pattern[] acceptedPatterns;
	private Pattern redundancyPattern;
	
	/**
	 * BadISBNRequestException handles incorrect length or format for provided isbn sequence. It provides information about what sequence that was used and which redundancy pattern that can be used
	 * @param formattedSequence the formatted sequence to be used in an error message
	 * @param redundancyPattern the redundancy pattern that was used to filter invalid tokens
	 * @param acceptedPatterns the regex for valid isbn sequences used in by the api
	 */
	public BadISBNRequestException(String formattedSequence, Pattern redundancyPattern, Pattern[] acceptedPatterns) {
		super("Bad ISBN for sequence: " + formattedSequence);
		this.formattedSequence = formattedSequence;
		this.acceptedPatterns = acceptedPatterns;
		this.redundancyPattern = redundancyPattern;
	}
	
	public String getFormattedSequence() {
		return this.formattedSequence;
	}
	
	public Pattern[] getAcceptedPatterns() {
		return this.acceptedPatterns;
	}
	
	public Pattern getRedundancyPattern() {
		return this.redundancyPattern;
	}
}
