package simplenem12;

import java.util.Collection;

public interface LineProcessor {

	public void process(String str);

	public Collection<MeterRead> getMeterReads();

	public int getLineNumber();

}
