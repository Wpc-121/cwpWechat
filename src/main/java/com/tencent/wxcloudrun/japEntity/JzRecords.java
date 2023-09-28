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
 * @Date 2023-09-27 16:59:08 
 */

@Entity
@Table ( name ="jz_records" , schema = "")
public class JzRecords  implements Serializable {

	private static final long serialVersionUID =  8724368485081756239L;

	@Id
   	@Column(name = "rec_id" )
	private String recId;

	/**
	 * 1-shouru 2-zhichu
	 */
   	@Column(name = "rec_type" )
	private String recType;

   	@Column(name = "rec_jz_type_id" )
	private String recJzTypeId;

   	@Column(name = "rec_time" )
	private String recTime;

   	@Column(name = "rec_date" )
	private String recDate;

   	@Column(name = "rec_mark" )
	private String recMark;

   	@Column(name = "rec_born_time" )
	private String recBornTime;

   	@Column(name = "rec_money" )
	private String recMoney;

   	@Column(name = "rec_ownerid" )
	private String recOwnerid;

   	@Column(name = "rec_del" )
	private String recDel;

	public String getRecId() {
		return this.recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getRecType() {
		return this.recType;
	}

	public void setRecType(String recType) {
		this.recType = recType;
	}

	public String getRecJzTypeId() {
		return this.recJzTypeId;
	}

	public void setRecJzTypeId(String recJzTypeId) {
		this.recJzTypeId = recJzTypeId;
	}

	public String getRecTime() {
		return this.recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

	public String getRecDate() {
		return this.recDate;
	}

	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}

	public String getRecMark() {
		return this.recMark;
	}

	public void setRecMark(String recMark) {
		this.recMark = recMark;
	}

	public String getRecBornTime() {
		return this.recBornTime;
	}

	public void setRecBornTime(String recBornTime) {
		this.recBornTime = recBornTime;
	}

	public String getRecMoney() {
		return this.recMoney;
	}

	public void setRecMoney(String recMoney) {
		this.recMoney = recMoney;
	}

	public String getRecOwnerid() {
		return this.recOwnerid;
	}

	public void setRecOwnerid(String recOwnerid) {
		this.recOwnerid = recOwnerid;
	}

	public String getRecDel() {
		return this.recDel;
	}

	public void setRecDel(String recDel) {
		this.recDel = recDel;
	}

	@Override
	public String toString() {
		return "{" +
					"recId='" + recId + '\'' +
					"recType='" + recType + '\'' +
					"recJzTypeId='" + recJzTypeId + '\'' +
					"recTime='" + recTime + '\'' +
					"recDate='" + recDate + '\'' +
					"recMark='" + recMark + '\'' +
					"recBornTime='" + recBornTime + '\'' +
					"recMoney='" + recMoney + '\'' +
					"recOwnerid='" + recOwnerid + '\'' +
					"recDel='" + recDel + '\'' +
				'}';
	}

}
