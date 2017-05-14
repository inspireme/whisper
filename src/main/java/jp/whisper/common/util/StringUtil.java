package jp.whisper.common.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.UrlBase64;

/**
 * String Utility Class
 */
public class StringUtil
{
	private static final char	CHAR_BLANK		= ' ';
	public final static String	COLON			= ":";
	public final static String	COMMA			= ",";
	public final static String	EMPTY			= "";
	public final static String	UNDER_LINE		= "_";
	public final static String	ENDL			= "\n";
	public final static String	SLASH			= "/";
	public final static String	BLANK			= " ";
	public final static String	DOT				= ".";
	public final static String	FILE_SEPARATOR	= File.separator;

	static char					hexDigits[]		=
												{ // 16進数に変換
												'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f'							};


	public static String getMD5(String data)
	{
		String s = "";
		byte[] source = data.getBytes();
		try
		{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++)
			{
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}


	private StringUtil()
	{
		// nothing
	}


	/**
	 * encode a password using MD5 algorithm
	 *
	 * @param password
	 * @return
	 */
	public static String encodePassword(String password)
	{
		String encodedPassword = null;
		try
		{
			encodedPassword = encodePassword(password, "MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return encodedPassword;
	}


	public static String changeList2String(List<String> list)
	{
		String tmp = list.toString();
		return tmp.substring(1, tmp.length() - 1);
	}


	/**
	 * check if the string is blank "" and null return true
	 *
	 */
	public static boolean isBlank(String str)
	{
		boolean flag = false;
		if (str == null || "".equals(str.trim()))
		{
			flag = true;
		}
		return flag;
	}


	/**
	 * check if the String is not Blank "" and null return false
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str)
	{
		boolean flag = false;
		if (str != null && !"".equals(str))
		{
			flag = true;
		}
		return flag;
	}


	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 *
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 *
	 * @return encypted password based on the algorithm.
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodePassword(String password, String algorithm) throws NoSuchAlgorithmException
	{
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		// first create an instance, given the provider
		md = MessageDigest.getInstance(algorithm);

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++)
		{
			if ((encodedPassword[i] & 0xff) < 0x10)
			{
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}


	public static boolean isEmpty(String str)
	{
		if (str == null)
			return true;
		return EMPTY.equals(str) ? true : false;
	}


	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 *
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 *
	 * @param str
	 * @return String
	 */
	public static String base64Encode(String str)
	{
		// sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		// return encoder.encodeBuffer(str.getBytes()).trim();
		byte[] bytes = UrlBase64.encode(str.getBytes());
		return new String(bytes);
	}


	/**
	 * Decode a string using Base64 encoding.
	 *
	 * @param str
	 * @return String
	 */
	public static String base64Decode(String str)
	{
		// sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		// try {
		// return new String(dec.decodeBuffer(str));
		// } catch (IOException io) {
		// throw new RuntimeException(io.getMessage(), io.getCause());
		// }
		byte[] bytes = UrlBase64.decode(str.getBytes());
		return new String(bytes);
	}


	/**
	 * 获取指定长度的随机字串
	 *
	 * @param length
	 * @return String
	 */
	public static String getRandomString(int length)
	{
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		int i = 0;
		int c;
		while (i < length)
		{
			if ((c = r.nextInt(90)) > 64 || (c = r.nextInt(122)) > 97)
			{
				sb.append((char) c);
				i++;
			}
		}
		return sb.toString();
	}


	public static boolean contains(String[] arr, String s)
	{
		if (arr != null)
		{
			for (int i = 0; i < arr.length; i++)
			{
				if (s.equals(arr[i]))
				{
					return true;
				}
			}
		}
		return false;
	}


	public static String ljustZero(int i, int len)
	{
		return ljustZero(String.valueOf(i), len);
	}


	public static String ljustZero(String s, int len)
	{
		String id = "";
		for (int i = 0; i < len - s.length(); i++)
		{
			id += "0";
		}
		id += s;
		return id;
	}


	public static int getWordLength(String str)
	{
		int len = 0;
		if (StringUtil.isBlank(str))
		{
			return len;
		}
		char[] ch = str.toCharArray();
		for (int i = 0; i < ch.length; i++)
		{
			if (isChinese(ch[i]))
			{
				len = len + 2;
			}
			else
			{
				len = len + 1;
			}
		}
		return len;
	}


	public static String getWord(String str, int max)
	{
		char[] ch = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		int len = 0;
		for (int i = 0; i < ch.length; i++)
		{
			if (isChinese(ch[i]))
			{
				len = len + 2;
			}
			else
			{
				len = len + 1;
			}
			if (len > max)
			{
				break;
			}
			sb.append(ch[i]);
		}
		return sb.toString();
	}


	public static boolean hasChinese(String s)
	{
		if (s == null)
			return true;
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (isChinese(chars[i]))
			{
				return true;
			}
		}
		return false;
	}


	public static boolean isChinese(char c)
	{
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS)
		{
			return true;
		}
		return false;
	}


	public static String[] splitIntoArr(String s)
	{
		if (isEmpty(s))
			return new String[] {};
		return s.split(",");
	}


	public static String join(String[] arr)
	{
		return join(arr, ",", "'");
	}


	public static String join(String[] arr, String separtor)
	{
		return join(arr, ",", "");
	}


	public static String join(String[] arr, String separtor, String quota)
	{
		String result = "";
		for (int i = 0; i < arr.length; i++)
		{
			result = result + quota + arr[i] + quota;
			if (i != arr.length - 1)
				result += separtor;
		}
		return result;
	}


	/**
	 * 转化字符编码，如convertCode("someStr","iso-8859-1",UTF-8")
	 *
	 * @param inStr
	 * @param encode1
	 *            原字符串编码
	 * @param encode2
	 *            目标字串编码
	 * @return
	 */
	public static String convertCode(String inStr, String encode1, String encode2)
	{
		if (isBlank(inStr))
			return EMPTY;
		try
		{
			return new String(inStr.getBytes(encode1), encode2);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return inStr;
		}

	}


	public static boolean isUpperCase(String s)
	{
		boolean result = true;
		char ch;
		for (int i = 0; i < s.length(); i++)
		{
			ch = s.charAt(i);
			if (!(ch >= 'A' && ch <= 'Z'))
			{
				result = false;
			}
		}
		return result;
	}


	public static boolean isLowerCase(String s)
	{
		boolean result = true;
		char ch;
		for (int i = 0; i < s.length(); i++)
		{
			ch = s.charAt(i);
			if (!(ch >= 'a' && ch <= 'z'))
			{
				result = false;
			}
		}
		return result;
	}


	public static int countBlanks(String s)
	{
		int count = 0;
		char[] chars = s.toCharArray();
		for (char ch : chars)
		{
			if (ch != CHAR_BLANK)
			{
				break;
			}
			count++;
		}
		return count;
	}


	public static String encodeIso(String sourceStr)
	{
		return encodeIso(sourceStr, "GBK");
	}


	public static String encodeIso(String sourceStr, String encoding)
	{
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(sourceStr))
		{
			try
			{
				result = new String(sourceStr.getBytes(encoding), "ISO8859_1");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}


	/**
	 * oracle.sql.Clob类型转换成String类型
	 *
	 * @param clob
	 * @return
	 */
	public static String clob2String(Clob clob)
	{
		if (clob == null)
		{
			return null;
		}
		StringBuffer sb = new StringBuffer(65535);// 64K
		Reader clobStream = null;// 创建一个输入流对象
		try
		{
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1)
			{
				sb.append(b, 0, i);
			}
		}
		catch (Exception ex)
		{
			sb = null;
		}
		finally
		{
			try
			{
				if (clobStream != null)
					clobStream.close();
			}
			catch (Exception e)
			{
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

	/**
	 *
	* @Title: concat
	* @Description: 字符数组合并
	* @param a
	* @param b
	* @return
	* @Author: bieby
	* @Date: 2014-1-15
	 */
	public static String[] concat(String[] a, String[] b) {
		   String[] c= new String[a.length+b.length];
		   System.arraycopy(a, 0, c, 0, a.length);
		   System.arraycopy(b, 0, c, a.length, b.length);
		   return c;
	}

	/**
	 *
	* @Title: getStackTrace
	* @Description: 获取堆栈日志信息
	* @param t 异常
	* @return 异常堆栈
	* @Author: bieby
	* @Date: 2014-4-3
	 */
	public static String getStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	/**
	* @Title: ToSBC
	* @Description: 半角转全角
	* @param input
	* @return string
	* @Author: lit
	* @Date: 2015-6-17
	 */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
        	if (c[i] == ' ') {
        		c[i] = '\u3000';
        	} else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
    * @Title: ToDBC
    * @Description: 全角转半角
    * @param input
    * @return returnString
    * @Author: lit
    * @Date: 2015-6-17
     */
    public static String ToDBC(String input) {

        char c[] = input.toCharArray();
        for(int i = 0; i < c.length; i++) {
        	if(c[i] == '\u3000') {
                c[i] = ' ';
        	} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
        		c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);
        return returnString;
    }

	public static void main(String[] args)
	{
		String uid = "abc";
		String encodeUid = StringUtil.base64Encode(uid);
		DevLog.debug(encodeUid);
		DevLog.debug(StringUtil.base64Decode(encodeUid));
		try
		{
			DevLog.debug(encodePassword(uid, "MD5"));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}
}
