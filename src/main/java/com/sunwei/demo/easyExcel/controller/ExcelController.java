package com.sunwei.demo.easyExcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunwei.demo.easyExcel.easyExcel.SignRecordExcelHandler;
import com.sunwei.demo.easyExcel.entity.Books;
import com.sunwei.demo.easyExcel.service.IBooksService;
import com.sunwei.demo.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    private IBooksService booksService;

    @RequestMapping({"/list"})
    public Result<IPage<Books>> list(Books books,
                                            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                            HttpServletRequest req){
        QueryWrapper<Books> queryWrapper = new QueryWrapper();
        Page<Books> page = new Page<Books>(pageNo, pageSize);
        IPage<Books> pageList = booksService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @RequestMapping({"/excelExport"})
    public void  excelExport(Books books,
                             @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                             @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                             HttpServletRequest req, HttpServletResponse response) throws IOException {
        QueryWrapper<Books> queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(books.getBookName())){
            queryWrapper.eq("BOOK_NAME",books.getBookName());
        }
        Page<Books> page = new Page<Books>(pageNo, pageSize);
        IPage<Books> pageList = booksService.page(page, queryWrapper);
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Books.class)
                    .registerWriteHandler(new SignRecordExcelHandler())
                    .autoCloseStream(Boolean.FALSE)
                    .sheet("模板")
                    .doWrite(pageList.getRecords());
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

}
