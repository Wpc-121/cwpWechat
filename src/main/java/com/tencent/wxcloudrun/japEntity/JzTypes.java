package com.tencent.wxcloudrun.japEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

/**
 * @Description  
 * @Author  linmengmeng
 * @Date 2023-09-22 16:21:46 
 */

@Entity
@Table ( name ="jz_types" , schema = "")
public class JzTypes  implements Serializable {

	private static final long serialVersionUID =  1952697158711992001L;

	@Id
   	@Column(name = "typeid" )
	private String typeid;

   	@Column(name = "typename" )
	private String typename;

   	@Column(name = "typeicon" )
	private String typeicon;

	/**
	 * 1-out 2-in
	 */
   	@Column(name = "typedir" )
	private String typedir;

	/**
	 * 1-有子类型  0-没有子类型
	 */
   	@Column(name = "typefarther" )
	private String typefarther;

   	@Column(name = "typefartherid" )
	private String typefartherid;

   	@Column(name = "typeiconname" )
	private String typeiconname;

   	@Column(name = "typeaddtime" )
	private String typeaddtime;

   	@Column(name = "typeowner" )
	private String typeowner;

	/**
	 * 1-支出  2-收入
	 */
   	@Column(name = "type" )
	private String type;

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypeicon() {
		return this.typeicon;
	}

	public void setTypeicon(String typeicon) {
		this.typeicon = typeicon;
	}

	public String getTypedir() {
		return this.typedir;
	}

	public void setTypedir(String typedir) {
		this.typedir = typedir;
	}

	public String getTypefarther() {
		return this.typefarther;
	}

	public void setTypefarther(String typefarther) {
		this.typefarther = typefarther;
	}

	public String getTypefartherid() {
		return this.typefartherid;
	}

	public void setTypefartherid(String typefartherid) {
		this.typefartherid = typefartherid;
	}

	public String getTypeiconname() {
		return this.typeiconname;
	}

	public void setTypeiconname(String typeiconname) {
		this.typeiconname = typeiconname;
	}

	public String getTypeaddtime() {
		return this.typeaddtime;
	}

	public void setTypeaddtime(String typeaddtime) {
		this.typeaddtime = typeaddtime;
	}
	public String getTypeowner() {
		return this.typeowner;
	}

	public void setTypeowner(String typeowner) {
		this.typeowner = typeowner;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "{" +
					"typeid='" + typeid + '\'' +
					"typename='" + typename + '\'' +
					"typeicon='" + typeicon + '\'' +
					"typedir='" + typedir + '\'' +
					"typefarther='" + typefarther + '\'' +
					"typefartherid='" + typefartherid + '\'' +
					"typeiconname='" + typeiconname + '\'' +
					"typeaddtime='" + typeaddtime + '\'' +
					"type='" + type + '\'' +
				'}';
	}

}
