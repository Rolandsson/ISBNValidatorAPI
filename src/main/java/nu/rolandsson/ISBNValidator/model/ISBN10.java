package nu.rolandsson.ISBNValidator.model;

import nu.rolandsson.ISBNValidator.type.SequenceType;

public class ISBN10 extends ISBN {
	
	public static final int EXPECTED_SEQUENCE_LENGTH = 10;
	
	private static final int VALUE_OF_X = 10;

	public ISBN10(String value) {
		super(value, SequenceType.ISBN10);
	}

	@Override
	protected int getDigitSum() {
		String[] sequence = this.getSequence();
		int sum = 0;
		
		for(int i = 0; i < EXPECTED_SEQUENCE_LENGTH - 1; i++) {
			sum += (EXPECTED_SEQUENCE_LENGTH - i) * Integer.parseInt(sequence[i]);
		}
		return sum;
	}

	@Override
	protected int getCheckDigit() {
		String checksumDigit = this.getParts().getCheckDigit();
		return checksumDigit.equalsIgnoreCase("x") ? VALUE_OF_X : Integer.parseInt(checksumDigit);
	}

}
