package com.sunwei.demo.secKillDemo.SeckillUserS.vo;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 用户表
 * @Author: 孙伟
 * @Date:   2022-04-26 07:42:273.057
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "seckill_user视图对象", description = "用户表")
public class SeckillUserVo implements Serializable {
    private static final long serialVersionUID = 4957992520342539678L;

	/**唯一标识,手机号码*/
	//springboot中参数校验（validation）使用 list查询的时候回校验报错
	@NotNull(message = "id不能为null")
	private String id;

	/**姓名*/
	@ApiModelProperty(value = "姓名")
	private String nikename;

	/**密码*/
	@ApiModelProperty(value = "密码")
	private String password;

	/**盐*/
	@ApiModelProperty(value = "盐")
	private String slat;

	/**头像*/
	@ApiModelProperty(value = "头像")
	private String head;

	/**注册时间*/
	@ApiModelProperty(value = "注册时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date registerDate;

	/**最后一次登陆时间*/
	@ApiModelProperty(value = "最后一次登陆时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginDate;

	/**登陆次数*/
	@ApiModelProperty(value = "登陆次数")
	private Integer loginCount;

}
