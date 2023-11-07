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
 * @Date 2023-11-06 15:50:35 
 */

@Entity
@Table ( name ="jz_account" , schema = "")
public class JzAccount  implements Serializable {

	private static final long serialVersionUID =  3804109801684927158L;

	@Id
   	@Column(name = "jz_account_id" )
	private String jzAccountId;

   	@Column(name = "jz_account_ownerid" )
	private String jzAccountOwnerid;

   	@Column(name = "jz_account_name" )
	private String jzAccountName;

   	@Column(name = "jz_account_time" )
	private String jzAccountTime;

	public String getJzAccountId() {
		return this.jzAccountId;
	}

	public void setJzAccountId(String jzAccountId) {
		this.jzAccountId = jzAccountId;
	}

	public String getJzAccountOwnerid() {
		return this.jzAccountOwnerid;
	}

	public void setJzAccountOwnerid(String jzAccountOwnerid) {
		this.jzAccountOwnerid = jzAccountOwnerid;
	}

	public String getJzAccountName() {
		return this.jzAccountName;
	}

	public void setJzAccountName(String jzAccountName) {
		this.jzAccountName = jzAccountName;
	}

	public String getJzAccountTime() {
		return this.jzAccountTime;
	}

	public void setJzAccountTime(String jzAccountTime) {
		this.jzAccountTime = jzAccountTime;
	}

	@Override
	public String toString() {
		return "{" +
					"jzAccountId='" + jzAccountId + '\'' +
					"jzAccountOwnerid='" + jzAccountOwnerid + '\'' +
					"jzAccountName='" + jzAccountName + '\'' +
					"jzAccountTime='" + jzAccountTime + '\'' +
				'}';
	}

}
