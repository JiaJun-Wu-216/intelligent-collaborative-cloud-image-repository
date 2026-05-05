package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.PictureQueryRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.PictureReviewRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.PictureUploadByBatchRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.PictureUploadRequest;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Picture;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.PictureVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 图片相关应用层接口
 *
 * @author WuJiaJun
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource 文件输入源
     * @param request     当前请求信息
     * @param loginUser   登陆用户信息
     * @return 图片包装类
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest request, User loginUser);

    /**
     * 获取查询对象
     *
     * @param pictureQueryRequest 图片查询请求信息
     * @return 查询对象
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片包装类（单条）
     *
     * @param picture 图片实体信息
     * @param request 当前请求信息
     * @return 图片包装类（单条）
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param picturePage 图片分页信息
     * @param request     当前请求信息
     * @return 图片包装类（分页）
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片信息
     *
     * @param picture 图片信息
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     *
     * @param pictureReviewRequest 图片审核请求信息
     * @param user                 审核人员信息
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User user);

    /**
     * 填充审核参数
     *
     * @param picture   图片信息
     * @param loginUser 当前登录用户
     */
    void fillReviewParams(Picture picture, User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest 批量图片上传请求信息
     * @param loginUser                   当前登录用户
     * @return 成功上传的数量
     */
    Integer uploadPictureByBatch(PictureUploadByBatchRequest pictureUploadByBatchRequest,
                                 User loginUser);

    /**
     * 清楚图片文件
     *
     * @param oldPicture 旧图片文件
     */
    void clearPictureFile(Picture oldPicture);
}
