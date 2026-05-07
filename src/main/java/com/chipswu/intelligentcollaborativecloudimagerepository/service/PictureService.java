package com.chipswu.intelligentcollaborativecloudimagerepository.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.dto.picture.*;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.Picture;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.entity.User;
import com.chipswu.intelligentcollaborativecloudimagerepository.model.vo.PictureVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

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
     * 清除图片文件
     *
     * @param oldPicture 旧图片文件
     */
    void clearPictureFile(Picture oldPicture);

    /**
     * 校验空间图片的权限
     *
     * @param loginsUser 当前登录用户信息
     * @param picture    图片信息
     */
    void checkPictureAuth(User loginsUser, Picture picture);

    /**
     * 删除图片
     *
     * @param pictureId 图片主键
     * @param loginUser 当前登录用户
     */
    void deletePicture(long pictureId, User loginUser);

    /**
     * 编辑图片
     *
     * @param pictureEditRequest 图片编辑请求信息
     * @param loginUser          当前登录用户
     */
    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    /**
     * 根据图片颜色搜索图片
     *
     * @param spaceId   空间 ID
     * @param picColor  图片颜色
     * @param loginUser 当前登录用户信息
     * @return 搜寻到的图片列表
     */
    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    /**
     * 批量编辑图片
     *
     * @param pictureEditByBatchRequest 批量编辑图片请求信息
     * @param loginUser                 当前登录用户
     */
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);
}
