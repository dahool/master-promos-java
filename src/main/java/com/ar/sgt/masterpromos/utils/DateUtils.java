package com.ar.sgt.masterpromos.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("America/Argentina/Buenos_Aires");
	
	private DateUtils() {}
	
	
	public static Date getCurrent() {
		Calendar calendar = Calendar.getInstance(TIME_ZONE);
		return calendar.getTime();
	}
	
}
