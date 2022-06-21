package com.sunwei.demo.secKillDemo.SeckillUserS.mapstruct;//package com.sunwei.demo.secKillDemo.SeckillUserS.mapstruct;

import com.sunwei.demo.secKillDemo.SeckillUserS.entity.SeckillUser;
import com.sunwei.demo.secKillDemo.SeckillUserS.vo.SeckillUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Description   :  用户表 SeckillUser => SeckillUserVo  对象转换
 * @author        :  孙伟
 * @createDate    :  2022-04-26 07:42:259.057
 * @UpdateUser    :  孙伟
 * @UpdateDate    :  2022-04-26 07:42:259.057
 * @UpdateRemark  :  修改内容
 * @Version       :  1.0
 */
@Component
public interface SeckillUserStruct{
    /**
     * @Description  ：
     * @author       : 孙伟
     * @param     seckillUserVo   : 视图对象
     * @return    SeckillUser   :    数据库实体对象
     * @exception    :
     * @date         : 2022-04-26 07:42:260.057
     */
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "nikename", source = "nikename"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "slat", source = "slat"),
            @Mapping(target = "head", source = "head"),
            @Mapping(target = "registerDate", source = "registerDate"),
            @Mapping(target = "lastLoginDate", source = "lastLoginDate"),
            @Mapping(target = "loginCount", source = "loginCount")
    })
    SeckillUser conventSeckillUserVo2SeckillUser (SeckillUserVo seckillUserVo);
}
