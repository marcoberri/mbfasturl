/*
 * 
 *  Classe per la lettura di file di grossa mole
 *  grazie a http://code.hammerpig.com/how-to-read-really-large-files-in-java.html
 */

package it.marcoberri.mbfasturl.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

/**
 * 
 * @author marco
 */
public class BigFile implements Iterable<String> {
    private class FileIterator implements Iterator<String> {
	private String _currentLine;

	@Override
	public boolean hasNext() {
	    try {
		_currentLine = _reader.readLine();
	    } catch (Exception ex) {
		_currentLine = null;
		ex.printStackTrace();
	    }

	    return _currentLine != null;
	}

	@Override
	public String next() {
	    return _currentLine;
	}

	@Override
	public void remove() {
	}
    }

    private BufferedReader _reader;

    /**
     * 
     * @param filePath
     * @throws Exception
     */
    public BigFile(String filePath) throws Exception {
	_reader = new BufferedReader(new FileReader(filePath));
    }

    /**
     *
     */
    public void Close() {
	try {
	    _reader.close();
	} catch (Exception ex) {
	}
    }

    /**
     * 
     * @return
     */
    @Override
    public Iterator<String> iterator() {
	return new FileIterator();
    }
}
