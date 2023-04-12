package es.netmind.banana_invoices.util;

import org.springframework.batch.item.file.transform.FieldSet;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class BatchUtil {
    public static final String FORWARD_SLASH = "/";
    public static final String DOT = ".";
    public static final String HYPHEN = "-";
    public static final String UNDER_SCORE = "_";
    public static final String S3_PROTOCOL_PREFIX = "s3://";
    public static final String ZIP_FILE_EXTENSION = "zip";
    public static final String XML_FILE_EXTENSION = "xml";
    public static final String WILDCARD_CHARACTER = "*";
    public static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";
    public static final String ANY_OTHER_EXIT_STATUS = "*";
    public static final String ZERO = "0";
    public static final String SPACE = " ";
    public static final String DOUBLE_QUOTE = "\"";
    public static final String COMMA = ",";

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static boolean isValidField(FieldSet fieldSet, String fieldName) {
        if (fieldSet != null && fieldSet.getProperties().containsKey(fieldName)) {
            String val = fieldSet.readString(fieldName);
            if (val!=null && !val.trim().equals("")) return true;
            else return false;
        } else return false;
    }
}


