package com.defectscan.controller.img;

import com.defectscan.dto.ImgPageQueryDTO;
import com.defectscan.entity.Img;
import com.defectscan.result.PageResult;
import com.defectscan.result.Result;
import com.defectscan.service.ImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/img")
@CrossOrigin("*") // 允许跨域
@Api(tags = "图片管理相关接口")
@Slf4j
public class ImgController {

    @Autowired
    private ImgService imgService;


    /**
     * 图片分页查询
     * @param imgPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(@RequestParam(defaultValue = "1") int page,  // 页码
                                   @RequestParam(defaultValue = "10") int pageSize, // 每页记录数
                                   ImgPageQueryDTO imgPageQueryDTO){

        log.info("分页查询,页码：{},每页记录数：{},{}", page, pageSize, imgPageQueryDTO);
        PageResult pageResult = imgService.pageQuery(page, pageSize, imgPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/update")
    public Result updateImg(@RequestBody Img img) {
        log.info("编辑(更新)信息：{}",img);
        boolean result = imgService.updateImg(img);
        if (result) {
            return Result.success("图片信息修改成功");
        } else {
            return Result.error("图片信息修改失败");
        }
    }

    @DeleteMapping("/delete")
    public Result deleteImg(Long id) {
        log.info("删除图片信息id ：{}",id);
        boolean result = imgService.deleteImg(id);
        if (result) {
            return Result.success("图片信息删除成功");
        } else {
            return Result.error("图片信息删除失败");
        }
    }
}
