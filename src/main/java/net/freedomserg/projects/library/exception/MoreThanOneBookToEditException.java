package net.freedomserg.projects.library.exception;

public class MoreThanOneBookToEditException extends LibraryException {

    private String bookName;
    private String newBookName;

    public MoreThanOneBookToEditException(String message, String bookName, String newBookName) {
        super(message);
        this.bookName = bookName;
        this.newBookName = newBookName;
    }

    public String getBookName() {
        return bookName;
    }

    public String getNewBookName() {
        return newBookName;
    }
}
