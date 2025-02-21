package com.lzk.controller;

import com.lzk.entity.Img;
import com.lzk.entity.OpeImg;
import com.lzk.entity.Result;
import com.lzk.service.ImgService;
import com.lzk.service.OpeImgService;
import com.lzk.tools.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@ResponseBody // 返回JSON
@CrossOrigin("*") // 允许跨域
public class opeImg {

    //日志记录对象
    private final Logger logger = LoggerFactory.getLogger("com.lzk.controller.opeImg");

    @Autowired
    OpeImgService opeImgService;

    @Autowired
    FileTool fileTool;

    @Autowired
    ImgService imgService;


    @Value("${indexDir}")
    private String indexDir;

    @PostMapping("/addOpe")
    public Result addOpe(@RequestBody OpeImg request){
        try {
            logger.info("添加打标：" + request.toString());
            // 生成文件名
            String indexUrlName = request.getOper() + "_" + request.getImgId() + "_" + request.getSafeType() + ".txt";
            // 生成坐标文件地址
            String indexUrl = Paths.get(indexDir, indexUrlName).toString();
            //先用隐患ID + 图片ID查询一下有没有这个记录
            OpeImg temp = new OpeImg();
            temp.setSafeType(request.getSafeType());
            temp.setImgId(request.getImgId());
            List<OpeImg> result = opeImgService.findOpeImgs(temp);
            //先把坐标写入坐标文件中
            fileTool.writeLine(indexUrl, request.getIndexUrl());
            //更新request的indexUrl 换成真的indexUrl
            request.setIndexUrl(indexUrl);
            //获取当前时间戳
            request.setKeyTime(LocalDateTime.now().toString());
            if (result.isEmpty()) {
                //如果没有这个记录
                opeImgService.addOpeImg(request);
                //同时更新IMGS表 说明该图片已经打标签了
                Img tempImg = new Img();
                tempImg.setId(request.getImgId());
                tempImg.setIsOpe("1");
                imgService.changeImg(tempImg);
            } else opeImgService.changeOpeImg(request);

            return Result.success("标注成功！",null);
        }
        catch (Exception e){
            return Result.error(e.toString());
        }
    }

    @PostMapping("/findOpe")
    public Result findOpe(@RequestBody OpeImg request){
        logger.info("查询打标记录："+request.toString());
        List<OpeImg> result = opeImgService.findOpeImgs(request);
        return Result.success(result);
    }

    @PostMapping("/delOpe")
    public Result delOpe(@RequestBody OpeImg request){
        logger.info("删除坐标文件"+ request.toString());
        opeImgService.delOpeImg(request);
        //删完之后还要检查一般该图片是否还有其他标注
        OpeImg Temp = new OpeImg();
        Temp.setImgId(request.getImgId());
        List<OpeImg> resultTemp = opeImgService.findOpeImgs(Temp);
        //如果没有其他标注那么就要把该图片的isope字段设置为0 代表没有标注
        if(resultTemp.isEmpty())
        {
            Img imgTemp = new Img();
            imgTemp.setId(request.getImgId());
            imgTemp.setIsOpe("0");
            imgService.changeImg(imgTemp);
        }
        return Result.success();
    }

    @PostMapping("/getIndexFromOpe")
    public Result getIndexFromOpe(@RequestBody OpeImg request){
        logger.info("获取坐标文件内的坐标："+ request.toString());
        String indexPath = request.getIndexUrl();
        return Result.success(fileTool.readLines(indexPath));
    }

    @GetMapping("/getSampleHiddens")
    public Result getSampleHiddens(){
        logger.info("获取样本集隐患集合");
        List<OpeImg> result = opeImgService.getSampleHiddens();
        return Result.success(result);
    }

    @GetMapping("/getSampleHiddenIds")
    public Result getSampleHiddenIds(){
        logger.info("获取样本集隐患ID集合");
        return Result.success(opeImgService.getSampleHiddenIds());
    }


}
