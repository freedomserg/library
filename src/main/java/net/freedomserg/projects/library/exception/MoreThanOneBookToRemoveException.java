package net.freedomserg.projects.library.exception;

public class MoreThanOneBookToRemoveException extends LibraryException {

    private String bookName;

    public MoreThanOneBookToRemoveException(String message, String bookName) {
        super(message);
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }
}
