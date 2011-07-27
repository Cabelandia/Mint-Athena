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

package gr.ntua.ivml.athena.db;

import gr.ntua.ivml.athena.persistent.Mapping;
import gr.ntua.ivml.athena.persistent.Organization;

import java.util.List;

public class MappingDAO extends DAO<Mapping, Long> {
	
	/**
	 * Get all Mappings for that organization, even if organization is null.
	 * @param org
	 * @return
	 */
	public List<Mapping> findByOrganization( Organization org ) {
		if( org != null )
		return getSession().createQuery("from Mapping where organization=:org")
		.setEntity("org", org)
		.list();
		else 
			return getSession().createQuery("from Mapping where organization is null")
			.list();
	}
	
	public List<Mapping> findAllOrderOrg(  ) {
		
		return getSession().createQuery("from Mapping order by organization asc")
		
		.list();
		}
}
