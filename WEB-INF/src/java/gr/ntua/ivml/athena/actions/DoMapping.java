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


package gr.ntua.ivml.athena.actions;

import gr.ntua.ivml.athena.db.DB;
import gr.ntua.ivml.athena.persistent.DataUpload;
import gr.ntua.ivml.athena.persistent.Mapping;
import gr.ntua.ivml.athena.persistent.User;
import gr.ntua.ivml.athena.util.Config;
import gr.ntua.ivml.athena.xml.TreeGenerationParser;
import gr.ntua.ivml.athena.persistent.Lock;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


@Results({
	  @Result(name="input", location="editor.jsp"),
	  @Result(name="error", location="ImportSummary", type="redirectAction" ),
	  @Result(name="success", location="editor.jsp" )
	})

public class DoMapping extends GeneralAction  {

	protected final Logger log = Logger.getLogger(getClass());
	public String fileLoc;
	private long uploadId;
	private long mapid;
	private Lock lock;
	private long lockId;
	private String mapname;
	
	
	public long getUploadId() {
		return uploadId;
	}

	public void setUploadId(long uploadId) {
		this.uploadId = uploadId;
	}
	
	public long getMapid() {
		return mapid;
	}
	
	public String getMapname() {
		return DB.getMappingDAO().findById(mapid, false).getName();
	}
	
	
	public void setMapid(long mapid) {
		this.mapid = mapid;
	}

	public long getLockId() {
		return lockId;
	}
	

	
	@Action(value="DoMapping")
    public String execute() throws Exception {
			DataUpload du = DB.getDataUploadDAO().getById(getUploadId(), false);
			Mapping mp=DB.getMappingDAO().findById(getMapid(), false);
			if( du != null  && mp!=null)
			{
					lock=DB.getLockManager().directLock(getUser(), getSessionId(), mp );
			        if(lock!=null)	{
			        	this.lockId=lock.getDbID();
			  		return "success";}
			        else return "error";
				} else {
					addActionError("Couldn't acquire lock on Mapping!");
				}
			return "error";
    }

	public String getFileLoc(){
		
		fileLoc= Config.get("targetDefinition") ;
		return fileLoc;
	}
	
	@Action("DoMapping_input")
	@Override
	public String input() throws Exception {
    	if( (user.getOrganization() == null && !user.hasRight(User.SUPER_USER)) || !user.hasRight(User.MODIFY_DATA)) {
    		throw new IllegalAccessException( "No mapping rights!" );
    	}

		return super.input();
	}
	
	public String getUploadSchema() {
		log.debug( "getSchema called");
		TreeGenerationParser tgp = new TreeGenerationParser();
		DataUpload du = DB.getDataUploadDAO().findById(uploadId, false);
		try {
			return tgp.parseUpload(du);
		} catch( Exception e ) {
			log.error( "Problems with the DB",e );
		}
		return "damn";
	}
}