package com.atomikos.persistence.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import com.atomikos.logging.Logger;
import com.atomikos.logging.LoggerFactory;
import com.atomikos.persistence.LogException;

public class LogFileLock {
	
	private static Logger LOGGER = LoggerFactory.createLogger(LogFileLock.class);
	
	private File lockfileToPreventDoubleStartup_;
	private FileOutputStream lockfilestream_ = null;
	private FileLock lock_ = null;

	private String dir;

	private String fileName;

	public LogFileLock(String dir, String fileName) {
		this.dir = dir;
		this.fileName = fileName;
	}

	public void acquireLock() throws LogException {
		try {
			lockfileToPreventDoubleStartup_ = new File(dir + fileName + ".lck");
			lockfilestream_ = new FileOutputStream(lockfileToPreventDoubleStartup_);
			lock_ = lockfilestream_.getChannel().tryLock();
			lockfileToPreventDoubleStartup_.deleteOnExit();
		} catch (OverlappingFileLockException failedToGetLock) {
			// happens on windows
			lock_ = null;
		} catch (IOException failedToGetLock) {
			// happens on windows
			lock_ = null;
		}
		if (lock_ == null) {
			LOGGER.logWarning("ERROR: the specified log seems to be " + "in use already. Make sure that no other instance is " + "running, or kill any pending process if needed.");
			throw new LogException("Log already in use?");
		}
	}

	public void releaseLock() {
		try {
			if (lock_ != null) {
				lock_.release();
			}
			if (lockfilestream_ != null)
				lockfilestream_.close();
		} catch (IOException e) {
			LOGGER.logWarning("Error releasing file lock: " + e.getMessage());
		} finally {
			lock_ = null;
		}

		if (lockfileToPreventDoubleStartup_ != null) {
			lockfileToPreventDoubleStartup_.delete();
			lockfileToPreventDoubleStartup_ = null;
		}
	}
	
}
