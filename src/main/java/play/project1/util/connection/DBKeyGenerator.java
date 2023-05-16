package play.project1.util.connection;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class DBKeyGenerator {

	private DBKeyGenerator() { }

	public static class LazyHolder {
		private static final KeyHolder INSTANCE = new GeneratedKeyHolder();
	}

	public static KeyHolder getInstance() {
		return LazyHolder.INSTANCE;
	}
}
