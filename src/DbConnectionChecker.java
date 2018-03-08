import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DbConnectionChecker {

	public static void main(String[] args) throws SQLException {
		printHeader();
		registerDriver();
		LocalDateTime startTime = LocalDateTime.now();
		connect(args[0], args[1], args[2]);
		printSummary(ChronoUnit.MILLIS.between(startTime, LocalDateTime.now()));
	}

	private static void connect(String url, String userName, String password) throws SQLException {
		Connection connection = null;
		try {
			System.out.printf("Host: %s \nUsername: %s \nPassword: %s\n", url, userName, maskPassword(password));
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			System.err.println("Connection Failed! Check output console");
			System.err.println("SQL State: " + e.getSQLState());
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("Connection Established!");
			connection.close();
			System.out.println("Connection Closed!");
		} else {
			System.err.println("Failed to make connection!");
		}
	}

	private static void printHeader() {
		System.out.println();
		System.out.println("-------- BEGIN Oracle JDBC Connection Test ------");
		System.out.println();
	}
	
	private static void printSummary(long elapsedTimeInMilliSeconds) {
		System.out.println();
		System.out.printf("Operation took %d minutes, %d seconds, %d milliseconds.\n", elapsedTimeInMilliSeconds/(1000*60), elapsedTimeInMilliSeconds/1000, elapsedTimeInMilliSeconds%1000);
		System.out.println();
		System.out.println("-------- CONCLUDE Oracle JDBC Connection Test ------");
	}

	private static void registerDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.err.println("Dude, Where is the Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Registered Oracle JDBC Driver!");
	}
	
	private static String maskPassword(String password) {
		String maskedPassword = null;
		if(password != null) {
			int passwordLength = password.length();
			maskedPassword = (passwordLength > 2) ? password.charAt(0) + mask(password.substring(1, passwordLength), '*') + password.charAt(passwordLength-1) : mask(password, '*');
		}
		return maskedPassword;
	}
	
	private static String mask(String str, char maskCharacter) {
		return String.format("%0" + str.length() + "d", 0).replace('0', maskCharacter);
	}

}
