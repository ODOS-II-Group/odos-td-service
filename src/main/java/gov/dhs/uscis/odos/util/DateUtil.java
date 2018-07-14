package gov.dhs.uscis.odos.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger log  = LoggerFactory.getLogger(DateUtil.class);
	
	private static final String DATE_FORMAT  = "yyyy-MM-dd HH:mm";
	
	public static Date convertDateString(String dateStr) {
		Date dateValue = null;
		try {
			dateValue = DateUtils.parseDate(dateStr, DATE_FORMAT);
		}
		catch(ParseException e) {
			log.error("Error parsing date value " + dateStr, e);
			throw new RuntimeException(e);
		}
		return dateValue;
	}
	
	public static String convertDateValue(Date dateValue) {
		Instant instant = dateValue.toInstant();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		return localDateTime.toString();
	}
}
