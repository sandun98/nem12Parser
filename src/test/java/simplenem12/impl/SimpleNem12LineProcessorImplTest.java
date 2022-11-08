package simplenem12.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import simplenem12.MeterReadException;

@SpringBootTest
public class SimpleNem12LineProcessorImplTest {

	@Test
	public void testFileSingleMeterRead() {
		SimpleNem12LineProcessorImpl lineProcessor = new SimpleNem12LineProcessorImpl();

		lineProcessor.process("100");
		lineProcessor.process("200,6123456789,KWH");
		lineProcessor.process("300,20161113,-50.8,A");
		lineProcessor.process("900");
		assertNotNull(lineProcessor.getMeterReads());

		assertEquals(1, lineProcessor.getMeterReads().size());
	}

	@Test
	public void testFileTwoMeterRead() {
		SimpleNem12LineProcessorImpl lineProcessor = new SimpleNem12LineProcessorImpl();

		lineProcessor.process("100");
		lineProcessor.process("200,6123456789,KWH");
		lineProcessor.process("300,20161113,-50.8,A");
		lineProcessor.process("200,6123456766,KWH");
		lineProcessor.process("300,20161122,-10.2,E");
		lineProcessor.process("900");
		assertNotNull(lineProcessor.getMeterReads());

		assertEquals(2, lineProcessor.getMeterReads().size());
	}

	@Test
	public void testInvalidStartFile() {
		SimpleNem12LineProcessorImpl lineProcessor = new SimpleNem12LineProcessorImpl();

		Assertions.assertThrows(MeterReadException.class, () -> lineProcessor.process("200,6123456789,KWH"));
	}

	@Test
	public void testInvalidEndFile() {
		SimpleNem12LineProcessorImpl lineProcessor = new SimpleNem12LineProcessorImpl();
		lineProcessor.process("100");
		lineProcessor.process("200,6123456789,KWH");
		lineProcessor.process("300,20161113,-50.8,A");
		lineProcessor.process("200,6123456766,KWH");
		lineProcessor.process("300,20161122,-10.2,E");
		Assertions.assertThrows(MeterReadException.class, () -> lineProcessor.getMeterReads());
	}

	@Test
	public void testInvalidNmi() throws Exception {
		SimpleNem12LineProcessorImpl lineProcessor = new SimpleNem12LineProcessorImpl();

		Assertions.assertThrows(MeterReadException.class, () -> lineProcessor.process("200,612345678,KWH"));
	}

}
