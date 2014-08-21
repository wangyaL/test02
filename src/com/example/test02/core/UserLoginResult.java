package com.example.test02.core;

import com.example.test02.core.bean.User;
import com.example.test02.core.bean.UserInfo;

/**
 * 用户登录返回信息
 * @author lyw
 *
 */
public class UserLoginResult extends BaseOutput{
	private static final long serialVersionUID = 1L;
	private boolean isSuccess;
	private String info;
	private User userVO;
	
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
//	public UserInfo getUserVo() {
//		return userVo;
//	}
//	public void setUserVo(UserInfo userVo) {
//		this.userVo = userVo;
//	}
	public User getUserVO() {
		return userVO;
	}
	public void setUserVO(User userVO) {
		this.userVO = userVO;
	}
	
}
