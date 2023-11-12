# ISBN Validation API
Does not verify the existence of an ISBN, but instead implements the specification validation algorithm for isbn10 and isbn13

## 1. Versions
- Java 17
- Spring boot 3.15
- Maven 3.9.5
- JUnit 5

---

## 2. Validation and isbn end-to-end tests

Try a variation of isbn validations by modifying the contents of validISBNs and invalidISBNs in **src/test/java/nu/rolandsson/ISBNValidator/controller/ISBNControllerTests.java** in the beforeAll method *prepareTestData*
```java
@BeforeAll
static void prepareTestData() {
	
	validISBNs = List.of(
			"9185057819",
			"9783161484100",
			"1111111111",
			"1111111111116"
	);

	invalidISBNs = List.of(
			"9185057818",
			"9783161484101",
			"1111111112",
			"1111111111115"
	);
	
}
```

<details>
	<summary>It will run the tests towards the endpoint <em>/isbn/{isbn sequences}</em> and verify that valid returns true and invalid returns false</summary>

```java
@Test
void shouldReturnTrueForAllValidISBNTestData() throws Exception {
	
	for(String isbn : validISBNs) {
		this.mockMvc
			.perform(get("/isbn/" + isbn))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.valid").value(true));
	}
}

@Test
void shouldReturnFalseForAllInvalidISBNTestData() throws Exception {
	for(String isbn : invalidISBNs) {
		this.mockMvc
			.perform(get("/isbn/" + isbn))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.valid").value(false));
	}
}
```

</details>

---

## 3. Installation

Download git repository using preferred method.

**Run executable jar**
```bash
java -jar target/ISBNValidator-1.0.jar --server.port={port}
```

**Run maven instance**
```bash
mvnw spring-boot:run
```

**Package new jar**
```bash
mvnw package
```

---

## 4. How to use
	
Validate 10 or 13 isbn digit sequences using a simple API call. Currently only accessible locally, thus requiring local installation.


### 4.1 API Call
	
```note
http://localhost:{port}/isbn/{isbn sequence}
```

#### Parameters
- *isbn sequence* (required!) a sequence of 10 or 13 digits representating isbn strings

### 4.2 Example API Call
The api currently does not support the 9-digit format, thus 0 should be added infront of a 9-digit isbn to ensure compatibility.

**ISBN 10**
```note
http://localhost:9090/isbn/9185057819
```

**ISBN 13**
```note
http://localhost:9090/isbn/9783161484100
```

### 4.3 Example API response
For a ISBN 13 valid request
```json
{
	"value": "9783161484100",
	"type": "ISBN13",
	"parts": {
		"ean": "978",
		"group": "31",
		"publisher": "6148",
		"title": "410",
		"checkDigit": "0"
	},
	"valid": true
}
```

#### Field response description
- *value* reduced representation of input value
- *type* the isbn type that was identified by the api, expected values are ISBN10 and ISBN13
- *parts* isbn parts of the digit sequence as specified by international isbn agency


---
