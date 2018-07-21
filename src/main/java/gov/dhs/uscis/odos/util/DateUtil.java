package gov.dhs.uscis.odos.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger log = LoggerFactory.getLogger(DateUtil.class);

	private static final String DATE_FORMAT_WITH_TIME = "yyyy-MM-dd'T'HH:mm";

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public static Date convertDateString(String dateStr) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FORMAT);
		return fmt.parseDateTime(dateStr).toDate();

	}

	public static Date convertDateTimeString(String dateStr) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern(DATE_FORMAT_WITH_TIME);
		return fmt.parseDateTime(dateStr).toDate();

	}

	public static String convertDateValue(Date dateValue) {
		Instant instant = dateValue.toInstant();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return localDateTime.toString();
	}
}
