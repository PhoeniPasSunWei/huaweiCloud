package com.sunwei.demo.secKillDemo.SeckillUserS.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sunwei.demo.result.Result;

import javax.validation.Valid;

import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.mapstruct.SeckillUserStruct;
import com.sunwei.demo.secKillDemo.SeckillUserS.vo.SeckillUserVo;
import com.sunwei.demo.secKillDemo.SeckillUserS.service.ISeckillUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunwei.demo.utils.mybatisPlus.QueryGenerator;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 用户表
 * @Author: 孙伟
 * @Date:   2022-04-26 07:42:240.057
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户表")
@RestController
@RequestMapping("/SeckillUserS/seckillUser")
public class SeckillUserController {
    /**
      * 允许操作的数据--数量值 100
      */
	@Value("${spring.datasource.allow_oper_num}")
	private int ALLOW_OPER_NUM;
     /**
      * 用户表数据批量保存一次性COMMIT条数
      */
    private static final int SAVE_BATCH_SIZE = 10000;
     /**
      * 用户表数据批量更新一次性COMMIT条数
      */
    private static final int UPDATE_BATCH_SIZE = 10000;
     /**
      * 用户表数据批量更新或者保存COMMIT条数
      */
    private static final int SAVEORUPDATE_BATCH = 10000;

	@Autowired
	private ISeckillUserService seckillUserService;

	@Autowired
    private SeckillUserStruct seckillUserStruct;
	/**
	  * 分页列表查询
	 * @param seckillUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="用户表-分页列表查询", notes="用户表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SeckillUser>> queryPageList(SeckillUser seckillUser,
													@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													HttpServletRequest req) {
        //将视图对象转换为  数据库对象
        //SeckillUser seckillUser = seckillUserStruct.conventSeckillUserVo2SeckillUser(seckillUserVo);
		QueryWrapper<SeckillUser> queryWrapper = QueryGenerator.initQueryWrapper(seckillUser, req.getParameterMap());
		Page<SeckillUser> page = new Page<SeckillUser>(pageNo, pageSize);
		IPage<SeckillUser> pageList = seckillUserService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	  *   添加
	 * @param seckillUser
	 * @return
	 */
	@ApiOperation(value="用户表-添加", notes="用户表-添加")
	@PostMapping(value = "/add")
	public Result<Object> add(@Valid @RequestBody SeckillUser seckillUser) {
		//将视图对象转换为  数据库对象
        //SeckillUser seckillUser = seckillUserStruct.conventSeckillUserVo2SeckillUser(seckillUserVo);
        boolean ok = seckillUserService.save(seckillUser);
        if(ok) {
             return Result.ok("添加成功");
         }else {
             return Result.error("添加失败");
         }
	}

    /**
      *   批量添加
     * @param seckillUsers
     * @return
     */
    @ApiOperation(value="用户表-批量添加", notes="用户表-批量添加")
    @PostMapping(value = "/add-batch")
    public Result<Object> addBatch(@Valid @RequestBody List<SeckillUser> seckillUsers) {
        boolean ok = seckillUserService.saveBatch(seckillUsers,SAVE_BATCH_SIZE);
        if(ok) {
             return Result.ok("批量添加成功");
         }else {
             return Result.error("批量添加失败");
         }
    }

	/**
	  *  编辑
	 * @param seckillUser
	 * @return
	 */
	@ApiOperation(value="用户表-编辑", notes="用户表-编辑")
	@PostMapping(value = "/edit")
	public Result<Object> edit(@Valid @RequestBody SeckillUser seckillUser) {
	    //将视图对象转换为  数据库对象
        //SeckillUser seckillUser = seckillUserStruct.conventSeckillUserVo2SeckillUser(seckillUserVo);
		SeckillUser seckillUserEntity = seckillUserService.getById(seckillUser.getId());
		if(seckillUserEntity==null) {
			return Result.error("未找到对应实体");
		}else {
			boolean ok = seckillUserService.updateById(seckillUser);
			if(ok) {
                return Result.ok("添加成功！");
            }else{
                return Result.error("修改失败！");
            }
		}
	}


/**
	  *  批量编辑
	 * @param seckillUsers
	 * @return
	 */
	@ApiOperation(value="用户表-批量编辑", notes="用户表-批量编辑")
	@PostMapping(value = "/edit-batch")
	public Result<Object> editBatch(@Valid @RequestBody List<SeckillUser> seckillUsers) {
	    boolean ok = seckillUserService.updateBatchById(seckillUsers,UPDATE_BATCH_SIZE);
        if(ok) {
            return Result.ok("批量编辑成功");
        }else {
            return Result.error("批量编辑失败");
        }
	}

	/**
	  *  保存或编辑
	 * @param seckillUser
	 * @return
	 */
	@ApiOperation(value="用户表-保存或编辑", notes="用户表-保存或编辑")
	@PostMapping(value = "/addoredit")
	public Result<Object> addOrEdit(@Valid @RequestBody SeckillUser seckillUser) {
	    //将视图对象转换为数据库对象
        //SeckillUser seckillUser = seckillUserStruct.conventSeckillUserVo2SeckillUser(seckillUserVo);
		boolean ok = seckillUserService.saveOrUpdate(seckillUser);
		 if(ok) {
			 return Result.ok("保存或者编辑成功");
		 }else {
			 return Result.error("保存或者编辑失败");
		 }
	}

/**
	  *  批量保存或编辑
	 * @param seckillUsers
	 * @return
	 */
	@ApiOperation(value="用户表-批量保存或编辑", notes="用户表-批量保存或编辑")
	@PostMapping(value = "/addoredit-batch")
	public Result<Object> addOrEditBatch(@Valid @RequestBody List<SeckillUser> seckillUsers) {
	    boolean ok = seckillUserService.saveOrUpdateBatch(seckillUsers,SAVEORUPDATE_BATCH);
		 if(ok) {
			 return Result.ok("批量保存或编辑成功");
		 }else {
			 return Result.error("批量保存或编辑失败");
		 }
	}


	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户表-通过id删除", notes="用户表-通过id删除")
	@GetMapping(value = "/delete/{id}")
	public Result<Object> delete(@PathVariable(name="id",required=true) String id) {
		SeckillUser seckillUser = seckillUserService.getById(id);
		if(seckillUser==null) {
			return Result.error("未找到对应实体");
		}else {
			boolean ok = seckillUserService.removeById(id);
			if(ok) {
                return Result.ok("删除成功！");
            }else{
                return Result.error("删除失败！");
            }
		}
	}

	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="用户表-批量删除", notes="用户表-批量删除")
	@GetMapping(value = "/deleteBatch")
	public Result<Object> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		if(ids==null || "".equals(ids.trim())) {
			return Result.error("参数不识别！");
		}else {
			this.seckillUserService.removeByIds(Arrays.asList(ids.split(",")));
			return Result.ok("删除成功！");
		}
	}

	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="用户表-通过id查询", notes="用户表-通过id查询")
	@GetMapping(value = "queryById/{id}")
	public Result<SeckillUser> queryById(@PathVariable(name="id",required=true) String id) {
		SeckillUser seckillUser = seckillUserService.getById(id);
		if(seckillUser==null) {
		    Result<SeckillUser> result = new Result<SeckillUser>();
			result.error500("未找到对应实体");
            return result;
		}else {
			return Result.ok(seckillUser);
		}
	}


}
