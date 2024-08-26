package com.training.book_store;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookStoreApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void returnBookById() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/id/99", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		Number id = context.read("$.id");
		assertThat(id).isNotNull();
		assertThat(id).isEqualTo(99);

		String title = context.read("$.title");
		assertThat(title).isNotNull();
		assertThat(title).isEqualTo("book");

		String author = context.read("$.author");
		assertThat(author).isNotNull();
		assertThat(author).isEqualTo("author");

		String isbn = context.read("$.isbn");
		assertThat(isbn).isNotNull();
		assertThat(isbn).isEqualTo("isbn1");

		String genre = context.read("$.genre");
		assertThat(genre).isNotNull();
		assertThat(genre).isEqualTo("genre1");

		String publisher = context.read("$.publisher");
		assertThat(publisher).isNotNull();
		assertThat(publisher).isEqualTo("publisher");

		Number amount = context.read("$.amount");
		assertThat(amount).isNotNull();
		assertThat(amount).isEqualTo(100);

		Double price = context.read("$.price");
		assertThat(price).isNotNull();
		assertThat(price).isEqualTo(100.0);
	}

	@Test
	public void invalidId() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/id/-1", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	void returnBookByIsbn() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/isbn/isbn1", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		Number id = context.read("$.id");
		assertThat(id).isNotNull();
		assertThat(id).isEqualTo(99);

		String title = context.read("$.title");
		assertThat(title).isNotNull();
		assertThat(title).isEqualTo("book");

		String author = context.read("$.author");
		assertThat(author).isNotNull();
		assertThat(author).isEqualTo("author");

		String isbn = context.read("$.isbn");
		assertThat(isbn).isNotNull();
		assertThat(isbn).isEqualTo("isbn1");

		String genre = context.read("$.genre");
		assertThat(genre).isNotNull();
		assertThat(genre).isEqualTo("genre1");

		String publisher = context.read("$.publisher");
		assertThat(publisher).isNotNull();
		assertThat(publisher).isEqualTo("publisher");

		Number amount = context.read("$.amount");
		assertThat(amount).isNotNull();
		assertThat(amount).isEqualTo(100);

		Double price = context.read("$.price");
		assertThat(price).isNotNull();
		assertThat(price).isEqualTo(100.0);
	}

	@Test
	public void invalidIsbn() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/isbn/isbn", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	void returnAllBooks() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		int booksCount = context.read("$.length()");

		assertThat(booksCount).isEqualTo(3);

		JSONArray ids = context.read("$..id");
		assertThat(ids).isNotNull();
		assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

		JSONArray titles = context.read("$..title");
		assertThat(titles).isNotNull();
		assertThat(titles).containsExactlyInAnyOrder("book", "book1", "book3");

		JSONArray authors = context.read("$..author");
		assertThat(authors).isNotNull();
		assertThat(authors).containsExactlyInAnyOrder("author", "author1", "author");

		JSONArray genres = context.read("$..genre");
		assertThat(genres).isNotNull();
		assertThat(genres).containsExactlyInAnyOrder("genre0", "genre1", "genre2");

		JSONArray isbns = context.read("$..isbn");
		assertThat(isbns).isNotNull();
		assertThat(isbns).containsExactlyInAnyOrder("isbn1", "isbn5", "isbn10");

		JSONArray publishers = context.read("$..publisher");
		assertThat(publishers).isNotNull();
		assertThat(publishers).containsExactlyInAnyOrder("publisher", "publisher1", "publisher");

		JSONArray amounts = context.read("$..amount");
		assertThat(amounts).isNotNull();
		assertThat(amounts).containsExactlyInAnyOrder(100, 1023, 1);

		JSONArray prices = context.read("$..price");
		assertThat(prices).isNotNull();
		assertThat(prices).containsExactlyInAnyOrder(100.0, 10.0, 200.0);
	}

	@Test
	void returnBooksByTitle() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/title/book", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		int booksCount = context.read("$.length()");

		assertThat(booksCount).isEqualTo(1);

		JSONArray ids = context.read("$..id");
		assertThat(ids).isNotNull();
		assertThat(ids).containsExactlyInAnyOrder(99);

		JSONArray titles = context.read("$..title");
		assertThat(titles).isNotNull();
		assertThat(titles).containsExactlyInAnyOrder("book");

		JSONArray authors = context.read("$..author");
		assertThat(authors).isNotNull();
		assertThat(authors).containsExactlyInAnyOrder("author");

		JSONArray genres = context.read("$..genre");
		assertThat(genres).isNotNull();
		assertThat(genres).containsExactlyInAnyOrder("genre1");

		JSONArray isbns = context.read("$..isbn");
		assertThat(isbns).isNotNull();
		assertThat(isbns).containsExactlyInAnyOrder("isbn1");

		JSONArray publishers = context.read("$..publisher");
		assertThat(publishers).isNotNull();
		assertThat(publishers).containsExactlyInAnyOrder("publisher");

		JSONArray amounts = context.read("$..amount");
		assertThat(amounts).isNotNull();
		assertThat(amounts).containsExactlyInAnyOrder(100);

		JSONArray prices = context.read("$..price");
		assertThat(prices).isNotNull();
		assertThat(prices).containsExactlyInAnyOrder(100.0);
	}

	@Test
	void returnBooksByAuthor() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/author/author", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		int booksCount = context.read("$.length()");

		assertThat(booksCount).isEqualTo(2);

		JSONArray ids = context.read("$..id");
		assertThat(ids).isNotNull();
		assertThat(ids).containsExactlyInAnyOrder(99, 101);

		JSONArray titles = context.read("$..title");
		assertThat(titles).isNotNull();
		assertThat(titles).containsExactlyInAnyOrder("book", "book3");

		JSONArray authors = context.read("$..author");
		assertThat(authors).isNotNull();
		assertThat(authors).containsExactlyInAnyOrder("author", "author");

		JSONArray genres = context.read("$..genre");
		assertThat(genres).isNotNull();
		assertThat(genres).containsExactlyInAnyOrder("genre1", "genre2");

		JSONArray isbns = context.read("$..isbn");
		assertThat(isbns).isNotNull();
		assertThat(isbns).containsExactlyInAnyOrder("isbn1", "isbn10");

		JSONArray publishers = context.read("$..publisher");
		assertThat(publishers).isNotNull();
		assertThat(publishers).containsExactlyInAnyOrder("publisher", "publisher1");

		JSONArray amounts = context.read("$..amount");
		assertThat(amounts).isNotNull();
		assertThat(amounts).containsExactlyInAnyOrder(100, 1);

		JSONArray prices = context.read("$..price");
		assertThat(prices).isNotNull();
		assertThat(prices).containsExactlyInAnyOrder(100.0, 200.0);
	}

	@Test
	void returnBooksByGenre() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/genre/genre2", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		int booksCount = context.read("$.length()");

		assertThat(booksCount).isEqualTo(1);

		JSONArray ids = context.read("$..id");
		assertThat(ids).isNotNull();
		assertThat(ids).containsExactlyInAnyOrder(101);

		JSONArray titles = context.read("$..title");
		assertThat(titles).isNotNull();
		assertThat(titles).containsExactlyInAnyOrder("book3");

		JSONArray authors = context.read("$..author");
		assertThat(authors).isNotNull();
		assertThat(authors).containsExactlyInAnyOrder("author");

		JSONArray genres = context.read("$..genre");
		assertThat(genres).isNotNull();
		assertThat(genres).containsExactlyInAnyOrder("genre2");

		JSONArray isbns = context.read("$..isbn");
		assertThat(isbns).isNotNull();
		assertThat(isbns).containsExactlyInAnyOrder("isbn10");

		JSONArray publishers = context.read("$..publisher");
		assertThat(publishers).isNotNull();
		assertThat(publishers).containsExactlyInAnyOrder("publisher1");

		JSONArray amounts = context.read("$..amount");
		assertThat(amounts).isNotNull();
		assertThat(amounts).containsExactlyInAnyOrder( 1);

		JSONArray prices = context.read("$..price");
		assertThat(prices).isNotNull();
		assertThat(prices).containsExactlyInAnyOrder(200.0);
	}

	@Test
	void returnBooksByPublisher() {
		ResponseEntity<String> response = restTemplate.getForEntity("/book-store/search/publisher/publisher", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext context = JsonPath.parse(response.getBody());

		assertThat(context).isNotNull();

		int booksCount = context.read("$.length()");

		assertThat(booksCount).isEqualTo(2);

		JSONArray ids = context.read("$..id");
		assertThat(ids).isNotNull();
		assertThat(ids).containsExactlyInAnyOrder(99, 100);

		JSONArray titles = context.read("$..title");
		assertThat(titles).isNotNull();
		assertThat(titles).containsExactlyInAnyOrder("book", "book1");

		JSONArray authors = context.read("$..author");
		assertThat(authors).isNotNull();
		assertThat(authors).containsExactlyInAnyOrder("author", "author1");

		JSONArray genres = context.read("$..genre");
		assertThat(genres).isNotNull();
		assertThat(genres).containsExactlyInAnyOrder("genre0", "genre1");

		JSONArray isbns = context.read("$..isbn");
		assertThat(isbns).isNotNull();
		assertThat(isbns).containsExactlyInAnyOrder("isbn1", "isbn5");

		JSONArray publishers = context.read("$..publisher");
		assertThat(publishers).isNotNull();
		assertThat(publishers).containsExactlyInAnyOrder("publisher", "publisher");

		JSONArray amounts = context.read("$..amount");
		assertThat(amounts).isNotNull();
		assertThat(amounts).containsExactlyInAnyOrder(100, 1023);

		JSONArray prices = context.read("$..price");
		assertThat(prices).isNotNull();
		assertThat(prices).containsExactlyInAnyOrder(100.0, 10.0);
	}
}