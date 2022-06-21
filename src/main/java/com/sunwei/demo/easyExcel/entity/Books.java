package com.sunwei.demo.easyExcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
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

@Data
@TableName("books")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "books对象", description = "书籍表")
public class Books {

    private static final long serialVersionUID = 3961849317413433654L;

    /**唯一标识编码*/
    @TableId(type = IdType.ID_WORKER_STR)
    @ExcelProperty("唯一标识编码")
    private String bookId;

    /**书名*/
    @Excel(name = "书名", width = 15 )
    @ExcelProperty("书名")
    @ApiModelProperty(value = "书名")
    private String  bookName;

    /**数量*/
    @Excel(name = "数量", width = 15 )
    @ExcelProperty("数量")
    @ApiModelProperty(value = "数量")
    private String  bookCounts;

    /**明细*/
    @Excel(name = "明细", width = 15 )
    @ExcelProperty("明细")
    @ApiModelProperty(value = "明细")
    private String  detail;

}
