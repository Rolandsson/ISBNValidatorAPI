package nu.rolandsson.ISBNValidator.model;

import nu.rolandsson.ISBNValidator.type.SequenceType;

/**
 * ISBN base class. 
 * To be used in conjunction with ISBN10 and ISBN13 implementations.
 */
public abstract class ISBN {
	private String value;
	private SequenceType type;
	private ISBNParts parts;
	
	private static final int ISBN_13_DIVISOR = 10;
	private static final int ISBN_10_DIVISOR = 11;
	private static final int ISBN_13_N_10_EXPECTED_REMINDER = 0;
	
	
	public ISBN(String value, SequenceType type) {
		
		this.value = value;
		this.type = type;
		this.parts = new ISBNParts(type, value.split(""));
	}
	
	/**
	 * Implemented by base class to provide the algorithm for respective digit sum
	 * @return
	 */
	protected abstract int getDigitSum();
	
	/**
	 * Check digit provides differently for ISBN10 and ISBN13, see respective implementation for details
	 * @return The digit sum, should be 10 if ISBN10 ends with "X" or "x"
	 */
	protected abstract int getCheckDigit();
	
	/**
	 * Value of the entire sequence string
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
	
	public SequenceType getType() {
		return this.type;
	}
	
	/**
	 * International ISBN agency standards providing ean (ISBN13), group, title, publisher, check digit
	 * @return
	 */
	public ISBNParts getParts() {
		return this.parts;
	}
	
	/**
	 * Ease of access to individual characters
	 * @return
	 */
	protected String[] getSequence() {
		return this.getValue().split("");
	}
	
	/**
	 * Uses check digit to determine if sequence is valid
	 * @return if the ISBN10 or ISBN13 sequence is valid
	 */
	public boolean isValid() {
		return switch(this.type) {
			case ISBN10 -> (this.getDigitSum() + getCheckDigit()) % ISBN_10_DIVISOR == ISBN_13_N_10_EXPECTED_REMINDER;
			case ISBN13 -> (this.getDigitSum() + getCheckDigit()) % ISBN_13_DIVISOR == ISBN_13_N_10_EXPECTED_REMINDER;
		};
	}

	@Override
	public String toString() {
		return "ISBN [value=" + value + ", type=" + type + ", parts=" + parts + ", digitSum=" + getDigitSum()
				+ ", valid=" + isValid() + "]";
	}
	
	
}
