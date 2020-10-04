package com.github.testyourcodenow;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.junit.Test;

public class TimeLapsedTest {
	@Test
	public void justNowLongNotationFromTimestamp() {
		assertEquals("just now long notation", TimeLapsed.fromTimestamp(Instant.now().toEpochMilli(), Notation.LONG),
				"just now");
	}

	@Test
	public void justNowLongNotationFromDateString() {
		assertEquals("just now long notation", TimeLapsed.fromDateString(Instant.now().toString(), Notation.LONG),
				"just now");
	}

	@Test
	public void justNowTwitterNotationFromTimestamp() {
		assertEquals("just now twitter notation",
				TimeLapsed.fromTimestamp(Instant.now().toEpochMilli(), Notation.TWITTER), "n");
	}

	@Test
	public void justNowShortNotationFromTimestamp() {
		assertEquals("just now short notation", TimeLapsed.fromTimestamp(Instant.now().toEpochMilli(), Notation.SHORT),
				"now");
	}

	@Test
	public void oneMinuteAgoLongNotationFromTimestamp() {
		assertEquals("1 minute ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60).toEpochMilli(), Notation.LONG),
				"1 minute ago");
	}

	@Test
	public void oneMinuteAgoLongNotationFromDateString() {
		assertEquals("1 minute ago long notation",
				TimeLapsed.fromDateString(Instant.now().minusMillis(1000 * 60).toString(), Notation.LONG),
				"1 minute ago");
	}

	@Test
	public void oneMinuteAgoTwitterNotationFromTimestamp() {
		assertEquals("1 minute ago twitter notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60).toEpochMilli(), Notation.TWITTER), "1m");
	}

	@Test
	public void oneMinuteAgoShortNotationFromTimestamp() {
		assertEquals("1 minute ago short notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60).toEpochMilli(), Notation.SHORT), "1min");
	}

	@Test
	public void seventeenMinuteAgoLongNotationFromTimestamp() {
		assertEquals("17 minutes ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(17000 * 60).toEpochMilli(), Notation.LONG),
				"17 minutes ago");
	}

	@Test
	public void seventeenMinuteAgoTwitterNotationFromTimestamp() {
		assertEquals("17 minutes ago twitter notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(17000 * 60).toEpochMilli(), Notation.TWITTER),
				"17m");
	}

	@Test
	public void seventeenMinuteAgoShortNotationFromTimestamp() {
		assertEquals("17 minutes ago short notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(17000 * 60).toEpochMilli(), Notation.SHORT),
				"17mins");
	}

	@Test
	public void oneWeekAgoShortNotationFromTimestamp() {
		assertEquals("1 week ago short notation", TimeLapsed.fromTimestamp(
				Instant.now().minusMillis(1000 * 60 * 60 * 24 * 7).toEpochMilli(), Notation.SHORT), "1wk");
	}

	@Test
	public void oneDayAgoShortNotationFromTimestamp() {
		assertEquals("1 day ago short notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60 * 60 * 24).toEpochMilli(), Notation.SHORT),
				"1dy");
	}

	@Test
	public void oneDayAgoTwitterNotationFromTimestamp() {
		assertEquals("1 day ago twitter notation", TimeLapsed
				.fromTimestamp(Instant.now().minusMillis(1000 * 60 * 60 * 24).toEpochMilli(), Notation.TWITTER), "1d");
	}

	@Test
	public void oneDayAgoLongNotationFromTimestamp() throws Exception {
		assertEquals("1 day ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60 * 60 * 24).toEpochMilli(), Notation.LONG),
				"yesterday");
	}

	@Test
	public void sixDaysAgoLongNotationFromTimestamp() {
		assertEquals("6 days ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(6000 * 60 * 60 * 24).toEpochMilli(), Notation.LONG),
				"6 days ago");
	}

	@Test
	public void FortyTwoMinutesAgoLongNotationFromTimestamp() {
		assertEquals("42 minutes ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(42000 * 60).toEpochMilli(), Notation.LONG),
				"42 minutes ago");
	}

	@Test
	public void OneHourAgoLongNotationFromTimestamp() {
		assertEquals("1 hour ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(1000 * 60 * 60).toEpochMilli(), Notation.LONG),
				"1 hour ago");
	}

	@Test
	public void nineteenHoursAgoLongNotationFromTimestamp() {
		assertEquals("19 hours ago long notation",
				TimeLapsed.fromTimestamp(Instant.now().minusMillis(19000 * 60 * 60).toEpochMilli(), Notation.LONG),
				"19 hours ago");
	}

	@Test
	public void threeWeeksAgoLongNotationFromTimestamp() {
		assertEquals("3 weeks ago long notation", TimeLapsed.fromTimestamp(
				Instant.now().minusMillis(3000 * 60 * 60 * 24 * 7).toEpochMilli(), Notation.LONG), "3 weeks ago");
	}

	@Test(expected = DateTimeParseException.class)
	public void throwsDateTimeParseExceptionWhenPassedInvalidDatetimeString() {
		assertEquals("will throw a DateTimeParseException exception",
				TimeLapsed.fromDateString("x2017-11-07 15:58:42.125836", Notation.LONG));
	}
}
