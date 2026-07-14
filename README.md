# webapp-exercise（演習用）

デバッグ & リファクタリング研修の**演習プロジェクト**です。
Servlet / JSP 製の社員・部門管理アプリで、**意図的な不具合が仕込まれています**。
ビルドを通し、正しく動作させ、テストを通すことがゴールです。

## 動作環境

| 項目 | バージョン |
|------|-----------|
| JDK | 17 |
| Gradle | 8.14.4（Wrapper 同梱。`./gradlew` を使用） |
| DB | PostgreSQL 16（`localhost:5432` / user=`postgres` / password=`postgres`） |
| サーブレットコンテナ | 組み込み Tomcat 10.1（Gretty） |

## セットアップ

### 1. データベース準備

```bash
psql -h localhost -U postgres -d postgres -f sql/webpractice_create_tables.sql
```

### 2. ビルド

```bash
./gradlew build
```

> このプロジェクトには意図的な不具合が含まれます。
> 初期状態ではコンパイルエラー等が発生します。**それらを修正していくのが演習です。**

### 3. 起動（修正が進んだら）

```bash
./gradlew appRun
```

ブラウザで **http://localhost:8080/webapp/menu** を開く。
停止は Ctrl+C、または別ターミナルで `./gradlew appStop`。

### 4. テスト

```bash
./gradlew test
```

## ディレクトリ構成

```
webapp-exercise/
├── build.gradle / settings.gradle   … Gradle 設定
├── gradlew / gradle/                 … Gradle Wrapper（8.14.4）
├── sql/                              … DB 初期化スクリプト
└── src/
    ├── main/java/                    … Servlet(controller) / model
    └── main/webapp/WEB-INF/jsp/      … JSP（画面）
    └── test/java/                    … テスト
```

## 進め方の目安

1. `./gradlew compileJava` でコンパイルエラーを解消する
2. `./gradlew appRun` で起動し、各機能（社員・部門の一覧／登録／更新／削除）を操作して実行時の不具合を見つけて直す
3. `./gradlew test` でテストを通す
