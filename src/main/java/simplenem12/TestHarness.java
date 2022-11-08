// Copyright Red Energy Limited 2017

package simplenem12;

import java.io.File;
import java.util.Collection;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import simplenem12.impl.SimpleNem12LineProcessorImpl;
import simplenem12.impl.SimpleNem12ParserImpl;

/**
 * Simple test harness for trying out SimpleNem12Parser implementation
 */
@SpringBootApplication
public class TestHarness {

	public static void main(String[] args) {

		if (args.length == 1) {
			File simpleNem12File = new File(args[0]);

			Collection<MeterRead> meterReads = new SimpleNem12ParserImpl(new SimpleNem12LineProcessorImpl()).parseSimpleNem12(simpleNem12File);
			System.out.println(meterReads);

			MeterRead read6123456789 = meterReads.stream().filter(mr -> mr.getNmi().equals("6123456789")).findFirst()
					.get();
			System.out.println(String.format("Total volume for NMI 6123456789 is %f", read6123456789.getTotalVolume())); // Should
																															// be
																															// -36.84

			MeterRead read6987654321 = meterReads.stream().filter(mr -> mr.getNmi().equals("6987654321")).findFirst()
					.get();
			System.out.println(String.format("Total volume for NMI 6987654321 is %f", read6987654321.getTotalVolume()));  // Should
			// be
			// 14.33
		} else {
			System.out.println("please provide a nem data file path"); 

		}
	}

}
