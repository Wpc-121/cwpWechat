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
 * @Date 2023-10-20 11:37:03 
 */

@Entity
@Table ( name ="jz_life_show" , schema = "")
public class JzLifeShow  implements Serializable {

	private static final long serialVersionUID =  3524331311015623124L;

	@Id
   	@Column(name = "life_show_id" )
	private String lifeShowId;

   	@Column(name = "life_show_time" )
	private String lifeShowTime;

   	@Column(name = "life_show_text" )
	private String lifeShowText;

	/**
	 * 1-private 2- public 3-friends
	 */
   	@Column(name = "life_show_see" )
	private String lifeShowSee;

	/**
	 * 1-release 2-draft
	 */
   	@Column(name = "life_show_status" )
	private String lifeShowStatus;

   	@Column(name = "life_show_medinum" )
	private String lifeShowMedinum;

   	@Column(name = "life_show_ownerid" )
	private String lifeShowOwnerid;

	public String getLifeShowId() {
		return this.lifeShowId;
	}

	public void setLifeShowId(String lifeShowId) {
		this.lifeShowId = lifeShowId;
	}

	public String getLifeShowTime() {
		return this.lifeShowTime;
	}

	public void setLifeShowTime(String lifeShowTime) {
		this.lifeShowTime = lifeShowTime;
	}

	public String getLifeShowText() {
		return this.lifeShowText;
	}

	public void setLifeShowText(String lifeShowText) {
		this.lifeShowText = lifeShowText;
	}

	public String getLifeShowSee() {
		return this.lifeShowSee;
	}

	public void setLifeShowSee(String lifeShowSee) {
		this.lifeShowSee = lifeShowSee;
	}

	public String getLifeShowStatus() {
		return this.lifeShowStatus;
	}

	public void setLifeShowStatus(String lifeShowStatus) {
		this.lifeShowStatus = lifeShowStatus;
	}

	public String getLifeShowMedinum() {
		return this.lifeShowMedinum;
	}

	public void setLifeShowMedinum(String lifeShowMedinum) {
		this.lifeShowMedinum = lifeShowMedinum;
	}

	public String getLifeShowOwnerid() {
		return this.lifeShowOwnerid;
	}

	public void setLifeShowOwnerid(String lifeShowOwnerid) {
		this.lifeShowOwnerid = lifeShowOwnerid;
	}

	@Override
	public String toString() {
		return "{" +
					"lifeShowId='" + lifeShowId + '\'' +
					"lifeShowTime='" + lifeShowTime + '\'' +
					"lifeShowText='" + lifeShowText + '\'' +
					"lifeShowSee='" + lifeShowSee + '\'' +
					"lifeShowStatus='" + lifeShowStatus + '\'' +
					"lifeShowMedinum='" + lifeShowMedinum + '\'' +
					"lifeShowOwnerid='" + lifeShowOwnerid + '\'' +
				'}';
	}

}
