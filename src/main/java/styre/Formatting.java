package styre;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Duration;

/**
 * Format some stuff for rendering.
 *
 * Groovy templates give a weird error when the argument of these methods is a {@code Double}.  Even though it passes in
 * a {@code Double}, it can't find these methods without their being able to accept {@code Object}.
 * @author steven
 */
public class Formatting {

	public static String formatPercentage(Object o) {
		double d = getDoubleFromObject(o);
		return new DecimalFormat("#.###").format(100.0 * d);
	}

	public static String formatTime(Object o) {
		return Duration.ofSeconds(Math.round(getDoubleFromObject(o))).toString();
	}

	private static double getDoubleFromObject(Object o) {
		double d;
		if (o instanceof BigDecimal) {
			d = ((BigDecimal) o).doubleValue();
		} else if (o instanceof Double) {
			d = (double) o;
		} else {
			throw new RuntimeException("Unknown number: " + o);
		}
		return d;
	}
}
