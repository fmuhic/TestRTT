package util;

import abstraction.Printable;

public class Printer implements Printable {
    @Override
    public void print(String input) {
        System.out.println(input);
    }
}
