package com.marketplace.data.user;

import com.marketplace.data.AuditMapper;
import com.marketplace.domain.user.User;

public interface UserMapper {

//    public static User toDomain(UserEntity entity) {
//        var u = toDomainCompat(entity);
//        if (entity.getPermissions() != null) {
//        	var permissions = entity.getPermissions().stream()
//        			.map(UserPermissionEntity::getPermission)
//        			.toList();
//        	u.setPermissions(permissions);
//        }
//        return u;
//    }
    
    public static User toDomain(UserEntity entity) {
        var u = new User();
        u.setId(entity.getId());
        u.setName(entity.getName());
        u.setPhone(entity.getPhone());
        u.setEmail(entity.getEmail());
        u.setPassword(entity.getPassword());
        u.setDisabled(entity.isDisabled());
        u.setImage(entity.getImage());
        u.setRole(entity.getRole());
        u.setPhoneNumberVerified(entity.isPhoneNumberVerified());
        u.setAudit(AuditMapper.from(entity));
        return u;
    }

}
