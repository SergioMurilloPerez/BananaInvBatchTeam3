package es.netmind.banana_invoices.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SimpleWriter implements ItemWriter<String> {
    final static Logger logger = LoggerFactory.getLogger(SimpleWriter.class);

    @Override
    public void write(List<? extends String> list) throws Exception {
        logger.info("SimpleWriter write()....:" + list.size());
        for (String item : list) System.out.printf("\t ...writing: %s\n", item);
    }
}
