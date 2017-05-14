package jp.whisper.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jp.whisper.common.constant.Constants;
import junit.framework.TestCase;

public class BaseTestCase extends TestCase
{

	protected ApplicationContext	ctx;


	public BaseTestCase()
	{
		super();
	}


	public BaseTestCase(String methodName)
	{
		super(methodName);
	}


	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		ctx = new ClassPathXmlApplicationContext(Constants.CONTEXT_CONF);
	}

}

