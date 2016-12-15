package net.freedomserg.projects.library.exception;

public class MoreThanOneBookToEditException extends LibraryException {

    private String bookName;

    public MoreThanOneBookToEditException(String message, String bookName) {
        super(message);
        this.bookName = bookName;
    }

    public String getBookName() {
        return bookName;
    }
}
