package nu.rolandsson.ISBNValidator.model;

import java.util.stream.Stream;

import nu.rolandsson.ISBNValidator.exception.BadISBNRequestException;

/**
 * Class represents ISBN client error messages. Should remain flexible enough to handle an expanding need for error handling
 */
public class BadSequenceError {
	
	private String sequence;
	private String redundancyPattern;
	private String[] acceptedPatterns;
	private String errorMessage;
	private int sequenceLength;
	
	/**
	 * Construct an error message based on ISBN sequence and redundancy pattern
	 * @param ex contains information about redudancy pattern, formatted sequence and patterns that the api may accept
	 */
	public BadSequenceError(BadISBNRequestException ex) {
		this.sequence = ex.getFormattedSequence();
		this.sequenceLength = this.sequence.length();
		this.redundancyPattern = ex.getRedundancyPattern().pattern();
		this.acceptedPatterns = Stream.of(ex.getAcceptedPatterns()).map(p -> p.pattern()).toArray(String[]::new);		
	}

	public String getSequence() {
		return this.sequence;
	}

	public String getRedundancyPattern() {
		return this.redundancyPattern;
	}

	public int getSequenceLength() {
		return this.sequenceLength;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public String[] getAcceptedPatterns() {
		return this.acceptedPatterns;
	}
	
	/**
	 * Set the actual error message provided to the client
	 * @param message error message property 
	 */
	public void setMessage(String message) {
		this.errorMessage = message;
	}

	@Override
	public String toString() {
		return "BadSequenceError [errorMessage=" + errorMessage + ", sequenceLength=" + sequenceLength + "]";
	}
	
	

}
