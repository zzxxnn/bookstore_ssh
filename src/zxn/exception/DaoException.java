package zxn.exception;

public class DaoException extends RuntimeException {

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}
	/**
	 * �״��쳣ʱһ��Ҫ��e�Ž���
	 * @param cause
	 */
	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
