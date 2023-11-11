package nu.rolandsson.ISBNValidator.model;

import nu.rolandsson.ISBNValidator.type.SequenceType;

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
	
	protected abstract int getDigitSum();
	
	protected abstract int getCheckDigit();
	
	public String getValue() {
		return this.value;
	}
	
	public SequenceType getType() {
		return this.type;
	}
	
	public ISBNParts getParts() {
		return this.parts;
	}
	
	protected String[] getSequence() {
		return this.getValue().split("");
	}
	
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
