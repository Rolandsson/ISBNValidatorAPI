# ISBN Validation API
Does not validate the existence of an ISBN, but instead implements the specification validation algorithm for isbn10 and isbn13

## 1. Versions
- Java 17
- Spring boot 3.15
- Maven 3.9.5

<details>
  <summary><h2>2. Installation</h2></summary>

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
</details>

---

## 3. How to use
	
Validate 10 or 13 isbn digit sequences using a simple API call. Currently only accessible locally, thus requiring local installation.

<details>
	<summary><h2>3.1 API Call</h2></summary>
	
```note
http://localhost:{port}/isbn/{isbn sequence}
```

### Parameters
- *isbn sequence* (required!) a sequence of 10 or 13 digits representating isbn strings

#### 3.1.2 Example API Call
The api currently does not support the 9-digit format, thus 0 should be added infront of a 9-digit isbn to ensure compatibility.

**ISBN 10**
```note
http://localhost:9090/isbn/9185057819
```

**ISBN 13**
```note
http://localhost:9090/isbn/9783161484100
```


### 3.2 Example API response
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

#### 3.2.1 Field response description
- *value* reduced representation of input value
- *type* the isbn type that was identified by the api, expected values are ISBN10 and ISBN13
- *parts* isbn parts of the digit sequence as specified by international isbn agency

</details>
