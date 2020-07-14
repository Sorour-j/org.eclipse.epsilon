/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.eclipse.epsilon.common.util.OperatingSystem;

/**
 * Utility class for using external programs in transforming content.
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class ExternalContentTransformation implements Runnable, Callable<byte[]> {

	public final String program, args[];
	
	public Duration timeout = Duration.ofSeconds(30);
	
	protected Path logFile;
	
	public final Path outputFile;
	
	private Exception exception;
	
	private int resultCode = Integer.MIN_VALUE;
	
	private boolean hasRun = false;

	public ExternalContentTransformation(Path outputFile, String program, Object... arguments) {
		this.outputFile = outputFile;
		
		if (arguments == null) {
			this.args = new String[0];
		}
		else {
			this.args = new String[arguments.length];
			for (int i = 0; i < arguments.length; this.args[i] = ""+arguments[i++]);
		}
		
		if (OperatingSystem.isMac()) {
			this.program = "/usr/local/bin/" + program;
		}
		else if (OperatingSystem.isUnix()) {
			this.program = "/usr/bin/" + program;
		}
		else {
			this.program = program;
		}
	}
	
	/**
	 * Convenience method for temporary files used in intermediate transformations.
	 * 
	 * @param extension The file extension.
	 * @param contents The initial contents of the file. May be null.
	 * @return The absolute path of the temporary file.
	 * @throws IOException If the temp file couldn't be created.
	 */
	public static Path createTempFile(String extension, byte[] contents) throws IOException {
		Path file = Files.createTempFile(Files.createTempDirectory("picto"), "picto-renderer", extension);
		Path result = contents != null && contents.length > 0 ? Files.write(file, contents) : file;
		return result.toAbsolutePath();
	}
	
	public int getResultCode() {
		screenRun();
		return resultCode;
	}
	
	protected void screenRun() throws IllegalStateException {
		if (!hasRun) {
			throw new IllegalStateException("Must run program first!");
		}
	}
	
	public byte[] getResult() throws IOException {
		screenRun();
		Path result = exception == null && outputFile != null && outputFile.toFile().exists() ? outputFile : logFile;
		return Files.readAllBytes(result);
	}
	
	@Override
	public final void run() {
		try {
			call();
		}
		catch (Exception ex) {
			this.exception = ex;
		}
		hasRun = true;
	}
	
	@Override
	public byte[] call() throws IOException {
		hasRun = false;
		String[] programAndArgs = args != null ?
			new String[args.length + 1] :
			new String[]{program};
			
		if (programAndArgs.length > 1) {
			programAndArgs[0] = program;
			for (int i = 0; i < args.length; programAndArgs[i+1] = args[i++]);
		}
		
		ProcessBuilder pb = new ProcessBuilder(programAndArgs);
		logFile = createTempFile(".log", null);
		pb.redirectError(logFile.toFile());
		try {
			Process process = pb.start();
			if (process.waitFor(timeout != null ? timeout.toMillis() : Long.MAX_VALUE, TimeUnit.MILLISECONDS)) {
				resultCode = process.exitValue();
			}
		}
		catch (InterruptedException ie) {
			throw new IOException(ie);
		}
		hasRun = true;
		return getResult();
	}
	
	public Exception getException() {
		return exception;
	}
}
