package edu.spbstu.taxi.Exceptions;

public class HaveNotUserEx extends Throwable {
    public HaveNotUserEx(){
        super("Have not user with this login");
    }
}
