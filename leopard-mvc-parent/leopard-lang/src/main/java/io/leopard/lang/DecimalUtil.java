package io.leopard.lang;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 小数运算工具类.
 * 
 * @author 谭海潮
 *
 */
public class DecimalUtil {

	/**
	 * 判断是否2位小数点.
	 * 
	 * @param num
	 */
	public static void isScale(double num) {
		int count = count(num);
		if (count > 2) {
			throw new IllegalArgumentException("小数点位数不能超过2位[" + count + "].");
		}
	}

	/**
	 * 判断是否4位小数点.
	 * 
	 * @param num
	 */
	public static void isScale4(double num) {
		int count = count(num);
		if (count > 4) {
			throw new IllegalArgumentException("小数点位数不能超过4位[" + count + "].");
		}
	}

	/**
	 * 获取小数点位数
	 * 
	 * @param num
	 */
	public static int count(double num) {
		if ((num - 1) > 99999999999D) {// 999亿， 小数点只能计算到5位
			throw new IllegalArgumentException("超过了99999999999(999亿)，不能正确计算小数点位数[" + num + "].");
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(5);
		String s = nf.format(num);
		int index = s.indexOf(".");
		if (index <= 0) {
			return 0;
		}
		int count = s.length() - index - 1;
		return count;
	}

	// /**
	// * 获取小数点位数
	// *
	// * @param num
	// */
	// public static int count2(double num) {
	// long num2 = (long) num;
	// BigDecimal b1 = new BigDecimal(Double.toString(num));
	// BigDecimal b2 = new BigDecimal(Long.toString(num2));
	// double num3 = b1.subtract(b2).doubleValue();
	// System.out.println("num3:" + num3);
	// return 0;
	// }

	// /**
	// * 获取小数点位数
	// *
	// * @param num
	// */
	// public static int count2(double num) {
	// double abc1 = num * 1000000;
	// long abc2 = (long) abc1;
	//
	// long num2 = (long) num * 100000;
	// double num3 = num - num2;
	// System.out.println("num2:" + num2 + " num3:" + num3 + " abc1:" + abc1 + " abc2:" + abc2);
	// if ((num - 1) > 99999999999D) {// 999亿， 小数点只能计算到5位
	// // throw new IllegalArgumentException("超过了99999999999(999亿)，不能正确计算小数点位数[" + num + "].");
	// }
	// NumberFormat nf = NumberFormat.getNumberInstance();
	// nf.setMaximumFractionDigits(5);
	// String s = nf.format(num);
	// int index = s.indexOf(".");
	// if (index == 0) {
	// return 0;
	// }
	// int count = s.length() - index - 1;
	// return count;
	// }

	/**
	 * 4舍5入，保留2位小数点
	 * 
	 * @param num
	 * @return
	 */
	public static double scale(double num) {
		return new BigDecimal(num).setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 4舍5入，保留4位小数点
	 * 
	 * @param num
	 * @return
	 */
	public static double scale4(double num) {
		return new BigDecimal(num).setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double divide(double v1, double v2) {
		return divide(v1, v2, 10);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
