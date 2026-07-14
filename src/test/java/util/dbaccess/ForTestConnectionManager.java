package util.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * データベースの接続と切断ドライバークラス<br>
 * テスト用
 *
 * @author Fullness, Inc.
 *
 */
public class ForTestConnectionManager {

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

	private static final String NEW_PASSWORD = "new";

	/**
	 * DBとのコネクションを取得し、返却
	 * 
	 * @return コネクション
	 */
	public Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				// コミットモードの設定
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new RuntimeException("データベースの接続に失敗しました", e);
			}
		}
		return connection;
	}

	/**
	 * DBとのコネクションを取得し、返却
	 * 
	 * @return コネクション
	 */
	public Connection getConnectionForReset() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, NEW_PASSWORD);
				// コミットモードの設定
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				throw new RuntimeException("データベースの接続に失敗しました", e);
			}
		}
		return connection;
	}

	/**
	 * DBとのコネクションを切断
	 */
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("データベースの切断に失敗しました", e);
		}
	}
}
