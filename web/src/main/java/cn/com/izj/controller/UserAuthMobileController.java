package cn.com.izj.controller;

import cn.com.izj.base.controller.BaseController;
import cn.com.izj.base.entity.UserAuth;
import cn.com.izj.base.response.ApiResult;
import cn.com.izj.dto.DriverCardInfo;
import cn.com.izj.dto.IdCardInfo;
import cn.com.izj.entity.User;
import cn.com.izj.service.SettingService;
import cn.com.izj.service.UserAuthService;
import cn.com.izj.utils.QiNiuUploadFile;
import cn.com.izj.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: 朱鸿平
 * @date: 2018/8/12 17:48
 */
@RestController
@RequestMapping("/mobile/card")
public class UserAuthMobileController extends BaseController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private SettingService settingService;

    /**
     * 上传身份证信息
     *
     * @param info 身份证信息
     */
    @PutMapping("/addIdCardInfo")
    public ApiResult addIdCardInfo(@RequestBody IdCardInfo info) {
        User user = (User) getUser();
        info.setUserId(user.getId());
        info.setRealNameAuthStatus(UserAuth.AUDIT_IN);
        return userAuthService.addIdCardInfo(info);
    }

    /**
     * 上传驾驶证信息
     *
     * @param info 身份证信息
     */
    @PutMapping("/addDriverCardInfo")
    public ApiResult addDriverCardInfo(@RequestBody DriverCardInfo info) {
        User user = (User) getUser();
        info.setUserId(user.getId());
        info.setDriverLicenceAuthStatus(UserAuth.AUDIT_IN);
        return userAuthService.addDriverCardInfo(info);
    }

    /**
     * 获取身份证信息
     */
    @GetMapping("/getIdCardInfo")
    public ApiResult getIdCardInfo() {
        User user = (User) getUser();
        return userAuthService.getIdCardInfo(user.getId());
    }

    /**
     * 获取驾驶证信息
     */
    @GetMapping("/getDriverCardInfo")
    public ApiResult getDriverCardInfo() {
        User user = (User) getUser();
        return userAuthService.getDriverCardInfo(user.getId());
    }

    /**
     * 获取个人信息
     */
    @GetMapping("/getUserInfo")
    public ApiResult getUserInfo() {
        User user = (User) getUser();
        return userAuthService.getUserInfo(user.getId());
    }

    /**
     * 上传文件
     */
    @PostMapping(value = "/uploadImage")
    public ApiResult uploadImage(@RequestParam("filename") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!checkFile(fileName)) {
            return ApiResult.errorWithData("文件格式不正确,只能上传图片格式！");
        }
        String uuid = StringUtil.getGUID();
        String newFileName = uuid + "." + getExtensionName(fileName);
        String name = QiNiuUploadFile.fileUpload(file.getInputStream(), newFileName);
        return ApiResult.successWithData(name);
    }

    /**
     * 检测文件扩展名
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,png,ico,bmp,jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件扩展名
     */
    private String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(".");
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 是否强制升级
     */
    @GetMapping("/forbidVersion")
    public ApiResult gertForbidVersion() {
        return settingService.getForbidVersion();
    }

    /**
     * 获取Ocr识别 token
     *
     * @param url
     */
    @PostMapping("/getIdNumber")
    public ApiResult getIdNumber(String url) throws Exception {
        String name = QiNiuUploadFile.getIdNumber(url);
        return ApiResult.successWithData(name);
    }

}
