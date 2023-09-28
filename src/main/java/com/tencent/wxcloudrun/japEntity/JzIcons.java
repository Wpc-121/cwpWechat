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
 * @Date 2023-09-25 10:19:35 
 */

@Entity
@Table ( name ="jz_icons" , schema = "")
public class JzIcons  implements Serializable {

	private static final long serialVersionUID =  5092788589199945820L;

	@Id
   	@Column(name = "jz_iconid" )
	private String jzIconid;

   	@Column(name = "jz_iconurl" )
	private String jzIconurl;

   	@Column(name = "jz_icon_owner" )
	private String jzIconOwner;

   	@Column(name = "jz_time" )
	private String jzTime;

   	@Column(name = "jz_base64" )
	private String jzBase64;

	public String getJzIconid() {
		return this.jzIconid;
	}

	public void setJzIconid(String jzIconid) {
		this.jzIconid = jzIconid;
	}

	public String getJzIconurl() {
		return this.jzIconurl;
	}

	public void setJzIconurl(String jzIconurl) {
		this.jzIconurl = jzIconurl;
	}

	public String getJzIconOwner() {
		return this.jzIconOwner;
	}

	public void setJzIconOwner(String jzIconOwner) {
		this.jzIconOwner = jzIconOwner;
	}

	public String getJzTime() {
		return this.jzTime;
	}

	public void setJzTime(String jzTime) {
		this.jzTime = jzTime;
	}

	public String getJzBase64() {
		return this.jzBase64;
	}

	public void setJzBase64(String jzBase64) {
		this.jzBase64 = jzBase64;
	}

	@Override
	public String toString() {
		return "{" +
					"jzIconid='" + jzIconid + '\'' +
					"jzIconurl='" + jzIconurl + '\'' +
					"jzIconOwner='" + jzIconOwner + '\'' +
					"jzTime='" + jzTime + '\'' +
					"jzBase64='" + jzBase64 + '\'' +
				'}';
	}

}
