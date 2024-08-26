package com.training.book_store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void bookSerializationTest() throws IOException {
        Book book = new Book(1L,"book", "author", "isbn1", "genre1", "publisher",
                100L, 100.0);

        assertThat(json.write(book)).isStrictlyEqualToJson("single.json");

        assertThat(json.write(book)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(book)).extractingJsonPathNumberValue("@.id").isEqualTo(1);

        assertThat(json.write(book)).hasJsonPathStringValue("@.title");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.title").isEqualTo("book");

        assertThat(json.write(book)).hasJsonPathStringValue("@.author");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.author").isEqualTo("author");

        assertThat(json.write(book)).hasJsonPathStringValue("@.isbn");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.isbn").isEqualTo("isbn1");

        assertThat(json.write(book)).hasJsonPathStringValue("@.genre");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.genre").isEqualTo("genre1");

        assertThat(json.write(book)).hasJsonPathStringValue("@.publisher");
        assertThat(json.write(book)).extractingJsonPathStringValue("@.publisher").isEqualTo("publisher");

        assertThat(json.write(book)).hasJsonPathNumberValue("@.amount");
        assertThat(json.write(book)).extractingJsonPathNumberValue("@.amount").isEqualTo(100);

        assertThat(json.write(book)).hasJsonPathNumberValue("@.price");
        assertThat(json.write(book)).extractingJsonPathNumberValue("@.price").isEqualTo(100.0);
    }

    @Test
    public void bookDeserializationTest() throws IOException {
        String book = """
                {
                  "id": 1,
                  "title": "book",
                  "author": "author",
                  "isbn": "isbn1",
                  "genre": "genre1",
                  "publisher": "publisher",
                  "amount": 100,
                  "price": 100.0
                }
                """;

        assertThat(json.parse(book)).isEqualTo(new Book(1L,"book", "author", "isbn1", "genre1", "publisher",
                100L, 100.0));
        assertThat(json.parseObject(book).id()).isEqualTo(1);
        assertThat(json.parseObject(book).title()).isEqualTo("book");
        assertThat(json.parseObject(book).author()).isEqualTo("author");
        assertThat(json.parseObject(book).isbn()).isEqualTo("isbn1");
        assertThat(json.parseObject(book).genre()).isEqualTo("genre1");
        assertThat(json.parseObject(book).publisher()).isEqualTo("publisher");
        assertThat(json.parseObject(book).amount()).isEqualTo(100);
        assertThat(json.parseObject(book).price()).isEqualTo(100.0);
    }
}
