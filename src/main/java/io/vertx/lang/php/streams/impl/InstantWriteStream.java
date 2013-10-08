package io.vertx.lang.php.streams.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.caucho.vfs.StreamImpl;
import com.caucho.vfs.WriteStream;

public class InstantWriteStream extends WriteStream{

	public InstantWriteStream(StreamImpl source) {
		super(source);
	}
	
	@Override
	public void write(int ch) throws IOException {
		super.write(ch);
		super.flush();
	}
	
	public void write(byte []buf, int offset, int length) throws IOException {
		super.write(buf, offset, length);
	    super.flush();
	}
	
	public long writeStream(InputStream source) throws IOException {
		long returnValue = super.writeStream(source);
		super.flush();
		return returnValue;
	}
	
	public void writeStream(Reader reader) throws IOException {
		super.writeStream(reader);
		super.flush();
	}
	
	public void writeStream(InputStream source, int totalLength)
		    throws IOException {
		super.writeStream(source, totalLength);
		super.flush();
	}
	
	public void writeStream(StreamImpl source) throws IOException {
		super.writeStream(source);
		super.flush();
	}
}
