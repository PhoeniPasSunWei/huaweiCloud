package com.sunwei.demo.easyExcel.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sunwei.demo.easyExcel.Annotation.Excel;
import com.sunwei.demo.easyExcel.Annotation.IdType;
import com.sunwei.demo.easyExcel.Annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 点表子表
 * @Author: 张思楠
 * @Date:   2022-03-15 05:51:460.050
 * @Version: V1.0
 */
@Data
@TableName("T_POINT_LIST_SUB")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "T_POINT_LIST_SUB对象", description = "点表子表")
public class TPointListSub implements Serializable {
    private static final long serialVersionUID = 3961849317413433654L;


	/**唯一标识编码*/
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;

	/**专业编码*/
	@ApiModelProperty(value = "专业编码")
	private String profession;

	/**专业名称*/
    @Excel(name = "专业名称", width = 15 )
	@ApiModelProperty(value = "专业名称")
	private String professionName;

	/**单位编码*/
    @Excel(name = "单位编码", width = 15)
	@ApiModelProperty(value = "单位编码")
	private String unitCode;

	/**单位名称*/
    @Excel(name = "单位名称", width = 15)
	@ApiModelProperty(value = "单位名称")
	private String unitName;

	/**变电站编码*/
    @Excel(name = "变电站编码", width = 15)
	@ApiModelProperty(value = "变电站编码")
	private String stationLine;

	/**变电站名称*/
    @Excel(name = "变电站名称", width = 15)
	@ApiModelProperty(value = "变电站名称")
	private String stationLineName;

	/**电压等级编码*/
    @Excel(name = "电压等级编码", width = 15)
	@ApiModelProperty(value = "电压等级编码")
	private String voltageLevelCode;

	/**电压等级名称*/
    @Excel(name = "电压等级名称", width = 15)
	@ApiModelProperty(value = "电压等级名称")
	private String voltageLevelName;

	/**间隔类型编码*/
    @Excel(name = "间隔类型编码", width = 15)
	@ApiModelProperty(value = "间隔类型编码")
	private String dispatchNumTypeCode;

	/**间隔类型名称*/
    @Excel(name = "间隔类型名称", width = 15)
	@ApiModelProperty(value = "间隔类型名称")
	private String dispatchNumTypeName;

	/**间隔编码*/
    @Excel(name = "间隔编码", width = 15)
	@ApiModelProperty(value = "间隔编码")
	private String dispatchNum;

	/**间隔名称*/
    @Excel(name = "间隔名称", width = 15)
	@ApiModelProperty(value = "间隔名称")
	private String dispatchNumId;

	/**信息分类编码*/
    @Excel(name = "信息分类编码", width = 15)
	@ApiModelProperty(value = "信息分类编码")
	private String signalClassification;

	/**信息分类名称*/
    @Excel(name = "信息分类名称", width = 15)
	@ApiModelProperty(value = "信息分类名称")
	private String signalClassificationName;

	/**信息描述*/
    @Excel(name = "信息描述", width = 15)
	@ApiModelProperty(value = "信息描述")
	private String infoExplain;

	/**设备分类编码*/
    @Excel(name = "设备分类编码", width = 15)
	@ApiModelProperty(value = "设备分类编码")
	private String equipmentType;

	/**设备分类名称*/
    @Excel(name = "设备分类名称", width = 15)
	@ApiModelProperty(value = "设备分类名称")
	private String equipmentTypeName;

	/**传送单位编码*/
    @Excel(name = "传送单位编码", width = 15)
	@ApiModelProperty(value = "传送单位编码")
	private String transUnitCode;

	/**传送单位名称*/
    @Excel(name = "传送单位名称", width = 15)
	@ApiModelProperty(value = "传送单位名称")
	private String transUnitName;

	/**遥信点号编码*/
    @Excel(name = "遥信点号编码", width = 15)
	@ApiModelProperty(value = "遥信点号编码")
	private String remoteSignalPointCode;

