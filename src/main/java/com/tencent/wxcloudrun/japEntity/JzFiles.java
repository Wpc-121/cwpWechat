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
 * @Date 2023-10-20 11:26:57 
 */

@Entity
@Table ( name ="jz_files" , schema = "")
public class JzFiles  implements Serializable {

	private static final long serialVersionUID =  7374840074404573272L;

	@Id
   	@Column(name = "jz_id" )
	private String jzId;

   	@Column(name = "jz_file_url" )
	private String jzFileUrl;

   	@Column(name = "jz_file_time" )
	private String jzFileTime;

   	@Column(name = "jz_file_order" )
	private String jzFileOrder;

	public String getJzId() {
		return this.jzId;
	}

	public void setJzId(String jzId) {
		this.jzId = jzId;
	}

	public String getJzFileUrl() {
		return this.jzFileUrl;
	}

	public void setJzFileUrl(String jzFileUrl) {
		this.jzFileUrl = jzFileUrl;
	}

	public String getJzFileTime() {
		return this.jzFileTime;
	}

	public void setJzFileTime(String jzFileTime) {
		this.jzFileTime = jzFileTime;
	}

	public String getJzFileOrder() {
		return this.jzFileOrder;
	}

	public void setJzFileOrder(String jzFileOrder) {
		this.jzFileOrder = jzFileOrder;
	}

	@Override
	public String toString() {
		return "{" +
					"jzId='" + jzId + '\'' +
					"jzFileUrl='" + jzFileUrl + '\'' +
					"jzFileTime='" + jzFileTime + '\'' +
					"jzFileOrder='" + jzFileOrder + '\'' +
				'}';
	}

}
