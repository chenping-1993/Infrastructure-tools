package com.example.cp.controller;

import com.example.cp.common.tool.ZipUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-09-15
 */
@RestController
public class TestController extends BaseController {

    @RequestMapping("/tozip")
    public void zipTest() throws IOException {
        ZipUtil.toZip("C:/资料/cp/work/知识问题.docx","C:/资料/cp/work/aa.zip",true);
    }
}
