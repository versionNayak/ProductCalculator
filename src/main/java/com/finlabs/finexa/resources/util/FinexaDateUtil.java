package com.finlabs.finexa.resources.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public class FinexaDateUtil {

	public static String getLastDayOfTheMonth(String date) {
		String lastDayOfTheMonth = "";

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			java.util.Date dt = formatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);

			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DATE, -1);

			java.util.Date lastDay = calendar.getTime();

			lastDayOfTheMonth = formatter.format(lastDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastDayOfTheMonth;
	}

	public static String getFiscalYear(Date refDate) {
		Calendar cRefDate = Calendar.getInstance();
		int y1 = 0;
		int y2 = 0;
		cRefDate.setTime(refDate);
		int varMonth = cRefDate.get(Calendar.MONTH);
		int varYear = cRefDate.get(Calendar.YEAR);
		y1 = varMonth < 3 ? varYear - 1 : varYear;
		y2 = y1 + 1;
		String fiscalYear = Integer.toString(y1) + "-" + Integer.toString(y2);
		return fiscalYear;
	}

	public long[] getYearCountByDay(Date startDate, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(0);
		cal2.setTime(startDate);
		long calculateddays = FinexaDateUtil.getDays(cal2.getTime(), cal.getTime());
		cal2.add(Calendar.DAY_OF_YEAR, days);
		LocalDate dateTo = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
		LocalDate dateFrom = LocalDate.of(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH) + 1,
				cal2.get(Calendar.DATE));
		Period intervalPeriod = Period.between(dateTo, dateFrom);
		long years = intervalPeriod.getYears();
		long months = intervalPeriod.getMonths();
		long remainingDays = intervalPeriod.getDays();
		return new long[] { years, months, remainingDays };
	}

	public static Boolean checkDateForInterest(String dateRange, Date currentDate) {
		String stringDate = dateRange;// "1st apr 2017 - 31st mar 2018";
		String firstDateString[] = stringDate.split(" - ");
		String firstDate = firstDateString[0].replaceAll("st", "").replaceAll(" ", "-");
		String endDate = firstDateString[1].replaceAll("st", "").replaceAll("th", "").replaceAll(" ", "-");
		SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");
		Date dtLastDayOfMonth = currentDate;
		try {
			if (dtLastDayOfMonth.after(sdfOutput.parse(firstDate))
					&& (dtLastDayOfMonth.before(sdfOutput.parse(endDate)))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static long getDays(Date endDate, Date startDate) {
		long diff = endDate.getTime() - startDate.getTime();
		diff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return diff;

	}

	public static int getMonths(Calendar endDate, Calendar startDate) {
		LocalDate dateTo = LocalDate.of(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH) + 1,
				endDate.get(Calendar.DATE));
		LocalDate dateFrom = LocalDate.of(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH) + 1,
				startDate.get(Calendar.DATE));
		Period intervalPeriod = Period.between(dateTo, dateFrom);
		int months = 0;
		if (intervalPeriod.getYears() > 0 && intervalPeriod.getMonths() < 1) {
			months = intervalPeriod.getYears() * 12;
		} else {
			months = intervalPeriod.getMonths();
		}
		int d = intervalPeriod.getDays();
		if (d > 0) {
			months ++;
		}
		return months;
	}

	public static int getYears(Calendar endDate, Calendar startDate) {
		LocalDate dateTo = LocalDate.of(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH) + 1,
				endDate.get(Calendar.DATE));
		LocalDate dateFrom = LocalDate.of(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH) + 1,
				startDate.get(Calendar.DATE));
		Period intervalPeriod = Period.between(dateTo, dateFrom);
		int years = intervalPeriod.getYears();
		return years;
	}

	public static Date getDateFromBirthDateToGivenAge(Date birthDate, int givenAge) {
		DateTime dateTime = new DateTime(birthDate);
		dateTime = dateTime.plusYears(givenAge);
		return dateTime.toDate();
	}

}
