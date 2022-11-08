package simplenem12.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import simplenem12.LineProcessor;
import simplenem12.MeterRead;
import simplenem12.MeterReadException;
import simplenem12.SimpleNem12Parser;

public class SimpleNem12ParserImpl implements SimpleNem12Parser {

	final Logger log = LoggerFactory.getLogger(getClass());
	private LineProcessor lineProcessor;

	public SimpleNem12ParserImpl(LineProcessor simpleNem12LineProcessor) {
		lineProcessor = simpleNem12LineProcessor;
	}

	@Override
	public Collection<MeterRead> parseSimpleNem12(File simpleNem12File) {
		Path path = Paths.get(simpleNem12File.getAbsolutePath());
		log.debug("processing file {}", path.toAbsolutePath());
		try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
			String curLine;
			while ((curLine = bufferedReader.readLine()) != null) {
				lineProcessor.process(curLine);
			}
			return lineProcessor.getMeterReads();
		} catch (IOException e) {
			throw new MeterReadException(e.getMessage(), e);
		}
	}

}
