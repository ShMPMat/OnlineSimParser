package io.tashtabash.parser.onlinesim.number.renderer;

import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;
import io.tashtabash.parser.onlinesim.number.dao.PhoneNumber;

public class ConsoleFreeNumberRenderer implements FreeNumberRenderer {
    @Override
    public void render(FreeNumbers freeNumbers) {
        for (var entry: freeNumbers.getCountryToNumbers().entrySet()) {
            System.out.println(entry.getKey().getName() + ":");

            for (PhoneNumber number: entry.getValue()) {
                System.out.println("    " + number.getNumber());
            }
        }
    }
}
