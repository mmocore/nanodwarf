/*LICENSE*/

package com.sun.sgs.impl.sharedutil.logging;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

/**
 * Defines a logging {@code Filter} that logs exceptions at a different logging
 * level from other logging. This filter selects log records that contain an
 * exception of a specified type, and otherwise requires the record to have a
 * particular logging level.
 * <p>
 * 
 * This class recognizes the following {@link LogManager} configuration
 * properties:
 * 
 * <dl style="margin-left: 1em">
 * 
 * <dt><i>Property:</i> <b>{@value #EXCEPTION_CLASS_PROPERTY}</b> <br>
 * <i>Default:</i> {@link Throwable java.lang.Throwable}
 * 
 * <dd style="padding-top: .5em">
 * Specifies the exception class for which log records containing an exception
 * of that type should be logged regardless of the logging level. The value
 * should be the fully qualified name of the exception class. All exceptions of
 * the specified class or subclasses of that class will be considered.
 * <p>
 * 
 * <dt><i>Property:</i> <b>{@value #LEVEL_PROPERTY}</b> <br>
 * <i>Default:</i> {@link Level#INFO INFO}
 * 
 * <dd style="padding-top: .5em">
 * Specifies the logging level that is required for log records that do not
 * contain an exception of the type specified by
 * {@value #EXCEPTION_CLASS_PROPERTY}.
 * 
 * </dl>
 * <p>
 * 
 * For example, to use this class to log records to the console that throw
 * {@code TransactionAbortedException} at {@code FINEST} and all other messages
 * at {@code WARNING}, put the following text in the logging configuration file:
 * 
 * <pre>
 *   .level=FINEST
 *   handlers=java.util.logging.ConsoleHandler
 *   java.util.logging.ConsoleHandler.level=FINEST
 *   java.util.logging.ConsoleHandler.filter=com.sun.sgs.impl.sharedutil.logging.ExceptionFilter
 *   com.sun.sgs.impl.sharedutil.logging.ExceptionFilter.exception=com.sun.sgs.app.TransactionAbortedException
 *   com.sun.sgs.impl.sharedutil.logging.ExceptionFilter.level=WARNING
 * </pre>
 */
public class ExceptionFilter implements Filter {

	/** The property that specifies the exception class. */
	public static final String EXCEPTION_CLASS_PROPERTY = "com.sun.sgs.impl.sharedutil.logging.ExceptionFilter.exception";

	/**
	 * The property that specifies the logging level for log records that do not
	 * contain an exception of the specified exception class.
	 */
	public static final String LEVEL_PROPERTY = "com.sun.sgs.impl.sharedutil.logging.ExceptionFilter.level";

	/** The exception class. */
	private final Class<? extends Throwable> exceptionClass;

	/**
	 * The logging level required for log records that do not contain an
	 * exception of the exception class.
	 */
	private final Level level;

	/**
	 * Creates an instance of this class.
	 * 
	 * @throws ClassNotFoundException
	 *             if the exception class is not found
	 */
	public ExceptionFilter() throws ClassNotFoundException {
		LogManager logManager = LogManager.getLogManager();
		String value = logManager.getProperty(EXCEPTION_CLASS_PROPERTY);
		exceptionClass = (value != null) ? Class.forName(value).asSubclass(
				Throwable.class) : Throwable.class;
		value = logManager.getProperty(LEVEL_PROPERTY);
		level = (value != null) ? Level.parse(value) : Level.INFO;
	}

	/* -- Implement Filter -- */

	/**
	 * {@inheritDoc}
	 * <p>
	 * 
	 * This implementation returns true if the log record contains an exception
	 * that is a subclass of the class specified by the {@value
	 * EXCEPTION_CLASS_PROPERTY} log manager property, or if the level of the
	 * record is greater than or equal to the logging level specified by the
	 * {@value LEVEL_PROPERTY} property.
	 */
	public boolean isLoggable(LogRecord record) {
		return exceptionClass.isInstance(record.getThrown())
				|| record.getLevel().intValue() >= level.intValue();
	}
}
