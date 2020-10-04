package com.github.testyourcodenow;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.lang.Math;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.lang.StringBuilder;

public class TimeLapsed {
	private static final long SECONDS_PER_MINUTE = 60;
	private static final long SECONDS_PER_HOUR = 3_600;
	private static final long SECONDS_PER_DAY = 86_400;
	private static final long SECONDS_PER_WEEK = 60_4800;
	private static final long SECONDS_PER_MONTH = 2_629_800;
	private static final int CURRENT_YEAR = LocalDateTime.now().getYear();

	private static final Map<String, String[]> FUZZY_TIME_NOTATION_STRINGS = new HashMap<>();
	private static final String[] MONTHS_SHORT = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };
	private static final String[] MONTHS_LONG = { "January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December" };

	/**
	 * Obtains an instance of {@code String} fuzzy time notation
	 *
	 * @param milliseconds the number of milliseconds from 1970-01-01T00:00:00Z, not
	 *                     null
	 * @param notation     a {@code Notation}, not null
	 * @return a String containing the fuzzy time notation, not null
	 * @throws NullPointerException if the milliseconds is null
	 * @throws NullPointerException if the notation is null
	 */
	public static String fromTimestamp(long milliseconds, Notation notation) {
		Objects.requireNonNull(milliseconds, "milliseconds");
		Objects.requireNonNull(notation, "notation");

		long secondsLapsed = getSecondsLapsed(milliseconds);

		return deductSeconds(secondsLapsed, milliseconds, notation);
	}

	/**
	 * Obtains an instance of {@code String} fuzzy time notation
	 *
	 * @param dateTimeString an ISO-8601 representation of datetime, not null
	 * @param notation       a {@code Notation}, not null
	 * @return a String containing the fuzzy time notation, not null
	 * @throws NullPointerException   if the dateTimeString is null
	 * @throws NullPointerException   if the notation is null
	 * @throws DateTimeParseException if the dateTimeString cannot be parsed
	 */
	public static String fromDateString(String dateTimeString, Notation notation) {
		Objects.requireNonNull(dateTimeString, "dateTimeString");
		Objects.requireNonNull(notation, "notation");

		long milliseconds = Instant.parse(dateTimeString).toEpochMilli();
		long secondsLapsed = getSecondsLapsed(milliseconds);

		return deductSeconds(secondsLapsed, milliseconds, notation);
	}

	private static long getSecondsLapsed(long milliseconds) {
		return Instant.now().minusMillis(milliseconds).getEpochSecond();
	}

	private static void initNotations() {
		String[] now = { "just now", "now", "n" };
		String[] min = { " minute ago", "min", "m" };
		String[] mins = { " minutes ago", "mins", "m" };
		String[] hour = { " hour ago", "hr", "h" };
		String[] hours = { " hours ago", "hrs", "h" };
		String[] day = { "yesterday", "dy", "d" };
		String[] days = { " days ago", "dys", "d" };
		String[] week = { " week ago", "wk", "w" };
		String[] weeks = { " weeks ago", "wks", "w" };

		FUZZY_TIME_NOTATION_STRINGS.put("now", now);
		FUZZY_TIME_NOTATION_STRINGS.put("min", min);
		FUZZY_TIME_NOTATION_STRINGS.put("mins", mins);
		FUZZY_TIME_NOTATION_STRINGS.put("hour", hour);
		FUZZY_TIME_NOTATION_STRINGS.put("hours", hours);
		FUZZY_TIME_NOTATION_STRINGS.put("day", day);
		FUZZY_TIME_NOTATION_STRINGS.put("days", days);
		FUZZY_TIME_NOTATION_STRINGS.put("week", week);
		FUZZY_TIME_NOTATION_STRINGS.put("weeks", weeks);
	}

	private static long floorTimeCalc(long duration) {
		return (long) Math.floor(duration);
	}

	private static String deductSecondsMonthly(long timeStamp, Notation notation) {
		LocalDateTime dateTime = new Timestamp(timeStamp).toLocalDateTime();
		StringBuilder fuzzyTime = new StringBuilder();
		String[] monthStr;

		if (notation == Notation.TWITTER || notation == Notation.SHORT) {
			monthStr = MONTHS_SHORT;
		} else {
			monthStr = MONTHS_LONG;
		}

		int theYear = dateTime.getYear();
		int theDate = dateTime.getDayOfMonth();
		int theMonth = dateTime.getMonthValue();

		if (theYear < CURRENT_YEAR) {
			fuzzyTime.append(theDate).append(" ").append(monthStr[theMonth - 1]).append(", ").append(theYear);

			return fuzzyTime.toString();
		}

		fuzzyTime.append(theDate).append(" ").append(monthStr[theMonth - 1]);
		return fuzzyTime.toString();
	}

	private static String deductSeconds(long secondsLapsed, long milliseconds, Notation notation) {
		int timestrings;
		if (notation == Notation.TWITTER) {
			timestrings = 2;
		} else if (notation == Notation.SHORT) {
			timestrings = 1;
		} else {
			timestrings = 0;
		}

		initNotations();

		StringBuilder fuzzyTime = new StringBuilder();
		long remainder;
		if (secondsLapsed < SECONDS_PER_MINUTE) {
			fuzzyTime.append(FUZZY_TIME_NOTATION_STRINGS.get("now")[timestrings]);

			return fuzzyTime.toString();
		}

		if (secondsLapsed >= SECONDS_PER_MINUTE && secondsLapsed < SECONDS_PER_HOUR) {
			remainder = floorTimeCalc(secondsLapsed / SECONDS_PER_MINUTE);
			if (remainder == 1) {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("min")[timestrings]);
			} else {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("mins")[timestrings]);
			}

			return fuzzyTime.toString();
		}

		if (secondsLapsed >= SECONDS_PER_HOUR && secondsLapsed < SECONDS_PER_DAY) {
			remainder = floorTimeCalc(secondsLapsed / SECONDS_PER_HOUR);
			if (remainder == 1) {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("hour")[timestrings]);
			} else {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("hours")[timestrings]);
			}

			return fuzzyTime.toString();
		}

		if (secondsLapsed >= SECONDS_PER_DAY && secondsLapsed < SECONDS_PER_WEEK) {
			remainder = floorTimeCalc(secondsLapsed / SECONDS_PER_DAY);
			if (remainder == 1) {
				if (timestrings == 0)
					fuzzyTime.append(FUZZY_TIME_NOTATION_STRINGS.get("day")[timestrings]);
				else
					fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("day")[timestrings]);
			} else {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("days")[timestrings]);
			}

			return fuzzyTime.toString();
		}

		if (secondsLapsed >= SECONDS_PER_WEEK && secondsLapsed < SECONDS_PER_MONTH) {
			remainder = floorTimeCalc(secondsLapsed / SECONDS_PER_WEEK);
			if (remainder == 1) {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("week")[timestrings]);
			} else {
				fuzzyTime.append(remainder + FUZZY_TIME_NOTATION_STRINGS.get("weeks")[timestrings]);
			}

			return fuzzyTime.toString();
		}

		fuzzyTime.append(deductSecondsMonthly(milliseconds, notation));
		return fuzzyTime.toString();
	}
}
