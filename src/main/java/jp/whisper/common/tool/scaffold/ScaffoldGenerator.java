package jp.whisper.common.tool.scaffold;

import jp.whisper.common.tool.scaffold.ScaffoldBuilder.GeneratorType;

public class ScaffoldGenerator
{

	public static void main(String[] args) throws Exception
	{
		ScaffoldGen generator = new ScaffoldGen("f00", "Role", "roles");
		/**
		 * パラメータ①：falseの場合、ソースファイルを生成。
		 *               trueの場合、コンソールでしか出力しない
		 * パラメータ②：GeneratorType (MODEL ,SQLMAP,CONTROLLER,SERVICE,TEST,VALIDATOR,NONE,ALL)
		 *               指定したタイプだけのソースファイルを生成。
		 *               NONEの場合は、パラメータ①がtrueである処理と一緒。
		 *               ALLである場合、あらゆるタイプのファイルを生成
		 *
		 */
		generator.execute(false, GeneratorType.SQLMAP);
	}

}
