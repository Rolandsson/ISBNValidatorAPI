package nu.rolandsson.ISBNValidator.model;

import nu.rolandsson.ISBNValidator.type.SequenceType;

public class ISBN13 extends ISBN {
	
	public static final int EXPECTED_SEQUENCE_LENGTH = 13;

	public ISBN13(String value) {
		super(value, SequenceType.ISBN13);
	}

	@Override
	protected int getDigitSum() {
		String[] sequence = this.getSequence();
		int sum = 0;
		
		for(int i = 0; i < EXPECTED_SEQUENCE_LENGTH - 1; i++) {
			if((i + 1) % 2 == 0) {
				sum += 3 * Integer.parseInt(sequence[i]);
			} else {
				sum += Integer.parseInt(sequence[i]);
			}
		}
		return sum;
	}

	@Override
	protected int getCheckDigit() {
		String checksumDigit = this.getParts().getCheckDigit();
		return Integer.parseInt(checksumDigit);
	}

}
