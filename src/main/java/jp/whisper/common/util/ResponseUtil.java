package jp.whisper.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class ResponseUtil
{
	private static final int	BUF_SIZE	= 4096;

	private ResponseUtil(){

	}

	/**
	 *
	* @Title: download
	* @Description: ファイルダウンロード
	* @param response
	* @param path
	* @param fileType
	* @param fileName
	* @throws FileNotFoundException
	* @throws IOException
	* @Return: void
	* @Throws:
	* @Author: bieby
	* @Date: 2013-12-4
	 */
	public static void download(HttpServletResponse response, String path, String fileType, String fileName) throws FileNotFoundException, IOException
	{
		path = StringUtils.replace(path, "\\", "/");
		response.setContentType(fileType);
		response.setHeader("Content-Disposition", "attachment;   filename=\""
				+ URLEncoder.encode(fileName, "utf8") + "\"");
		byte[] buffer = new byte[BUF_SIZE]; //
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		OutputStream out = response.getOutputStream();
		try
		{
			bos = new BufferedOutputStream(out);
			bis = new BufferedInputStream(new FileInputStream(path));
			int n = (-1);
			while ((n = bis.read(buffer, 0, BUF_SIZE)) > -1)
			{
				bos.write(buffer, 0, n);
			}
			response.flushBuffer();
		}
		finally
		{
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
			if(out != null){
				out.flush();
				out.close();
			}
		}
	}

	/**
	 *
	* @Title: writeJson
	* @Description: json出力
	* @param response
	* @param jsonStr
	 * @throws IOException
	* @Return: void
	* @Throws:
	* @Author: bieby
	* @Date: 2013-12-4
	 */
	public static void writeJson( HttpServletResponse response, String strJson) throws IOException{
		PrintWriter out = null;
		DevLog.debug(strJson);
		try
		{
			out = response.getWriter();
			out.write(strJson);
		}
		finally{
			if(out != null ){
				out.flush();
				out.close();
			}
		}

	}

	/**
	 *
	* @Title: writeSuccesJson
	* @Description: 情報をJSON化してから出力
	* @param response response
	* @param msg msg NULL可
	* @throws IOException IOException
	* @Author: bieby
	* @Date: 2013-12-11
	 */
	public static void writeSuccessJson(HttpServletResponse response, String msg)throws IOException{
		Map<String, Object> hm = new HashMap<String, Object>();
		hm.put("success", Boolean.TRUE);
		if(StringUtil.isNotBlank(msg)){
			hm.put("messages", msg);
		}
		String strJson = JsonUtil.toJSONString(hm);
		ResponseUtil.writeJson(response, strJson);
	}


}
