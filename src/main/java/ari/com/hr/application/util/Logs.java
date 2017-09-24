package ari.com.hr.application.util;

import org.slf4j.Logger;

public class Logs {
	public static void logDebug(Logger logger, String message, Object... parameter) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(message, parameter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
