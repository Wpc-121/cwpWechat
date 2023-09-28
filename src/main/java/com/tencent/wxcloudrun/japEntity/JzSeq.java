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
 * @Date 2023-09-21 11:00:06 
 */

@Entity
@Table ( name ="jz_seq" , schema = "")
public class JzSeq  implements Serializable {

	private static final long serialVersionUID =  1696836759347265924L;

	@Id
   	@Column(name = "name" )
	private String name;

   	@Column(name = "current_value" )
	private Long currentValue;

   	@Column(name = "increment" )
	private Long increment;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCurrentValue() {
		return this.currentValue;
	}

	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	public Long getIncrement() {
		return this.increment;
	}

	public void setIncrement(Long increment) {
		this.increment = increment;
	}

	@Override
	public String toString() {
		return "{" +
					"name='" + name + '\'' +
					"currentValue='" + currentValue + '\'' +
					"increment='" + increment + '\'' +
				'}';
	}

}
