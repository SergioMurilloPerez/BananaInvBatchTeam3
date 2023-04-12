package es.netmind.banana_invoices.batch.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class SimpleReader implements ItemReader<String> {
    final static Logger logger = LoggerFactory.getLogger(SimpleReader.class);

    private final static String[] texts = new String[]{"Hello", "world", "!!!"};
    private static int count = 0;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (count < texts.length) {
            logger.info("SimpleReader read()...:" + texts[count]);
            return texts[count++];
        } else return null;
    }
}
