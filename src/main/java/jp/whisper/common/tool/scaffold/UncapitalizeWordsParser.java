package jp.whisper.common.tool.scaffold;

import org.apache.commons.lang3.StringUtils;

public class UncapitalizeWordsParser implements WordsParser
{

	public String parseWords(String orginalString)
	{
		return StringUtils.uncapitalize(orginalString.toLowerCase());
	}

}

