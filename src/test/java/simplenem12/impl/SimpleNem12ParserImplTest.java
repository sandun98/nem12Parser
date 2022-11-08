package simplenem12.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import simplenem12.MeterRead;
import simplenem12.SimpleNem12Parser;

@SpringBootTest
public class SimpleNem12ParserImplTest {

	private SimpleNem12Parser simpleNem12Parser = new SimpleNem12ParserImpl(new SimpleNem12LineProcessorImpl());

	@Test
	public void testParse() throws IOException {
		Resource resource = new ClassPathResource("SimpleNem12.csv");

		Collection<MeterRead> list = simpleNem12Parser.parseSimpleNem12(resource.getFile());
		assertEquals(2, list.size(), "2 items expected");
	}
	
	@Test
	public void testParse_invalid_quality() throws IOException {
		Resource resource = new ClassPathResource("SimpleNem12_invalid_quality.csv");
		File file = resource.getFile();

		Assertions.assertThrows(IllegalArgumentException.class, () -> simpleNem12Parser.parseSimpleNem12(file));

	}
	

	@Test
	public void testParse_invalid_unit() throws IOException {
		Resource resource = new ClassPathResource("SimpleNem12_invalid_unit.csv");
		File file = resource.getFile();
		Assertions.assertThrows(IllegalArgumentException.class, () -> simpleNem12Parser.parseSimpleNem12(file));

	}

}
