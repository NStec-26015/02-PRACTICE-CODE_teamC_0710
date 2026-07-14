package model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベースの接続・切断・トランザクション処理などを行うクラス
 *
 * @author Fullness, Inc.
 *
 */
public class ConnectionManager {

	/**
	 * コネクション
	 */
	private Connection connection;

	/**
	 * 接続URL
	 */
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";

	/**
	 * 接続ユーザ
	 */
	private static final String USER = "postgres";

	/**
	 * 接続パスワード
	 */
	private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("ドライバのロードに失敗しました", e);
        }
    }

	/**
	 * DBとのコネクションを取得し、返却
	 * 
	 * @return コネクション
	 * @throws SQLException コネクション取得失敗
	 */
	public Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
		}
		return connection;
	}

	/**
	 * トランザクションをコミット
	 * 
	 * @throws SQLException コミット失敗
	 */
	public void commit() throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	/**
	 * トランザクションをロールバック
	 * 
	 * @throws SQLException ロールバック失敗
	 */
	public void rollback() throws SQLException {
		if (connection != null) {
			connection.rollback();
		}
	}
}