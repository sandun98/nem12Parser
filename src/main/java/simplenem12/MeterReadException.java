package simplenem12;


public class MeterReadException extends RuntimeException {

	public MeterReadException(String message) {
		super(message);
	}

	public MeterReadException(String message, Throwable e) {
		super(message, e);
	}

}
