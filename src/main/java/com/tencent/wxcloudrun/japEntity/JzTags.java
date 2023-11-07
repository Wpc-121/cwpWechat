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
 * @Date 2023-11-06 17:22:15 
 */

@Entity
@Table ( name ="jz_tags" , schema = "")
public class JzTags  implements Serializable {

	private static final long serialVersionUID =  3472507406880065594L;
	@Id
   	@Column(name = "tag_id" )
	private String tagId;

   	@Column(name = "tag_name" )
	private String tagName;

   	@Column(name = "tag_time" )
	private String tagTime;

   	@Column(name = "tag_ownerid" )
	private String tagOwnerid;

	public String getTagId() {
		return this.tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagTime() {
		return this.tagTime;
	}

	public void setTagTime(String tagTime) {
		this.tagTime = tagTime;
	}

	public String getTagOwnerid() {
		return this.tagOwnerid;
	}

	public void setTagOwnerid(String tagOwnerid) {
		this.tagOwnerid = tagOwnerid;
	}

	@Override
	public String toString() {
		return "{" +
					"tagId='" + tagId + '\'' +
					"tagName='" + tagName + '\'' +
					"tagTime='" + tagTime + '\'' +
					"tagOwnerid='" + tagOwnerid + '\'' +
				'}';
	}

}
