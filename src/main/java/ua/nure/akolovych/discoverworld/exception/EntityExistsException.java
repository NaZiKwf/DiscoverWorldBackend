package ua.nure.akolovych.discoverworld.exception;

public class EntityExistsException extends RuntimeException{
    public EntityExistsException(String message) {
        super(message);
    }
}
