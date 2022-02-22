package com.yjy.moxibustion.web.controller.app;

import com.yjy.moxibustion.abstracts.AbstractBaseController;
import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.pojo.XueweiCategory;
import com.yjy.moxibustion.pojo.XueweiContent;
import com.yjy.moxibustion.service.XueweiCategoryService;
import com.yjy.moxibustion.service.XueweiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * App端的关于穴位的处理
 * @author 杨景元
 * @date 2021/1/19 15:15
 */
@RestController
@RequestMapping(value = "api/xuewei")
public class XueweiController {
    @Autowired
    private XueweiService xueweiService;

    @Autowired
    private XueweiCategoryService xueweiCategoryService;

    @RequestMapping(value = "/categories")
    public RestResp<List<XueweiCategory>> treeData(String name){
        Integer pid = xueweiCategoryService.getXueweiCategoryByName(name);
        List<XueweiCategory> list = xueweiCategoryService.selectByPid(pid);
        return RestResp.success("success",list);
    }

    @RequestMapping(value = "/contents")
    public RestResp<XueweiContent> getXueweiContentByName(String name){
        Integer cid = xueweiCategoryService.getXueweiCategoryByName(name);
        XueweiContent content = xueweiService.getXueweiContentByCid(cid);
        return RestResp.success("success",content);
    }

    /**
     * 将穴位的图片返回给android
     * @param name
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/images",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getImage(String name) throws IOException {
        try(InputStream inputStream = new FileInputStream("src/main/resources/images/"+name+".jpg")){
            //System.out.println(ImageIO.read(inputStream));
            return ImageIO.read(inputStream);
        }
    }
}
