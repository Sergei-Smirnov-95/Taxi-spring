package edu.spbstu.taxi.Exceptions;

public class DBConnectionException extends Exception {
    public DBConnectionException() {
        super("Unable to connect to the database");
    }
}
