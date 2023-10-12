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
 * @Date 2023-10-12 17:06:11 
 */

@Entity
@Table ( name ="jz_users" , schema = "")
public class JzUsers  implements Serializable {

	private static final long serialVersionUID =  7590550043187131675L;

	/**
	 * 用户openid
	 */
	@Id
   	@Column(name = "useropenid" )
	private String useropenid;

   	@Column(name = "username" )
	private String username;

   	@Column(name = "userphone" )
	private String userphone;

   	@Column(name = "userregtime" )
	private String userregtime;

   	@Column(name = "userlatesttime" )
	private String userlatesttime;

   	@Column(name = "userava" )
	private String userava;

	public String getUseropenid() {
		return this.useropenid;
	}

	public void setUseropenid(String useropenid) {
		this.useropenid = useropenid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserphone() {
		return this.userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

	public String getUserregtime() {
		return this.userregtime;
	}

	public void setUserregtime(String userregtime) {
		this.userregtime = userregtime;
	}

	public String getUserlatesttime() {
		return this.userlatesttime;
	}

	public void setUserlatesttime(String userlatesttime) {
		this.userlatesttime = userlatesttime;
	}

	public String getUserava() {
		return this.userava;
	}

	public void setUserava(String userava) {
		this.userava = userava;
	}

	@Override
	public String toString() {
		return "{" +
					"useropenid='" + useropenid + '\'' +
					"username='" + username + '\'' +
					"userphone='" + userphone + '\'' +
					"userregtime='" + userregtime + '\'' +
					"userlatesttime='" + userlatesttime + '\'' +
					"userava='" + userava + '\'' +
				'}';
	}

}
