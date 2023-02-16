package io.tashtabash.parser.onlinesim.number;

import io.tashtabash.parser.onlinesim.number.dao.FreeNumbers;
import io.tashtabash.parser.onlinesim.number.loader.FreeNumberLoader;
import io.tashtabash.parser.onlinesim.number.loader.FreeNumbersParser;
import io.tashtabash.parser.onlinesim.number.loader.ParseException;
import io.tashtabash.parser.onlinesim.number.renderer.ConsoleFreeNumberRenderer;
import io.tashtabash.parser.onlinesim.number.renderer.JavaFxFreeNumberRenderer;

import java.io.IOException;


public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        FreeNumberLoader loader = new FreeNumberLoader(new FreeNumbersParser());
        FreeNumbers freeNumbers = loader.load();

        new ConsoleFreeNumberRenderer().render(freeNumbers);

        new JavaFxFreeNumberRenderer().render(freeNumbers);
    }
}
