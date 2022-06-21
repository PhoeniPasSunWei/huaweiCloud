package com.sunwei.demo.secKillDemo.SeckillUserS.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;

import com.sunwei.demo.easyExcel.Annotation.Excel;
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
 * @Date:   2022-04-26 07:42:247.057
 * @Version: V1.0
 */
@Data
@TableName("seckill_user")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "seckill_user对象", description = "用户表")
public class SeckillUser implements Serializable {
    private static final long serialVersionUID = 6497432299266399110L;

	/**唯一标识,手机号码*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;

	/**姓名*/
    @Excel(name = "姓名", width = 15)
	@ApiModelProperty(value = "姓名")
	private String nikename;

	/**密码*/
    @Excel(name = "密码", width = 15)
	@ApiModelProperty(value = "密码")
	private String password;

	/**盐*/
    @Excel(name = "盐", width = 15)
	@ApiModelProperty(value = "盐")
	private String slat;

	/**头像*/
    @Excel(name = "头像", width = 15)
	@ApiModelProperty(value = "头像")
	private String head;

	/**注册时间*/
    @Excel(name = "注册时间", width = 15)
	@ApiModelProperty(value = "注册时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date registerDate;

	/**最后一次登陆时间*/
    @Excel(name = "最后一次登陆时间", width = 15)
	@ApiModelProperty(value = "最后一次登陆时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date lastLoginDate;

	/**登陆次数*/
    @Excel(name = "登陆次数", width = 15)
	@ApiModelProperty(value = "登陆次数")
	private Integer loginCount;

}
