package com.ikwang.fungi.dao.test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class GeohashTest {

 public GeohashTest() {
  
 }

 @Test
 public void test(){
	 DiscountUtils e = new DiscountUtils();
		String s = e.encode(23.232323, 32.323232);
		System.out.println(s);
		double[] latlon = e.decode(s);
		DecimalFormat df = new DecimalFormat("0.000000");
		System.out.println(df.format(latlon[0]) + ", " + df.format(latlon[1]));
 }
 
 @Test
 public void testHashMap(){
	 
	 System.out.print("start");
	 BASE36.decode("010");
	 System.out.println(BASE36.encode(1296, 3));
	 for(int i=0;i<=2000;i++){
		 
		 String coded=BASE36.encode(i, 3);
		 System.out.println(coded+":"+BASE36.decode(coded));
	 }
	 
 }
 
 static class BASE36{
	 
	 public static char[] digits={ '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A','B', 'C', 'D', 'E', 'F', 'G', 'H','I', 'J', 'K','L', 'M', 'N','O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	 private static String digitString=new String(digits);
	 private static long[] values={0,36,1296,46656,1679616,60466176,2176782336l,78364164096l};
	 
	 /**
	  * decode a BASE36 string to Long value
	  * 
	  * @param input the input Base36 string
	  * @return the long value
	  * @throws RuntimeException the input string is not a valid Base36 string
	  * 
	  * */
	 public static long decode(String input) throws RuntimeException{
		 if(input == null || input.length() == 0){
			 return 0;
		 }
		 char[] chars=input.toCharArray();
		 int position=0;
		 long ret=0;
		 while(position<chars.length){
			 int idx=digitString.indexOf(chars[position]);
			 if(idx < 0){
				 throw new RuntimeException("invalid input");
			 }
			 long pvalue=values[chars.length-position-1];
			 if(pvalue==0){
				 ret+=idx;
			 }else{
			 ret+=idx*pvalue;}
			 position++;
		 }
		 return ret;
	 }
	 /**
	  * encodes a long value into  Base36 string in a given length
	  * @param value the input long value
	  * @param length the length of the  string 
	  * @return the Base36 string
	  * @throws RuntimeException the input length is not in the range of[1,7],or the input value is too big to illustrate
	  * 
	  * */
	 public static String encode(long value,int length){
		 if(length>4 || length < 1){
			 throw new RuntimeException("maximum value for parameter length is 7");
		 }
		 if(values[length] <= value){
			 throw new RuntimeException("the input value is too big to illustrate with "+length+" digital output");
		 }
		 char[] ret=new char[length];
		 int indexStart=ret.length;
		 while(length>0){
			 Long digitalMax=values[length-1];
			 ret[indexStart-length]=getCharAtPosition(value, length-1);
			 if(digitalMax>0){
			 value=value%digitalMax;
			 }
			 length--;
		 }
		 return new String(ret);
	 }
	 private static char getCharAtPosition(Long value,int position){
		 long p=values[position];
		 if(p == 0)
			 return digits[value.intValue()];
		 Long idx=value/p;
		 return digits[idx.intValue()];
	 }
	 
 }
 
 
 static class DiscountUtils
 {

 	private static int numbits = 6 * 5;
 	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
 			'9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p',
 			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

 	final static HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
 	static {
 		int i = 0;
 		for (char c : digits) {
 			lookup.put(c, i++);
 		}
 	}
 //
// 	public static void main(String[] args) {
// 		double[] latlon = new DiscountUtils().decode("dj248j248j24");
// 		System.out.println(latlon[0] + " " + latlon[1]);
 //
// 		DiscountUtils e = new DiscountUtils();
// 		String s = e.encode(30, -90.0);
// 		System.out.println(s);
// 		latlon = e.decode(s);
// 		DecimalFormat df = new DecimalFormat("0.00000");
// 		System.out.println(df.format(latlon[0]) + ", " + df.format(latlon[1]));
// 		
// 	}

 	/**
 	 * 将Geohash字串解码成经纬值
 	 * 
 	 * @param geohash
 	 *            待解码的Geohash字串
 	 * @return 经纬值数组
 	 */
 	public static double[] decode(String geohash) {
 		StringBuilder buffer = new StringBuilder();
 		for (char c : geohash.toCharArray()) {
 			int i = lookup.get(c) + 32;
 			buffer.append(Integer.toString(i, 2).substring(1));
 		}

 		BitSet lonset = new BitSet();
 		BitSet latset = new BitSet();

 		// even bits
 		int j = 0;
 		for (int i = 0; i < numbits * 2; i += 2) {
 			boolean isSet = false;
 			if (i < buffer.length())
 				isSet = buffer.charAt(i) == '1';
 			lonset.set(j++, isSet);
 		}

 		// odd bits
 		j = 0;
 		for (int i = 1; i < numbits * 2; i += 2) {
 			boolean isSet = false;
 			if (i < buffer.length())
 				isSet = buffer.charAt(i) == '1';
 			latset.set(j++, isSet);
 		}

 		double lat = decode(latset, -90, 90);
 		double lon = decode(lonset, -180, 180);

 		DecimalFormat df = new DecimalFormat("0.00000");
 		return new double[] { Double.parseDouble(df.format(lat)), Double.parseDouble(df.format(lon)) };
 	}

 	/**
 	 * 根据二进制编码串和指定的数值变化范围，计算得到经/纬值
 	 * 
 	 * @param bs
 	 *            经/纬二进制编码串
 	 * @param floor
 	 *            下限
 	 * @param ceiling
 	 *            上限
 	 * @return 经/纬值
 	 */
 	private static double decode(BitSet bs, double floor, double ceiling) {
 		double mid = 0;
 		for (int i = 0; i < bs.length(); i++) {
 			mid = (floor + ceiling) / 2;
 			if (bs.get(i))
 				floor = mid;
 			else
 				ceiling = mid;
 		}
 		return mid;
 	}

 	/**
 	 * 根据经纬值得到Geohash字串
 	 * 
 	 * @param lat
 	 *            纬度值
 	 * @param lon
 	 *            经度值
 	 * @return Geohash字串
 	 */
 	public static String encode(double lat, double lon) {
 		BitSet latbits = getBits(lat, -90, 90);
 		BitSet lonbits = getBits(lon, -180, 180);
 		StringBuilder buffer = new StringBuilder();
 		for (int i = 0; i < numbits; i++) {
 			buffer.append((lonbits.get(i)) ? '1' : '0');
 			buffer.append((latbits.get(i)) ? '1' : '0');
 		}
 		return base32(Long.parseLong(buffer.toString(), 2));
 	}

 	/**
 	 * 将二进制编码串转换成Geohash字串
 	 * 
 	 * @param i
 	 *            二进制编码串
 	 * @return Geohash字串
 	 */
 	public static String base32(long i) {
 		char[] buf = new char[65];
 		int charPos = 64;
 		boolean negative = (i < 0);
 		if (!negative)
 			i = -i;
 		while (i <= -32) {
 			buf[charPos--] = digits[(int) (-(i % 32))];
 			i /= 32;
 		}
 		buf[charPos] = digits[(int) (-i)];

 		if (negative)
 			buf[--charPos] = '-';
 		return new String(buf, charPos, (65 - charPos));
 	}

 	/**
 	 * 得到经/纬度对应的二进制编码
 	 * 
 	 * @param lat
 	 *            经/纬度
 	 * @param floor
 	 *            下限
 	 * @param ceiling
 	 *            上限
 	 * @return 二进制编码串
 	 */
 	private static BitSet getBits(double lat, double floor, double ceiling) {
 		BitSet buffer = new BitSet(numbits);
 		for (int i = 0; i < numbits; i++) {
 			double mid = (floor + ceiling) / 2;
 			if (lat >= mid) {
 				buffer.set(i);
 				floor = mid;
 			} else {
 				ceiling = mid;
 			}
 		}
 		return buffer;
 	}}
}
