package com.pass.util.math;


import java.math.BigDecimal;

/**
 * 数学工具类
 * 
 * @author Shawpin Shi
 * @since Jun 30, 2016
 */
public class MathUtils {
	/**
	 * 判定一个字符串是不是数值
	 * 
	 * @author Shawpin Shi
	 * @since Jun 30, 2016
	 */
	public static boolean isNumber(String numberString) {
		try {
			Double.parseDouble(numberString);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * 将字符串转化为double值返回，如果字符串不是数字返回0
	 *
	 * @author Shawpin Shi
	 * @since 2017/12/10
	 */
	public static double paseDouble(String numberString) {
		try {
			return Double.parseDouble(numberString);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/**
	 * 判定一个字符串是不是整数
	 *
	 * @author Shawpin Shi
	 * @since 2017/12/10
	 */
	public static boolean isInteger(String numberString) {
		try {
			Integer.parseInt(numberString);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * 四舍五入函数
	 * 
	 * @param sourceNum 原来的数字
	 * @param scale 需要保留的精度，即小数点后的位数
	 * @author Shawpin Shi
	 * @since Jun 30, 2016
	 * @since 2018-08-29 删除原来先保留20位小数的的两行代码
	 */
	public static double round(double sourceNum, int scale) {
		BigDecimal sourceBD = new BigDecimal(Double.toString(sourceNum));
		BigDecimal one = new BigDecimal("1");

		// ROUND_HALF_UP
		// 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则舍入远离零的方向（数轴上方向上）。
		// 注意，这是我们大多数人在小学时就学过的舍入模式，即四舍五入。

		return sourceBD.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 四舍五入函数
	 *
	 * @param sourceNum 原来的数字
	 * @param scale 需要保留的精度，即小数点后的位数
	 * @author Shawpin Shi
	 * @since Jun 30, 2016
	 * @since 2018-08-29 删除原来先保留20位小数的的两行代码
	 */
	public static float round(float sourceNum, int scale) {
		BigDecimal sourceBD = new BigDecimal(Float.toString(sourceNum));
		BigDecimal one = new BigDecimal("1");

		// ROUND_HALF_UP
		// 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则舍入远离零的方向（数轴上方向上）。
		// 注意，这是我们大多数人在小学时就学过的舍入模式，即四舍五入。

		return sourceBD.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 求对数
	 * <p>
	 * logx(y) =loge(x) / loge(y)
	 * </p>
	 * 
	 * @param base 对数的底
	 * @author Shawpin Shi
	 * @since May 20, 2017
	 */
	public static double log(double base, double value) {
		return Math.log(value) / Math.log(base);
	}

	/**
	 * 将数字或字符串转换为整数
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static int castToInt(Object value) {
		if (value == null) {
			return 0;
		}

		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}

		if (value instanceof Number) {
			return ((Number) value).intValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;

			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return 0;
			}

			if (strVal.indexOf(',') != 0) {
				strVal = strVal.replaceAll(",", "");
			}

			return Integer.parseInt(strVal);
		}

		if (value instanceof Boolean) {
			return ((Boolean) value).booleanValue() ? 1 : 0;
		}

		throw new RuntimeException("can not cast to int, value : " + value);
	}

	/**
	 * 将数字或字符串转换为长整数
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static long castToLong(Object value) {
		if (value == null) {
			return 0;
		}

		if (value instanceof Number) {
			return ((Number) value).longValue();
		}

		if (value instanceof String) {
			String strVal = (String) value;
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return 0;
			}

			if (strVal.indexOf(',') != 0) {
				strVal = strVal.replaceAll(",", "");
			}

			try {
				return Long.parseLong(strVal);
			} catch (NumberFormatException ex) {
				//
			}
		}

		throw new RuntimeException("can not cast to long, value : " + value);
	}

	/**
	 * 将数字或字符串转换为Double
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static double castToDouble(Object value) {
		if (value == null) {
			return 0;
		}

		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}

		if (value instanceof String) {
			String strVal = value.toString();
			if (strVal.length() == 0 //
					|| "null".equals(strVal) //
					|| "NULL".equals(strVal)) {
				return 0;
			}

			if (strVal.indexOf(',') != 0) {
				strVal = strVal.replaceAll(",", "");
			}

			return Double.parseDouble(strVal);
		}

		throw new RuntimeException("can not cast to double, value : " + value);
	}

	/**
	 * 将数字或字符串转换为整数，转换过程中如果出现异常则返回0
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static int castToIntNoException(Object value) {
		try {
			return castToInt(value);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 将数字或字符串转换为长整数，转换过程中如果出现异常则返回0
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static long castToLongNoException(Object value) {
		try {
			return castToLong(value);
		} catch (Exception e) {
			return 0L;
		}
	}

	/**
	 * 将数字或字符串转换为Double，转换过程中如果出现异常则返回0
	 *
	 * @author Shawpin Shi
	 * @since 2018/5/25
	 */
	public static double castToDoubleNoException(Object value) {
		try {
			return castToDouble(value);
		} catch (Exception e) {
			return 0D;
		}
	}



}
