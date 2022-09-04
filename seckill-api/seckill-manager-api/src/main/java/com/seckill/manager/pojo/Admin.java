package com.seckill.manager.pojo;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:www.itheima.com
 * @Description:Admin构建
 * @Date  19:13
 *****/
@Table(name="tb_admin")
public class Admin implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;//

    @Column(name = "login_name")
	private String loginName;//用户名

    @Column(name = "password")
	private String password;//密码

    @Column(name = "status")
	private String status;//状态



	//get方法
	public Integer getId() {
		return id;
	}

	//set方法
	public void setId(Integer id) {
		this.id = id;
	}
	//get方法
	public String getLoginName() {
		return loginName;
	}

	//set方法
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	//get方法
	public String getPassword() {
		return password;
	}

	//set方法
	public void setPassword(String password) {
		this.password = password;
	}
	//get方法
	public String getStatus() {
		return status;
	}

	//set方法
	public void setStatus(String status) {
		this.status = status;
	}


}
