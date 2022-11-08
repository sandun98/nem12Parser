package simplenem12.impl;

import simplenem12.MeterReadException;

public class SimpleNem12Line {

	public static final String START_FILE = "100";
	public static final String END_FILE = "900";
	public static final String START_METER_BLOCK = "200";
	public static final String METER_READING = "300";

	private String content;

	public SimpleNem12Line(String line) {
		if (line == null || line.trim().isEmpty()) {
			throw new MeterReadException("line cannot be empty");
		}
		content = line;
	}

	public boolean isStartOfMeterBlock() {
		return content.startsWith(START_METER_BLOCK);
	}

	public boolean isStartFile() {
		return START_FILE.equals(content);
	}

	public boolean isEndFile() {
		return END_FILE.equals(content);
	}

	public boolean isMeterReading() {
		return content.startsWith(METER_READING);
	}

	public String[] getParts() {
		return content.split(",");
	}

}
