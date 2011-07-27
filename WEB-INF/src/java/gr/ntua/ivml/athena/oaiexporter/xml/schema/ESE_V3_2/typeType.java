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

////////////////////////////////////////////////////////////////////////
//
// typeType.java
//
// This file was generated by XMLSpy 2008r2 Enterprise Edition.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the XMLSpy Documentation for further details.
// http://www.altova.com/xmlspy
//
////////////////////////////////////////////////////////////////////////

package gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2;


public class typeType extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getTypes()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_ti_altova_typeType]); }
	
	public typeType(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{

	}
	// Attributes
	public String getValue() 
	{ 
		com.altova.typeinfo.MemberInfo member = gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getMembers()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_mi_altova_typeType._unnamed];
		return (String)com.altova.xml.XmlTreeOperations.castToString(getNode(), member);
	}
	
	public void setValue(String value)
	{
		com.altova.typeinfo.MemberInfo member = gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getMembers()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_mi_altova_typeType._unnamed];
		com.altova.xml.XmlTreeOperations.setValue(getNode(), member, value);
	}
	


	// Elements
}
