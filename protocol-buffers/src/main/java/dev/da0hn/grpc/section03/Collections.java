package dev.da0hn.grpc.section03;

import dev.da0hn.grpc.proto.models.section03.Book;
import dev.da0hn.grpc.proto.models.section03.Library;
import org.slf4j.Logger;

public class Collections {

  private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Collections.class);

  public static void main(String[] args) {

    final var book1 = Book.newBuilder()
      .setTitle("Harry Potter - part 1")
      .setAuthor("J. K. Rowling")
      .setPublicationYear(1997)
      .build();
    final var book2 = book1.toBuilder().setTitle("Harry Potter - part 2").setPublicationYear(1998).build();
    final var book3 = book1.toBuilder().setTitle("Harry Potter - part 3").setPublicationYear(1999).build();

    final var library = Library.newBuilder()
      .setName("My Library")
      .addBooks(book1)
      .addBooks(book2)
      .addBooks(book3)
      .build();
    LOGGER.info("Library: {}", library);
  }

}
