package com.marketplace.domain.user.dao;

import java.util.List;

import com.marketplace.domain.PageData;
import com.marketplace.domain.common.SearchQuery;
import com.marketplace.domain.user.ProfileUpdateInput;
import com.marketplace.domain.user.User;
import com.marketplace.domain.user.UserPermission;

public interface UserDao {

    User create(User user);

    User update(ProfileUpdateInput values);
    
    void updatePermissions(long userId, List<User.Permission> permissions);

    void updateImage(long userId, String fileName);

    void updateRole(long userId, User.Role role);
    
    void updatePassword(long userId, String password);
    
    void updatePhoneNumber(long userId, String phone);
    
    void verifyPhoneNumber(long userId);
    
    void delete(long id);
    
    void deletePermissionsByUser(long userId);

    boolean existsById(long id);
    
    boolean existsByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    boolean existsByIdNotAndEmail(long userId, String email);
    
    long count();

    String getImage(long id);
    
    User.Role getRole(long id);

    User findById(long id);
    
    User findByPhone(String phone);
    
    User findByEmail(String email);
    
    UserPermission getUserPermission(long userId, String permission);
    
    List<User.Permission> getPermissionsByUser(long userId);
    
    PageData<User> findAll(SearchQuery query);
}
