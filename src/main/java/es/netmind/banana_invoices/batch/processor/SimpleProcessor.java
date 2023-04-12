package es.netmind.banana_invoices.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class SimpleProcessor implements ItemProcessor<String,String> {
    final static Logger logger = LoggerFactory.getLogger(SimpleProcessor.class);

    @Override
    public String process(String text) throws Exception {
        logger.info("SimpleReader process()...:"+text.toString());
        return text.toUpperCase();
    }
}
