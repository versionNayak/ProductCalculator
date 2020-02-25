package com.finlabs.finexa.resources.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class FinanceUtil {
	private FinanceUtil() {
		// no instances of this class
	}

	public static double pmt(double r, double n, double p, double f, boolean t) {
		double retval = 0;
		if (r == 0) {
			retval = -1 * (f + p) / n;
		} else {
			double r1 = r + 1;
			retval = (f + p * Math.pow(r1, n)) * r / ((t ? r1 : 1) * (1 - Math.pow(r1, n)));
		}
		return retval;
	}

	public static double roundUpAmount(double amount) {
		double roundUpAmount = Math.round((amount * 100)) / 100.0;
		return roundUpAmount;
	}

	public static double YEARFRAC(Date startDate, Date maturityDate, int basis) {
		long diff = maturityDate.getTime() - startDate.getTime();

		long daysTotal = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		double interestReceived = 0.0;
		if (basis == 1) {
			interestReceived = (daysTotal / (double) 365.3310);
		}

		return interestReceived;
	}

}
