package es.netmind.banana_invoices.batch.reader;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import es.netmind.banana_invoices.models.Recibo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

import static es.netmind.banana_invoices.util.BatchUtil.*;

@Configuration
public class S3ReaderConfig {
    final static Logger logger = LoggerFactory.getLogger(S3ReaderConfig.class);
    @Value("${rawdata.s3bucket}")
    private String rawDataS3Bucket;

    @Value("${rawdata.s3object.prefix}")
    private String rawDataS3ObjectPrefix;

    @Value("${inputData.fileExtension}")
    private String inputDataFileExtension;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    @StepScope
    public SynchronizedItemStreamReader<Recibo> s3RecibosDataReader() {
        SynchronizedItemStreamReader synchronizedItemStreamReader = new SynchronizedItemStreamReader();
        List<Resource> resourceList = new ArrayList<>();

        ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                .withBucketName(rawDataS3Bucket)
                .withPrefix(rawDataS3ObjectPrefix);

        logger.info("listObjectsRequest::" + listObjectsRequest.getBucketName() + "::" + listObjectsRequest.getPrefix());

        ObjectListing sourceObjectsListing;
        do {
            sourceObjectsListing = amazonS3Client.listObjects(listObjectsRequest);
            logger.info("sourceObjectsListing::" + sourceObjectsListing.getObjectSummaries() + "::" + sourceObjectsListing.isTruncated());

            for (S3ObjectSummary sourceFile : sourceObjectsListing.getObjectSummaries()) {

                if (!(sourceFile.getSize() > 0) || (!sourceFile.getKey().endsWith(DOT.concat(inputDataFileExtension)))) {
                    // Skip if file is empty (or) file extension is not "csv"
                    continue;
                }

                logger.info("Reading file::" + sourceFile.getKey());
                resourceList.add(resourceLoader.getResource(S3_PROTOCOL_PREFIX.concat(rawDataS3Bucket).concat(FORWARD_SLASH).concat(sourceFile.getKey())));
            }
            listObjectsRequest.setMarker(sourceObjectsListing.getNextMarker());
        } while (sourceObjectsListing.isTruncated());

        Resource[] resources = resourceList.toArray(new Resource[resourceList.size()]);
        MultiResourceItemReader<Recibo> multiResourceItemReader = new MultiResourceItemReader<>();
        multiResourceItemReader.setName("recibo-multiResource-Reader");
        multiResourceItemReader.setResources(resources);
        multiResourceItemReader.setDelegate(reciboFileItemReader());
        synchronizedItemStreamReader.setDelegate(multiResourceItemReader);

        return synchronizedItemStreamReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Recibo> reciboFileItemReader() {
        // TODO: HERE IMPEMENT A FLAT ITEM WRITER
        FlatFileItemReader<Recibo> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(0);
        DefaultLineMapper<Recibo> movieDataLineMapper = new DefaultLineMapper();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{
                "id_remoto", "direccion_contacto", "direccion_envio", "fecha_emision", "fecha_vencimiento", "nombre_contacto", "propietario", "nombre_producto", "cantidad", "precio_unitario", "base_imponible", "impuestos", "total"
        });
        movieDataLineMapper.setFieldSetMapper(reciboFieldSetMapper());
        movieDataLineMapper.setLineTokenizer(tokenizer);
        reader.setLineMapper(movieDataLineMapper);
        reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());

        return reader;
    }

    @Bean
    public FieldSetMapper<Recibo> reciboFieldSetMapper() {
        return new ReciboFieldSetMapper();
    }


}


