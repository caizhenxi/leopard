package io.leopard.lang.datatype;

import java.util.Calendar;
import java.util.Date;

/**
 * 月范围
 * 
 * @author 谭海潮
 *
 */
public class DayRange extends TimeRange {

	public DayRange() {
		this(0);
	}

	public DayRange(int dayNum) {
		this(System.currentTimeMillis(), dayNum);
	}

	public DayRange(Date time) {
		this(time, 0);
	}

	public DayRange(Date time, int dayNum) {
		this(time.getTime(), dayNum);
	}

	public DayRange(long time, int dayNum) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);

		cal.add(Calendar.DATE, dayNum);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startTime = new Date(cal.getTimeInMillis());

		// System.err.println("time:" + time + " startTime:" + startTime + " dayNum:" + dayNum);
		Date endTime = null;
		{
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endTime = new Date(cal.getTimeInMillis());
		}
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}

}
