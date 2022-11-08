package simplenem12.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simplenem12.EnergyUnit;
import simplenem12.LineProcessor;
import simplenem12.MeterRead;
import simplenem12.MeterReadException;
import simplenem12.MeterVolume;
import simplenem12.Quality;

public class SimpleNem12LineProcessorImpl implements LineProcessor {
	final Logger log = LoggerFactory.getLogger(getClass());

	private List<MeterRead> meterReads = new ArrayList<>();
	private int lineNumber = 0;

	public int getLineNumber() {
		return lineNumber;
	}

	private SimpleNem12Line lastLine;

	/**
	 * 
	 * This method processes lines from an nem12 file
	 * Lines will be validated to ensure file has a start and end record type
	 * otherwise will stop processing.
	 * Each volume record line is processed and grouped in to a meter read
	 * block<code>MeterRead</code>.
	 * 
	 * 
	 */
	public void process(String str) {
		SimpleNem12Line line = new SimpleNem12Line(str);
		if (lineNumber == 0) {
			if (!line.isStartFile()) {
				throw new MeterReadException(
						"File start could not be located : File start must contain " + SimpleNem12Line.START_FILE
								+ " at line " + (lineNumber + 1));
			}
		} else {
			String[] parts = line.getParts();
			if (line.isStartOfMeterBlock()) {
				MeterRead meterRead = new MeterRead(validateNmi(parts[1]), EnergyUnit.valueOf(parts[2]));
				meterReads.add(meterRead);

			} else if (line.isMeterReading()) {
				MeterRead meterRead = meterReads.get(meterReads.size() - 1);
				meterRead.appendVolume(LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyyMMdd")),
						new MeterVolume(new BigDecimal(parts[2]), Quality.valueOf(parts[3])));
			}
		}

		lastLine = line;
		lineNumber++;
	}

	private String validateNmi(String nmi) {
		if (nmi.length() != 10) {
			throw new MeterReadException("nmi should be of 10 characters long at line " + (lineNumber + 1));
		}
		return nmi;
	}

	public List<MeterRead> getMeterReads() {
		validateEndOfFile();
		return meterReads;
	}

	private void validateEndOfFile() {
		if (lastLine != null && !lastLine.isEndFile()) {
			throw new MeterReadException(
					"File end could not be located : File end must contain " + SimpleNem12Line.END_FILE + " at line "
							+ (lineNumber + 1));
		}
	}

}
