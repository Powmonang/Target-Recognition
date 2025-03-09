package com.defectscan.service.impl;

//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
import com.defectscan.entity.*;
import com.defectscan.mapper.*;
import com.defectscan.service.ImgOriginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ImgOriginServiceImpl implements ImgOriginService {

    @Autowired
    ImgOriginMapper imgOriginMapper;

//    @Autowired
//    OpeImgMapper opeImgMapper;
//
//    @Autowired
//    SafeTypeMapper safeTypeMapper;
//
//    @Autowired
//    InferImgMapper inferImgMapper;
//
//    @Autowired
//    ImgTypeMapper imgTypeMapper;

    @Override
    public void addImgOrigin(ImgOrigin a) {
        a.setCreateTime(LocalDateTime.now().toString());
        a.setUpdateTime(LocalDateTime.now().toString());
        imgOriginMapper.addImgOrigin(a);
    }

//    @Override
//    public List<Img> findImgs(Img a) {
//        return imgMapper.findImgs(a);
//    }
//
//    //图像管理---这个是根据图片需求查询图片的隐患打标记录与推理记录
//    @Override
//    public PageBean findImgsPage(Img a, Integer page, Integer pageSize) {
//        PageHelper.startPage(page, pageSize);
//        Page<Img> pageResult = (Page<Img>)imgMapper.findImgsPage(a, page, pageSize);
//        List<Img> resultTemp = pageResult.getResult();
//
//        for(Img i:resultTemp){
//            String jude = i.getSafeCount();
//            // 如果诊断隐患数为-1 就是未诊断
//            if(jude.equals("-1")){
//                i.setSafeCount("未诊断");
//            }
//            //查询该图片所有的打标记录
//            OpeImg Temp = new OpeImg();
//            Temp.setImgId(i.getId());
//            List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//            //把里面的隐患类型 转化成实际的
//            for(OpeImg j:opeTemp){
//                //查询隐患名称
//                SafeType safeType1 = new SafeType();
//                safeType1.setId(j.getSafeType());
//                //替换成具体内容
//                j.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//            }
//            i.setOpeImgs(opeTemp);
//            //查询该图片所有的推理记录
//            InferImg inferImgTemp = new InferImg();
//            inferImgTemp.setImgId(i.getId());
//            List<InferImg> inferTemp = inferImgMapper.findInfers(inferImgTemp);
//            //把里面的隐患类型 转化成实际的
//            for(InferImg j:inferTemp){
//                //查询隐患名称
//                SafeType safeType1 = new SafeType();
//                safeType1.setId(j.getSafeType());
//                //替换成具体内容
//                j.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//            }
//            i.setInferImgs(inferTemp);
//        }
//        return new PageBean(pageResult.getTotal(), resultTemp);
//    }
//
//    //AI诊断---V1---这个是为了AI诊断条件查询
//    @Override
//    public PageBean findImgsPage(ImgDTO a){
//        //获取img信息
//        Img img = a.getImg();
//        //获取inferImg信息
//        InferImg inferImg = a.getInferImg();
//        //提取表单参数
//        Integer page = a.getPage();
//        Integer pageSize = a.getPageSize();
//        String imgId = null;
//        String isJude = null;
//        String safeType = null;
//        String keyDate = null;
//        String show = null;
//        String start = null;
//        String end = null;
//        if(img!=null){
//            imgId = img.getId();
//            isJude = img.getIsJude();
//            show = img.getShow();
//        }
//        if(inferImg!=null){
//            safeType = inferImg.getSafeType();
//            keyDate = inferImg.getKeyTime();
//            start = inferImg.getStart();
//            end = inferImg.getEnd();
//        }
//        //这个是查图片ID的 所以如果有了imgId
//        if(imgId!=null){
//            Img selectTemp = new Img();
//            selectTemp.setId(imgId);
//            selectTemp.setShow(show);
//            return findImgsPage(selectTemp, page, pageSize);
//        }else{
//            if(isJude!=null){
//                if(isJude.equals("0")){
//                    Img img1 = new Img();
//                    img1.setSafeCount("-1");
//                    img1.setShow(show);
//                    return findImgsPage(img1, page, pageSize);
//                }
//                else{
//                    List<Object> resultRows = new ArrayList<>();
//                    Long resultTotal = 0L;
//                    //如果是查询有诊断的
//                    InferImg inferImg1 = new InferImg();
//                    inferImg1.setSafeType(safeType);
//                    inferImg1.setKeyTime(keyDate);
//                    inferImg1.setStart(start);
//                    inferImg1.setEnd(end);
//                    //查询到符合要求的图片ID
//                    List<InferImg> resultTemp = inferImgMapper.findInfers(inferImg1);
//                    //图片ID
//                    Set<String> imgIds = new HashSet<>();
//                    for(InferImg i:resultTemp){
//                        //获取图片ID
//                        String imgId1 = i.getImgId();
//                        imgIds.add(imgId1);
//                    }
//                    for(String i:imgIds){
//                        Img img1 = new Img();
//                        img1.setId(i);
//                        img1.setShow(show);
//                        PageBean pageBeanTemp = findImgsPage(img1, page, pageSize);
//                        resultTotal += pageBeanTemp.getTotal();
//                        resultRows.add(pageBeanTemp.getRows().get(0));
//                    }
//                    return new PageBean(resultTotal, resultRows);
//                }
//            }else{
//                if(safeType==null && start ==null){
//                    Img selectTemp = new Img();
//                    selectTemp.setShow(show);
//                    return findImgsPage(selectTemp, page, pageSize);
//                }else{
//                    List<Object> resultRows = new ArrayList<>();
//                    Long resultTotal = 0L;
//                    //如果是查询有诊断的
//                    InferImg inferImg1 = new InferImg();
//                    inferImg1.setSafeType(safeType);
//                    inferImg1.setKeyTime(keyDate);
//                    inferImg1.setStart(start);
//                    inferImg1.setEnd(end);
//                    //查询到符合要求的图片ID
//                    List<InferImg> resultTemp = inferImgMapper.findInfers(inferImg1);
//                    //图片ID
//                    Set<String> imgIds = new HashSet<>();
//                    for(InferImg i:resultTemp){
//                        //获取图片ID
//                        String imgId1 = i.getImgId();
//                        imgIds.add(imgId1);
//                    }
//                    for(String i:imgIds){
//                        Img img1 = new Img();
//                        img1.setId(i);
//                        img1.setShow(show);
//                        PageBean pageBeanTemp = findImgsPage(img1, page, pageSize);
//                        resultTotal += pageBeanTemp.getTotal();
//                        resultRows.add(pageBeanTemp.getRows().get(0));
//                    }
//                    return new PageBean(resultTotal, resultRows);
//                }
//            }
//        }
//    }
//
//    /**
//     * 根据图片条件 查询报告的ID
//     * @param a 图片条件
//     * @return 返回报告ID
//     */
//    @Override
//    public PageBean findReportPage(ImgDTO a) {
//        Img img = a.getImg();
//        Integer page = a.getPage();
//        Integer pageSize = a.getPageSize();
//        PageHelper.startPage(page, pageSize);
//        Page<String> pageResult = (Page<String>)imgMapper.getAllReportIdPage(img, page, pageSize);
//        return new PageBean(pageResult.getTotal(), pageResult.getResult());
//    }
//
//    @Override
//    public List<String> findReport(Img a) {
//        return imgMapper.getAllReportId(a);
//    }
//
//    // 图像管理---按照报告以组的形式 返回图片
//    public ImgVo findImgGroupByReport(ImgDTO imgDTO){
//        List<List<Img>> result = new ArrayList<>();
//        // 根据图片条件 查询出报告ID
//        PageBean pageBean = findReportPage(imgDTO);
//        // 获取报告ID
//        List<String> rows = pageBean.getRows();
//        // 然后根据报告ID 查询图片
//        List<ImgType> imgTypes = imgTypeMapper.findImgType(new ImgType());
//        // 查询图片类型
//        Map<String, String> imgTypeMap = new HashMap<>();
//        // 图片类型ID 对应 图片类型名称
//        for(ImgType i:imgTypes){
//            imgTypeMap.put(i.getId(), i.getType());
//        }
//        for(String i:rows){
//            Img img = new Img();
//            img.setReportId(i);
//            List<Img> reportImgs = imgMapper.findImgs(img); //查询到该报告ID的图片组
//            //还要查询图片的打标记录
//            for(Img j:reportImgs){
//                String jude = j.getSafeCount();
//                j.setPhotoTagId(imgTypeMap.get(j.getPhotoTagId()));
//                // 如果诊断隐患数为-1 就是未诊断
//                if(jude.equals("-1")){
//                    j.setSafeCount("未诊断");
//                }
//                //查询该图片所有的打标记录
//                OpeImg Temp = new OpeImg();
//                Temp.setImgId(j.getId());
//                List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//                //把里面的隐患类型 转化成实际的
//                for(OpeImg k:opeTemp){
//                    //查询隐患名称
//                    SafeType safeType1 = new SafeType();
//                    safeType1.setId(k.getSafeType());
//                    //替换成具体内容
//                    k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                }
//                j.setOpeImgs(opeTemp);
//            }
//            result.add(reportImgs);
//        }
//        return new ImgVo(pageBean.getTotal(), result);
//    }
//    /**
//     * 为AI诊断 图片查询函数
//     */
//    @Override
//    public ImgVo findImgGroupByReportAi(ImgDTO imgDTO) {
//        Integer page = imgDTO.getPage();
//        Integer pageSize = imgDTO.getPageSize();
//        List<List<Img>> result = new ArrayList<>();
//        List<ImgType> imgTypes = imgTypeMapper.findImgType(new ImgType());
//        // 查询图片类型
//        Map<String, String> imgTypeMap = new HashMap<>();
//        // 图片类型ID 对应 图片类型名称
//        for(ImgType i:imgTypes){
//            imgTypeMap.put(i.getId(), i.getType());
//        }
//
//        if(imgDTO.getImg()!=null && imgDTO.getImg().getReportId() != null) {
//            // 根据报告ID 查询图片
//            List<Img> imgs = imgMapper.findImgs(imgDTO.getImg());
//            for (Img i : imgs) {
//                i.setPhotoTagId(imgTypeMap.get(i.getPhotoTagId()));
//                //查询该图片所有的打标记录
//                OpeImg Temp = new OpeImg();
//                Temp.setImgId(i.getId());
//                List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//                //把里面的隐患类型 转化成实际的
//                for(OpeImg j:opeTemp){
//                    //查询隐患名称
//                    SafeType safeType1 = new SafeType();
//                    safeType1.setId(j.getSafeType());
//                    //替换成具体内容
//                    j.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                }
//                i.setOpeImgs(opeTemp);
//
//                //查询该图片所有的推理记录
//                InferImg inferImgTemp = new InferImg();
//                inferImgTemp.setImgId(i.getId());
//                List<InferImg> inferTemp = inferImgMapper.findInfers(inferImgTemp);
//                //把里面的隐患类型 转化成实际的
//                for(InferImg j:inferTemp){
//                    //查询隐患名称
//                    SafeType safeType1 = new SafeType();
//                    safeType1.setId(j.getSafeType());
//                    //替换成具体内容
//                    j.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                }
//                // 如果诊断隐患数为-1 就是未诊断
//                if(i.getSafeCount().equals("-1")) {
//                    i.setSafeCount("未诊断");
//                }
//                i.setInferImgs(inferTemp);
//            }
//            if(!imgs.isEmpty())
//            {
//                result.add(imgs);
//            }
//            return new ImgVo((long)result.size(), result);
//        } else if (imgDTO.getImg()!=null && imgDTO.getImg().getIsJude() !=null) {
//            System.out.println(imgDTO.toString());
//            imgDTO.getImg().setShow("1");
//            // 条件查询图片 得到报告ID
//            PageBean pageBean = findReportPage(imgDTO);
//            // 获取报告ID
//            List<String> rows = pageBean.getRows();
//            // 然后根据报告ID 查询图片
//            for(String i:rows){
//                Img img = new Img();
//                img.setReportId(i);
//                List<Img> reportImgs = imgMapper.findImgs(img); //查询到该报告ID的图片组
//                //还要查询图片的推理记录
//                for(Img j:reportImgs){
//                    String jude = j.getSafeCount();
//                    j.setPhotoTagId(imgTypeMap.get(j.getPhotoTagId()));
//
//                    //查询该图片所有的打标记录
//                    OpeImg Temp = new OpeImg();
//                    Temp.setImgId(j.getId());
//                    List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//                    //把里面的隐患类型 转化成实际的
//                    for(OpeImg k:opeTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    j.setOpeImgs(opeTemp);
//
//                    //查询该图片所有的推理记录
//                    InferImg inferImgTemp = new InferImg();
//                    inferImgTemp.setImgId(j.getId());
//                    List<InferImg> inferTemp = inferImgMapper.findInfers(inferImgTemp);
//                    //把里面的隐患类型 转化成实际的
//                    for(InferImg k:inferTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    // 如果诊断隐患数为-1 就是未诊断
//                    if(jude.equals("-1")){
//                        j.setSafeCount("未诊断");
//                    }
//                    j.setInferImgs(inferTemp);
//                }
//                result.add(reportImgs);
//            }
//            return new ImgVo(pageBean.getTotal(), result);
//        }else if(imgDTO.getInferImg()!=null && ((imgDTO.getInferImg().getStart() != null && imgDTO.getInferImg().getEnd() != null) || imgDTO.getInferImg().getSafeType()!=null)){
//            // 根据推理条件 分页查询报告ID
//            PageHelper.startPage(page, pageSize);
//            Page<InferImg> pageResult = (Page<InferImg>)inferImgMapper.findReportIdDistinctPage(imgDTO.getInferImg(), page, pageSize);
//            // 根据报告ID 查询图片
//            for(InferImg i:pageResult){
//                Img img = new Img();
//                img.setReportId(i.getReportId());
//                List<Img> imgs = imgMapper.findImgs(img);
//                for(Img j:imgs){
//                    // 如果诊断隐患数为-1 就是未诊断
//                    if(j.getSafeCount().equals("-1")) {
//                        j.setSafeCount("未诊断");
//                    }
//                    //查询该图片所有的打标记录
//                    OpeImg Temp = new OpeImg();
//                    Temp.setImgId(j.getId());
//                    List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//                    //把里面的隐患类型 转化成实际的
//                    for(OpeImg k:opeTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    j.setOpeImgs(opeTemp);
//                    //查询该图片所有的推理记录
//                    InferImg inferImgTemp = new InferImg();
//                    inferImgTemp.setImgId(j.getId());
//                    List<InferImg> inferTemp = inferImgMapper.findInfers(inferImgTemp);
//                    //把里面的隐患类型 转化成实际的
//                    for(InferImg k:inferTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    j.setInferImgs(inferTemp);
//
//                }
//                result.add(imgs);
//            }
//            return new ImgVo(pageResult.getTotal(), result);
//        }else{
//            // 根据图片条件 查询出报告ID
//            PageBean pageBean = findReportPage(imgDTO);
//            // 获取报告ID
//            List<String> rows = pageBean.getRows();
//            for(String i:rows){
//                Img img = new Img();
//                img.setReportId(i);
//                List<Img> reportImgs = imgMapper.findImgs(img); //查询到该报告ID的图片组
//                //还要查询图片的打标记录
//                for(Img j:reportImgs){
//                    String jude = j.getSafeCount();
//                    j.setPhotoTagId(imgTypeMap.get(j.getPhotoTagId()));
//                    // 如果诊断隐患数为-1 就是未诊断
//                    if(jude.equals("-1")){
//                        j.setSafeCount("未诊断");
//                    }
//
//                    //查询该图片所有的打标记录
//                    OpeImg Temp = new OpeImg();
//                    Temp.setImgId(j.getId());
//                    List<OpeImg> opeTemp = opeImgMapper.findOpeImgs(Temp);
//                    //把里面的隐患类型 转化成实际的
//                    for(OpeImg k:opeTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    j.setOpeImgs(opeTemp);
//
//
//                    //查询该图片所有的推理记录
//                    InferImg inferImgTemp = new InferImg();
//                    inferImgTemp.setImgId(j.getId());
//                    List<InferImg> inferTemp = inferImgMapper.findInfers(inferImgTemp);
//                    //把里面的隐患类型 转化成实际的
//                    for(InferImg k:inferTemp){
//                        //查询隐患名称
//                        SafeType safeType1 = new SafeType();
//                        safeType1.setId(k.getSafeType());
//                        //替换成具体内容
//                        k.setSafeType(safeTypeMapper.findSafeType(safeType1).get(0).getType());
//                    }
//                    j.setInferImgs(inferTemp);
//                }
//                result.add(reportImgs);
//            }
//
//            return new ImgVo(pageBean.getTotal(), result);
//        }
//    }
//
//
//
//    @Override
//    public void delImg(Img a) {
//        imgMapper.delImg(a);
//    }
//
//    @Override
//    public void changeImg(Img a) {
//        imgMapper.changeImg(a);
//    }
}