	/**遥信点号名称*/
    @Excel(name = "遥信点号名称", width = 15)
	@ApiModelProperty(value = "遥信点号名称")
	private String remoteSignalPointName;

	/**RTU*/
    @Excel(name = "RTU", width = 15)
	@ApiModelProperty(value = "RTU")
	private String rtu;

	/**是否电容器伴生信号  0否1是*/
    @Excel(name = "是否电容器伴生信号  0否1是", width = 15)
	@ApiModelProperty(value = "是否电容器伴生信号  0否1是")
	private Integer capacitorSignalFlag;

	/**变电站对应信息*/
    @Excel(name = "变电站对应信息", width = 15)
	@ApiModelProperty(value = "变电站对应信息")
	private String stationLineInfo;

	/**点表类别名称*/
    @Excel(name = "点表类别名称", width = 15)
	@ApiModelProperty(value = "点表类别名称")
	private String pointTypeCode;

	/**点表类型编码*/
    @Excel(name = "点表类型编码", width = 15)
	@ApiModelProperty(value = "点表类型编码")
	private String pointTypeName;

	/**状态编码*/
    @Excel(name = "状态编码", width = 15)
	@ApiModelProperty(value = "状态编码")
	private String stateCode;

	/**状态名称*/
    @Excel(name = "状态名称", width = 15)
	@ApiModelProperty(value = "状态名称")
	private String stateName;

	/**流程状态编码*/
    @Excel(name = "流程状态编码", width = 15)
	@ApiModelProperty(value = "流程状态编码")
	private String procedureCode;

	/**流程状态名称*/
    @Excel(name = "流程状态名称", width = 15)
	@ApiModelProperty(value = "流程状态名称")
	private String procedureName;

	/**备注*/
    @Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private String remarks;

	/**删除状态  0否1是*/
    @Excel(name = "删除状态  0否1是", width = 15)
	@ApiModelProperty(value = "删除状态  0否1是")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
	private Integer delFlag;

	/**创建人*/
    @Excel(name = "创建人", width = 15)
	@ApiModelProperty(value = "创建人")
	private String createBy;

	/**创建时间*/
    @Excel(name = "创建时间", width = 15)
	@ApiModelProperty(value = "创建时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**更新人*/
    @Excel(name = "更新人", width = 15)
	@ApiModelProperty(value = "更新人")
	private String updateBy;

	/**更新时间*/
	@Excel(name = "更新时间", width = 15)
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
	private Date updateTime;

	/**字典类型*/
    @Excel(name = "字典类型", width = 15)
	@ApiModelProperty(value = "字典类型")
	private String type;

	/**版本号*/
    @Excel(name = "版本号", width = 15)
	@ApiModelProperty(value = "版本号")
    //乐观锁Version注解
    @Version
    @TableField(value="version", fill = FieldFill.INSERT, update="%s+1")
	private Integer version;

	/**关联点表汇总表ID*/
    @Excel(name = "关联点表汇总表ID", width = 15)
	@ApiModelProperty(value = "关联点表汇总表ID")
	private String relatedPointListId;

	/**数据来源类型*/
	@Excel(name = "数据来源类型", width = 15)
	@ApiModelProperty(value = "数据来源类型")
	private String dataSourceType;

	/**电压等级编码*/
	@Excel(name = "设备电压等级编码", width = 15)
	@ApiModelProperty(value = "设备电压等级编码")
	private String equipVoltageLevelCode;

	/**电压等级名称*/
	@Excel(name = "设备电压等级名称", width = 15)
	@ApiModelProperty(value = "设备电压等级名称")
	private String equipVoltageLevelName;

	/**预删除状态  0否1是*/
	@Excel(name = "预删除状态  0否1是", width = 15)
	@ApiModelProperty(value = "预删除状态  0否1是")
	private Integer previewDelFlag;

}
