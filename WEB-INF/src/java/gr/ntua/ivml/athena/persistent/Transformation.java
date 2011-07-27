/*
This file is part of mint-athena. mint-athena services compose a web based platform that facilitates aggregation of cultural heritage metadata.
   Copyright (C) <2009-2011> Anna Christaki, Arne Stabenau, Costas Pardalis, Fotis Xenikoudakis, Nikos Simou, Nasos Drosopoulos, Vasilis Tzouvaras

   mint-athena program is free software: you can redistribute it and/or
modify
   it under the terms of the GNU Affero General Public License as
   published by the Free Software Foundation, either version 3 of the
   License, or (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package gr.ntua.ivml.athena.persistent;

import gr.ntua.ivml.athena.db.DB;
import gr.ntua.ivml.athena.persistent.DataUpload.EntryProcessor;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import de.schlichtherle.util.zip.BasicZipFile;
import de.schlichtherle.util.zip.ZipEntry;
import de.schlichtherle.util.zip.ZipOutputStream;

public class Transformation {
	public static final Logger log = Logger.getLogger(Transformation.class );
	
	Long dbID;
	Date beginTransform, endTransform;
	User user;
	DataUpload dataUpload;
	Mapping mapping;
	BlobWrap zippedOutput;
	int statusCode;
	String statusMessage;
	XmlObject parsedOutput;
	String jsonMapping;

	// transient, contains a zip archive with all the output files
	File tmpFile;
	MyZipOutputStream zippedOutputStream;
	int runningOutputNumber;
	
	public static final int OK = 0;
	public static final int ERROR = -1;
	public static final int IDLE = 1;
	public static final int WRITING = 2;
	public static final int UPLOADING = 3;
	public static final int INDEXING = 4;
	
	/**
	 * 
	 * @author Arne Stabenau 
	 *	Overwrite close so that users of the class can close their stream without closing 
	 *	the ZipOutputStream. 
	 */
	public static class MyZipOutputStream extends ZipOutputStream {
		public MyZipOutputStream(OutputStream out)  {
			super(out);
		}
		public void close() throws IOException {
			super.closeEntry();
		}
		public void finished() throws IOException {
			super.close();
		}
	}
	
	public Long getDbID() {
		return dbID;
	}
	public void setDbID(Long dbID) {
		this.dbID = dbID;
	}

	/**
	 * If the json string changed, the transformation is stale ...
	 * @return
	 */
	public boolean isStale() {
		if(getMapping().getJsonString()!=null){
		try {
			return !getMapping().getJsonString().equals(getJsonMapping());
		} catch( Exception e ) {
			log.error( "on is stale check", e );
		}
		}
		return false;
	}
	
	public String getJsonMapping() {
		return jsonMapping;
	}
	public void setJsonMapping(String jsonMapping) {
		this.jsonMapping = jsonMapping;
	}

	public XmlObject getParsedOutput() {
		return parsedOutput;
	}
	public void setParsedOutput(XmlObject parsedOutput) {
		this.parsedOutput = parsedOutput;
	}
	public Date getBeginTransform() {
		return beginTransform;
	}
	public void setBeginTransform(Date beginTransform) {
		this.beginTransform = beginTransform;
	}
	public Date getEndTransform() {
		return endTransform;
	}
	public void setEndTransform(Date endTransform) {
		this.endTransform = endTransform;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public DataUpload getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(DataUpload dataUpload) {
		this.dataUpload = dataUpload;
	}
	public Mapping getMapping() {
		return mapping;
	}
	public void setMapping(Mapping mapping) {
		this.mapping = mapping;
	}
	public BlobWrap getZippedOutput() {
		return zippedOutput;
	}
	public void setZippedOutput(BlobWrap zippedOutput) {
		this.zippedOutput = zippedOutput;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	/**
	 * Call this before you start appending output. It creates a standard XML header,
	 * since the Transformations are not having one any more!
	 */
	public void startOutput() {
		try {
			tmpFile = File.createTempFile("AthenaOutput", ".xml.zip");
			zippedOutputStream = new MyZipOutputStream(new FileOutputStream(tmpFile));
			ZipEntry entry = new ZipEntry("Transformation_"+getDbID()+"/");
			zippedOutputStream.putNextEntry(entry);
			setStatusCode(Transformation.WRITING);
		} catch( Exception e ) {
			setStatusCode(ERROR);
			setStatusMessage(e.getMessage());
			log.error( "Couldnt open tmp file for Transformation output");
		}
	}
	
	/**
	 * Output to here
	 * @param output
	 */
	public void appendOutput( String output ) {
		try {
			setStatusCode(  WRITING );
			zippedOutputStream.write(output.getBytes("UTF8"));
		} catch( IOException ie ) {
			log.error( "Can't append output, now thats wired" );
			setStatusCode(ERROR);
			setStatusMessage( ie.getMessage() );
		}
	}
	
	/**
	 * Move output to next file, filenames are numbered.
	 */
	public void nextOutputFile() throws Exception {
		ZipEntry entry =new ZipEntry( "Transformation_" + getDbID() + "/Output_"+runningOutputNumber+".xml" );		
		zippedOutputStream.putNextEntry(entry );
		runningOutputNumber += 1 ;
	}
	
	/**
	 * Get a writer to the output file. You have to close it before you declare the
	 * output finished !!
	 * @return
	 */
	public Writer getWriterToOutput() {
		Writer result = null;
		try {
			setStatusCode(WRITING);
			OutputStreamWriter osw = new OutputStreamWriter(zippedOutputStream, "UTF8");
			result = new BufferedWriter( osw );
		} catch( Exception e ) {
			log.error( "Desaster", e  );
		}
		return result;
	}
	
	public OutputStream getStreamToOutput() {
		OutputStream result = null;
		try {
			setStatusCode(WRITING);
			result = new BufferedOutputStream(zippedOutputStream);
		} catch( Exception e ) {
			log.error( "Desaster", e  );
		}
		return result;		
	}
	/**
	 * Closes the output and writes it to database
	 */
	public void finishOutput() {
		try {
		// gzipping the tmp file
			zippedOutputStream.finished();
			
			setStatusCode(UPLOADING);
			DB.commit();
			// uploading into the db
			if( zippedOutput != null ) {
				DB.getSession().delete(zippedOutput);
			}
			zippedOutput = new BlobWrap();
			zippedOutput.data = Hibernate.createBlob( new FileInputStream( tmpFile ), (int) tmpFile.length());
			DB.commit();
			log.debug( "Successfully written output to DB");

		} catch( IOException ie ) {
			log.error( "finishing output failed" );
			setStatusCode(ERROR);
			setStatusMessage(ie.getMessage());
			DB.commit();
		}
	}

	/**
	 * Returns a stream to a zip archive.
	 * @return
	 */
	public InputStream getDownloadStream() {
		InputStream is = null;		
		if( tmpFile == null )
			unloadToTmpFile();
		try {
			is = new FileInputStream(tmpFile);
		} catch( Exception e ) {
			log.error( "File unload problem", e);
		}
		return is;
	}
	
		public void processAllEntries( EntryProcessor ep ) throws Exception {
			InputStream is = null;		
			de.schlichtherle.util.zip.ZipEntry ze = null;
			if( tmpFile == null )
				unloadToTmpFile();
			try {
				BasicZipFile bz = new BasicZipFile( tmpFile );
				Enumeration entries = bz.entries();
				while( entries.hasMoreElements() ) {
					ze = (de.schlichtherle.util.zip.ZipEntry) entries.nextElement();
					InputStream zis = bz.getInputStream(ze);
					// log.debug( "Processing " + ze.getName());
					try {
						ep.processEntry(ze, zis);
					} catch( Exception e ) {
						log.info( "Problematic file: " + ze.getName());
					}
				}
			} catch( Exception e ) {
					log.error( "Problem reading output", e );
			} finally {
				try { is.close(); } catch( Exception e ){};
			}
		}
	
	public void unloadToTmpFile() {
		try {
			tmpFile = File.createTempFile("unloadOutput", ".zip");
			tmpFile.deleteOnExit();
			log.info( "Unloading to " + tmpFile.getAbsolutePath());
			FileOutputStream fos = new FileOutputStream( tmpFile );
			BufferedOutputStream bos = new BufferedOutputStream( fos,4096 );
			
			InputStream is = getZippedOutput().getData().getBinaryStream();
			IOUtils.copy(is, bos);
			is.close();
			bos.flush();
			bos.close();
			DB.commit();
		} catch( Exception e ) {
			log.error( "Cannot copy BLOB to tmp file", e );
		}
	}

	/**
	 * If you are finished reading the output, it would be nice to
	 * remove the tmp file ..
	 */
	public void clearTmpFile() {
		if( tmpFile != null ) {
			tmpFile.delete();
			tmpFile = null;
		}
	}
	
	/**
	 * You can use this after you unloadToTmpFile()
	 * @return
	 */
	public File getTmpFile() {
		return tmpFile;
	}
}
