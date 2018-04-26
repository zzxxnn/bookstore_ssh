package zxn.exception;

public class DaoException extends RuntimeException {

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}
	/**
	 * 抛此异常时一定要把e放进来
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
