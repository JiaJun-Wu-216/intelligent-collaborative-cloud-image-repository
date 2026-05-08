package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.space.analyze.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Space;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.space.analyze.*;

import java.util.List;

/**
 * 空间分析相关应用层接口
 *
 * @author WuJiaJun
 */
public interface SpaceAnalyzeService extends IService<Space> {

    /**
     * 获取空间使用情况分析
     *
     * @param spaceUsageAnalyzeRequest 空间资源使用分析请求信息
     * @param loginUser                当前登录用户信息
     * @return 空间使用情况分析数据
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
                                                   User loginUser);

    /**
     * 获取空间图片分类分析
     *
     * @param spaceCategoryAnalyzeRequest 空间图片分类分析请求信息
     * @param loginUser                   当前登录用户
     * @return 空间图片分类分析数据
     */
    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest,
                                                               User loginUser);

    /**
     * 获取空间图片标签分析
     *
     * @param spaceTagAnalyzeRequest 空间图片标签分析请求信息
     * @param loginUser              当前登录用户
     * @return 空间图片标签分析数据
     */
    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest,
                                                     User loginUser);

    /**
     * 获取空间图片大小分析
     *
     * @param spaceSizeAnalyzeRequest 空间图片大小分析请求信息
     * @param loginUser               当前登录用户
     * @return 空间图片大小分析数据
     */
    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest,
                                                       User loginUser);

    /**
     * 获取用户上传行为分析
     *
     * @param spaceUserAnalyzeRequest 用户上传行为分析请求信息
     * @param loginUser               当前登录用户
     * @return 用户上传行为分析数据
     */
    List<SpaceUserAnalyzeResponse> getSpaceUserAnalyze(SpaceUserAnalyzeRequest spaceUserAnalyzeRequest,
                                                       User loginUser);

    /**
     * 获取空间使用排行分析（仅管理员）
     *
     * @param spaceRankAnalyzeRequest 空间使用排行分析请求信息
     * @param loginUser               当前登录用户
     * @return 空间使用排行分析
     */
    List<Space> getSpaceRankAnalyze(SpaceRankAnalyzeRequest spaceRankAnalyzeRequest,
                                    User loginUser);
}
