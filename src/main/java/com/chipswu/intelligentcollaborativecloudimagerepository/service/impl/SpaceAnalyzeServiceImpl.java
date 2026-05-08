package com.chipswu.intelligentcollaborativecloudimagerepository.service.impl;


import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.BusinessException;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ErrorCode;
import com.chipswu.intelligentcollaborativecloudimagerepository.exception.ThrowUtils;
import com.chipswu.intelligentcollaborativecloudimagerepository.mapper.SpaceMapper;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.analyze.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Picture;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Space;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.space.analyze.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.PictureService;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.SpaceAnalyzeService;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.SpaceService;
import com.chipswu.intelligentcollaborativecloudimagerepository.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 空间分析相关应用层接口实现类
 *
 * @author WuJiaJun
 */
@Service
public class SpaceAnalyzeServiceImpl extends ServiceImpl<SpaceMapper, Space> implements SpaceAnalyzeService {

    @Resource
    private UserService userService;

    @Resource
    private SpaceService spaceService;

    @Resource
    private PictureService pictureService;

    /**
     * 获取空间使用情况分析
     *
     * @param spaceUsageAnalyzeRequest 空间资源使用分析请求信息
     * @param loginUser                当前登录用户信息
     * @return 空间使用情况分析数据
     */
    @Override
    public SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
                                                          User loginUser) {
        // 1.校验参数

        // 全空间或公共图库，需要从 Picture 表来查询
        if (spaceUsageAnalyzeRequest.isQueryAll() || spaceUsageAnalyzeRequest.isQueryPublic()) {
            // 校验权限，仅管理员可以访问
            checkSpaceAnalyzeAuth(spaceUsageAnalyzeRequest, loginUser);
            // 统计图库的使用空间
            QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("pic_size");
            // 补充查询范围
            fillAnalyzeQueryWrapper(spaceUsageAnalyzeRequest, queryWrapper);
            // 查询数据库
            List<Object> pictureObjList = pictureService.getBaseMapper().selectObjs(queryWrapper);
            long usedSize = pictureObjList.stream().mapToLong(obj -> (Long) obj).sum();
            long usedCount = pictureObjList.size();
            // 封装返回结果
            SpaceUsageAnalyzeResponse response = new SpaceUsageAnalyzeResponse();
            response.setUsedSize(usedSize);
            response.setUsedCount(usedCount);
            // 公共图库（或者全部空间）无数量和容量限制、也没有比例
            response.setMaxSize(null);
            response.setSizeUsageRatio(null);
            response.setMaxCount(null);
            response.setCountUsageRatio(null);
            return response;
        } else {
            // 特定空间可以直接从 Space 表查询
            Long spaceId = spaceUsageAnalyzeRequest.getSpaceId();
            ThrowUtils.throwIf(spaceId == null || spaceId <= 0, ErrorCode.PARAMS_ERROR);
            // 获取空间信息
            Space space = spaceService.getById(spaceId);
            ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR, "空间不存在");
            // 校验权限，仅本人可以访问
            checkSpaceAnalyzeAuth(spaceUsageAnalyzeRequest, loginUser);
            // 封装返回结果
            SpaceUsageAnalyzeResponse response = new SpaceUsageAnalyzeResponse();
            response.setUsedSize(space.getTotalSize());
            response.setUsedCount(space.getTotalCount());
            response.setMaxSize(space.getMaxSize());
            response.setMaxCount(space.getMaxCount());
            // 计算比例
            double sizeUsageRatio = NumberUtil.round(space.getTotalSize() * 100.0 / space.getMaxSize(), 2).doubleValue();
            double countUsageRatio = NumberUtil.round(space.getTotalCount() * 100.0 / space.getMaxCount(), 2).doubleValue();
            response.setSizeUsageRatio(sizeUsageRatio);
            response.setCountUsageRatio(countUsageRatio);
            return response;
        }
    }

    /**
     * 获取空间图片分类分析
     *
     * @param spaceCategoryAnalyzeRequest 空间图片分类分析请求信息
     * @param loginUser                   当前登录用户
     * @return 空间图片分类分析数据
     */
    @Override
    public List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser) {
        ThrowUtils.throwIf(spaceCategoryAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        // 检查权限
        checkSpaceAnalyzeAuth(spaceCategoryAnalyzeRequest, loginUser);
        // 构造查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        // 补充查询条件
        fillAnalyzeQueryWrapper(spaceCategoryAnalyzeRequest, queryWrapper);
        // 分组查询
        queryWrapper.select("category", "count(*) as count", "sum(pic_size) as totalSize")
                .groupBy("category");
        // 查询并转换结果
        return pictureService.getBaseMapper().selectMaps(queryWrapper)
                .stream()
                .map(result -> {
                    String category = (String) result.get("category");
                    Long count = ((Number) result.get("count")).longValue();
                    Long totalSize = ((Number) result.get("totalSize")).longValue();
                    return new SpaceCategoryAnalyzeResponse(category, count, totalSize);
                }).toList();
    }

    /**
     * 获取空间图片标签分析
     *
     * @param spaceTagAnalyzeRequest 空间图片标签分析请求信息
     * @param loginUser              当前登录用户
     * @return 空间图片标签分析数据
     */
    @Override
    public List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser) {
        ThrowUtils.throwIf(spaceTagAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        // 检查权限
        checkSpaceAnalyzeAuth(spaceTagAnalyzeRequest, loginUser);
        // 构造查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        fillAnalyzeQueryWrapper(spaceTagAnalyzeRequest, queryWrapper);
        // 查询所有符合条件的标签
        queryWrapper.select("tags");
        List<String> tagsJsonList = pictureService.getBaseMapper().selectObjs(queryWrapper).stream()
                .filter(ObjUtil::isNotNull)
                .map(Object::toString)
                .toList();
        // 解析标签并统计
        Map<String, Long> tagCountMap = tagsJsonList.stream()
                // ["Java","Python"],["Java","PHP"] => "Java","Python","Java","PHP"
                .flatMap(tagsJson -> JSONUtil.toList(tagsJson, String.class).stream())
                .collect(Collectors.groupingBy(tag -> tag, Collectors.counting()));
        // 转换为响应对象
        return tagCountMap.entrySet().stream()
                // 降序排序
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(entry -> new SpaceTagAnalyzeResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    /**
     * 获取空间图片大小分析
     *
     * @param spaceSizeAnalyzeRequest 空间图片大小分析请求信息
     * @param loginUser               当前登录用户
     * @return 空间图片大小分析数据
     */
    @Override
    public List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser) {
        ThrowUtils.throwIf(spaceSizeAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        // 检查权限
        checkSpaceAnalyzeAuth(spaceSizeAnalyzeRequest, loginUser);
        // 构造查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        fillAnalyzeQueryWrapper(spaceSizeAnalyzeRequest, queryWrapper);
        // 查询所有符合条件的标签
        queryWrapper.select("pic_size");
        List<Long> picSizeList = pictureService.getBaseMapper().selectObjs(queryWrapper).stream()
                .filter(ObjUtil::isNotNull)
                .map(size -> (Long) size)
                .toList();
        // 定义分段范围，使用有序 Map
        Map<String, Long> sizeRanges = new LinkedHashMap<>();
        sizeRanges.put("<100KB", picSizeList.stream().filter(size -> size < 100 * 1024).count());
        sizeRanges.put("100KB-500KB", picSizeList.stream().filter(size -> size >= 100 * 1024 && size < 500 * 1024).count());
        sizeRanges.put("500KB-1MB", picSizeList.stream().filter(size -> size >= 500 * 1024 && size < 1024 * 1024).count());
        sizeRanges.put(">1MB", picSizeList.stream().filter(size -> size >= 1024 * 1024).count());
        // 转换为响应对象
        return sizeRanges.entrySet()
                .stream()
                .map(entry -> new SpaceSizeAnalyzeResponse(entry.getKey(), entry.getValue()))
                .toList();
    }


    /**
     * 获取用户上传行为分析
     *
     * @param spaceUserAnalyzeRequest 用户上传行为分析请求信息
     * @param loginUser               当前登录用户
     * @return 用户上传行为分析数据
     */
    @Override
    public List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest, User loginUser) {
        ThrowUtils.throwIf(spaceUserAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        // 检查权限
        checkSpaceAnalyzeAuth(spaceUserAnalyzeRequest, loginUser);
        // 构造查询条件
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        fillAnalyzeQueryWrapper(spaceUserAnalyzeRequest, queryWrapper);
        // 补充用户 ID 查询
        Long userId = spaceUserAnalyzeRequest.getUserId();
        queryWrapper.eq(ObjUtil.isNotNull(userId),
                "user_id",
                userId);
        // 补充分析维度：每日、每周、每月
        String timeDimension = spaceUserAnalyzeRequest.getTimeDimension();
        switch (timeDimension) {
            case "day":
                queryWrapper.select("DATE_FORMAT(create_time,'%Y-%m-%d') as period",
                        "count(*) as count");
                break;
            case "week":
                queryWrapper.select("YEARWEEK(create_time) as period",
                        "count(*) as count");
                break;
            case "month":
                queryWrapper.select("DATE_FORMAT(create_time,'%Y-%m') as period",
                        "count(*) as count");
                break;
            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "不支持的时间维度");
        }
        // 分组排序
        queryWrapper.groupBy("period").orderByAsc("period");
        // 查询并转换结果
        return pictureService.getBaseMapper().selectMaps(queryWrapper)
                .stream()
                .map(result -> {
                    String period = result.get("period").toString();
                    Long count = ((Number) result.get("count")).longValue();
                    return new SpaceUserAnalyzeResponse(period, count);
                }).toList();
    }

    /**
     * 获取空间使用排行分析（仅管理员）
     *
     * @param spaceRankAnalyzeRequest 空间使用排行分析请求信息
     * @param loginUser               当前登录用户
     * @return 空间使用排行分析
     */
    @Override
    public List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest, User loginUser) {
        ThrowUtils.throwIf(spaceRankAnalyzeRequest == null, ErrorCode.PARAMS_ERROR);
        // 检查权限，仅管理员可以查看
        ThrowUtils.throwIf(!userService.isAdmin(loginUser), ErrorCode.NO_AUTH_ERROR);
        // 构造查询条件
        QueryWrapper<Space> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "space_name", "user_id", "total_size")
                .orderByDesc("total_size")
                // 取前 N 名
                .last("limit " + spaceRankAnalyzeRequest.getTopN());
        // 查询结果
        return spaceService.list(queryWrapper);
    }

    /**
     * 校验空间分析权限
     *
     * @param spaceAnalyzeRequest 通用空间分析请求信息
     * @param loginUser           当前登录用户信息
     */
    private void checkSpaceAnalyzeAuth(SpaceAnalyzeRequest spaceAnalyzeRequest, User loginUser) {
        boolean isQueryPublic = spaceAnalyzeRequest.isQueryPublic();
        boolean isQueryAll = spaceAnalyzeRequest.isQueryAll();
        // 全空间分析或者公共图库权限校验，仅管理员可访问
        if (isQueryAll || isQueryPublic) {
            ThrowUtils.throwIf(!userService.isAdmin(loginUser), ErrorCode.NO_AUTH_ERROR);
        } else {
            // 分析特定空间，仅空间拥有者或管理员可以访问
            Long spaceId = spaceAnalyzeRequest.getSpaceId();
            ThrowUtils.throwIf(spaceId == null, ErrorCode.PARAMS_ERROR);
            Space space = spaceService.getById(spaceId);
            ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR, "空间不存在");
            spaceService.checkSpaceAuth(loginUser, space);
        }
    }

    /**
     * 根据请求对象封装查询条件
     *
     * @param spaceAnalyzeRequest 通用空间分析请求信息
     * @param queryWrapper        查询条件
     */
    private void fillAnalyzeQueryWrapper(SpaceAnalyzeRequest spaceAnalyzeRequest,
                                         QueryWrapper<Picture> queryWrapper) {
        boolean isQueryAll = spaceAnalyzeRequest.isQueryAll();
        // 全空间分析
        if (isQueryAll) {
            return;
        }
        // 公共图库
        boolean isQueryPublic = spaceAnalyzeRequest.isQueryPublic();
        if (isQueryPublic) {
            queryWrapper.isNull("space_id");
            return;
        }
        // 分析特定空间
        Long spaceId = spaceAnalyzeRequest.getSpaceId();
        if (spaceId != null) {
            queryWrapper.eq("space_id", spaceId);
            return;
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "未指定查询范围");
    }

}
