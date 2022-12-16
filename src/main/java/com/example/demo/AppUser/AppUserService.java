package com.example.demo.AppUser;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService {

    private AppUserRepository appUserRepository;

    public Long addAppUser(AppUser appUser) {
        Optional<AppUser> appUserOptional = appUserRepository.findAppUserByUsername(appUser.getUsername());
        if (appUserOptional.isPresent()) {
            // 用户名重复，返回-1
            return -1L;
        }
        // 对密码进行md5加密
        String password_md5 = DigestUtils.md5DigestAsHex(appUser.getPassword().getBytes(StandardCharsets.UTF_8));
        appUser.setPassword(password_md5);
        appUser.setUserLevel(0);
        appUser.setSignTime(LocalDateTime.now());
        appUser.setModifyTime(LocalDateTime.now());
        appUserRepository.save(appUser);
        return appUser.getId();
    }

    public JSONObject findAppUser(String username, String password) {
        JSONObject info = new JSONObject();

        Optional<AppUser> appUserOptional = appUserRepository.findAppUserByUsername(username);
        if (appUserOptional.isEmpty()) {
            // 用户名不存在，返回-1
            info.put("id", -1L);
            return info;
        }
        String password_md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!Objects.equals(appUserOptional.get().getPassword(), password_md5)) {
            // 密码错误，返回-2
            info.put("id", -2L);
            return info;
        }

        info.put("id", appUserOptional.get().getId());
        info.put("type", appUserOptional.get().getUserType());
        info.put("city", appUserOptional.get().getCity());

        return info;
    }

    @Transactional
    public void updateAppUser(Long id, String password, String phone, String detail) {
        AppUser appUser = appUserRepository.findAppUserById(id).get();
        if (password != null) {
            String password_md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            appUser.setPassword(password_md5);
        }
        if (phone != null) {
            appUser.setPhone(phone);
        }
        if (detail != null) {
            appUser.setDetail(detail);
        }
        appUser.setModifyTime(LocalDateTime.now());
    }

    public AppUser getUserById(Long userId) {
        return appUserRepository.findAppUserById(userId).get();
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }
}
