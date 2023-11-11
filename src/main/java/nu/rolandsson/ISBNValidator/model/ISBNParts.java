package nu.rolandsson.ISBNValidator.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import nu.rolandsson.ISBNValidator.type.SequenceType;

@JsonInclude(Include.NON_NULL)
public class ISBNParts {
	
	public ISBNParts(SequenceType type, String[] parts) {
		
		if(type == SequenceType.ISBN10) {
			this.group = String.join("", Arrays.copyOfRange(parts, 0, 2));
			this.publisher = String.join("", Arrays.copyOfRange(parts, 2, 6));
			this.title = String.join("", Arrays.copyOfRange(parts, 6, 9));
			this.checkDigit = parts[9];
		} else if(type == SequenceType.ISBN13) {
			this.ean = String.join("", Arrays.copyOfRange(parts, 0, 3));
			this.group = String.join("", Arrays.copyOfRange(parts, 3, 5));
			this.publisher = String.join("", Arrays.copyOfRange(parts, 5, 9));
			this.title = String.join("", Arrays.copyOfRange(parts, 9, 12));
			this.checkDigit = parts[12];
		}
	}
	
	private String ean;
	private String group;
	private String publisher;
	private String title;
	private String checkDigit;
	
	public String getEan() {
		return this.ean;
	}
	
	public String getGroup() {
		return this.group;
	}
	
	public String getPublisher() {
		return this.publisher;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getCheckDigit() {
		return this.checkDigit;
	}

	@Override
	public String toString() {
		return "ISBNParts [ean=" + ean + ", group=" + group + ", publisher=" + publisher + ", title=" + title
				+ ", checkDigit=" + checkDigit + "]";
	}
	
	
	
}
