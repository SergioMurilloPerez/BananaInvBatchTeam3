package es.netmind.banana_invoices.exceptions;

public class InvalidReciboException extends RuntimeException {
    private static final long serialVersionUID = 2L;

    public InvalidReciboException(String errorMessage) {
        super(errorMessage);
    }
}