package com.sunwei.demo.easyExcel.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunwei.demo.easyExcel.entity.Books;
import com.sunwei.demo.easyExcel.mapper.BooksMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class IBooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements IBooksService {
}
