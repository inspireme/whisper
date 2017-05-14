# Asita

開発環境設備
JDK1.8
Eclipse Neon Release (4.6.0)
ORACLE Version 10gR2


初期手順

①依存関係
mvn install:install-file "-DgroupId=com.oracle" "-DartifactId=ojdbc6" "-Dversion=11.2.0.4" "-Dpackaging=jar" "-Dfile=E:\99.Workspace\javaspace\maven_bak\ojdbc6-11.2.0.4.jar"
★「ojdbc6-11.2.0.4.jar」をwhisper\libに入れてある

②DB初期
/whisper/src/deployの配下にあるSQLファイルを順番で実行
create_scheme.sql
create_table.sql
init_data.sql



ソース生成
以下のファイルをご参考
/whisper/src/main/java/jp/whisper/common/tool/scaffold/ScaffoldGenerator.java



対応待ち
１、メッセージの統一管理
２、UI部分の枠の作成
３、共通部品の開発⇒ファイルのUP・DownLoad