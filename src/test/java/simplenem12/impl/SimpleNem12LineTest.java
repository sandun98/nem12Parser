package simplenem12.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleNem12LineTest {

	@Test
	public void testNull() {
		Assertions.assertThrows(Exception.class, () -> new SimpleNem12Line(null));
	}

	@Test
	public void testStartFile() throws Exception {
		SimpleNem12Line line = new SimpleNem12Line("100");
		assertTrue(line.isStartFile());
	}

	@Test
	public void testEndFile() throws Exception {
		SimpleNem12Line line = new SimpleNem12Line("900");
		assertTrue(line.isEndFile());
	}

	@Test
	public void testMeterBlock() throws Exception {
		SimpleNem12Line line = new SimpleNem12Line("200,6123456789,KWH");
		assertTrue(line.isStartOfMeterBlock());
	}

	@Test
	public void testMeterRead() throws Exception {
		SimpleNem12Line line = new SimpleNem12Line("300,20161117,0,A");
		assertTrue(line.isMeterReading());
	}

}
