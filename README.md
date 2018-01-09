## scalaping
指定したページに対してリンクの追加を監視し、slackで通知します。

## 手順
### jarの生成
### 設定ファイル(src/main/resources/application.conf)の編集
#### DBの接続設定
PostgreSQLの接続情報を`db.default.driver`,`db.default.url`,`db.default.user`,`db.default.password`に設定します。
```
# JDBC settings
db.default.driver="org.postgresql.Driver"
db.default.url= "jdbc:postgresql://localhost:5432/scalaping"
db.default.user=teruuu
db.default.password=teruuu
```

#### slackの通知設定
通知に使うslackのtokenとチャンネルを設定します。チャンネルが`#bot`の場合は`slack.channel="bot"`と設定します。
```
# slack
slack.token="***"
slack.channel="***"
```
#### スケジュール設定
通知のスケジューリングには`akka-quartz-scheduler`を使っています。以下は1時間に一回の設定になります。
```
akka {
  quartz {
    schedules {
      CrawlTask {
        description = "クローラ"
        expression = "0 0 * ? * *"
      }
    }
  }
}
```
### jar生成
以下のコマンドを実行します。(事前にsbtのインストールが必要です。)
> sbt assembly

## 実行
- 監視対象の確認
> java -cp jarパス com.teruuu.scalaping.command.util.Tops

- 監視対象の追加
> java -cp jarパス com.teruuu.scalaping.command.util.AddTop  監視対象URL ページ名 ページ説明

- 起動
> java -jar jarパス
