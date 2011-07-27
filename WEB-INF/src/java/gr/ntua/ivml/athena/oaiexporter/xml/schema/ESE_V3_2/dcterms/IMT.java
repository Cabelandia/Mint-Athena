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
// IMT.java
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

package gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.dcterms;


public class IMT extends com.altova.xml.TypeBase
{
		public static com.altova.xml.meta.ComplexType getStaticInfo() { return new com.altova.xml.meta.ComplexType(gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getTypes()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_ti_dcterms_altova_IMT]); }
	
	public IMT(org.w3c.dom.Node init)
	{
		super(init);
		instantiateMembers();
	}
	
	private void instantiateMembers()
	{
		lang = new MemberAttribute_lang (this, gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getMembers()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_mi_dcterms_altova_IMT._lang]);

	}
	// Attributes
	public String getValue() 
	{ 
		com.altova.typeinfo.MemberInfo member = gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getMembers()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_mi_dcterms_altova_IMT._unnamed];
		return (String)com.altova.xml.XmlTreeOperations.castToString(getNode(), member);
	}
	
	public void setValue(String value)
	{
		com.altova.typeinfo.MemberInfo member = gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo.binder.getMembers()[gr.ntua.ivml.athena.oaiexporter.xml.schema.ESE_V3_2.ESE_V3_2_TypeInfo._altova_mi_dcterms_altova_IMT._unnamed];
		com.altova.xml.XmlTreeOperations.setValue(getNode(), member, value);
	}
	
	public MemberAttribute_lang lang;
		public static class MemberAttribute_lang
		{
			private com.altova.xml.TypeBase owner;
			private com.altova.typeinfo.MemberInfo info; 
			public MemberAttribute_lang (com.altova.xml.TypeBase owner, com.altova.typeinfo.MemberInfo info) {this.owner = owner; this.info = info;}
			public String getValue() {
				return (String)com.altova.xml.XmlTreeOperations.castToString(com.altova.xml.XmlTreeOperations.findAttribute(owner.getNode(), info), info);
			}
			public void setValue(String value) {
				com.altova.xml.XmlTreeOperations.setValue(owner.getNode(), info, value);		
			}
			public boolean exists() {return owner.getAttribute(info) != null;}
			public void remove() {owner.removeAttribute(info);} 
			public com.altova.xml.meta.Attribute getInfo() {return new com.altova.xml.meta.Attribute(info);}
		}


	// Elements

		public void setXsiType() {com.altova.xml.XmlTreeOperations.setAttribute(getNode(), "http://www.w3.org/2001/XMLSchema-instance", "xsi:type", "http://purl.org/dc/terms/", "IMT");}
}
