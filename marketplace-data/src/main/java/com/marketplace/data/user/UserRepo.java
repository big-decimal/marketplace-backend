package com.marketplace.data.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketplace.domain.user.User;

public interface UserRepo extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    Optional<UserEntity> findByPhone(String phone);
    
    Optional<UserEntity> findByEmail(String mail);

    <T> Optional<T> getUserById(long id, Class<T> type);

    boolean existsByIdAndDisabledFalse(long id);

    boolean existsByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    boolean existsByIdNotAndEmail(long id, String email);

    @Modifying
    @Query("UPDATE User u SET u.image = :image WHERE u.id = :userId")
    void updateImage(@Param("userId") long userId, @Param("image") String image);
    
    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :userId")
    void updateRole(@Param("userId") long userId, @Param("role") User.Role role);

    @Modifying
    @Query("UPDATE User u SET u.phone = :phone WHERE u.id = :userId")
    void updatePhoneNumber(@Param("userId") long userId, @Param("phone") String phone);
    
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("userId") long userId, @Param("password") String password);
    
    @Modifying
    @Query("UPDATE User u SET u.phoneNumberVerified = :value WHERE u.id = :userId")
    void updatePhoneVerify(@Param("userId") long userId, @Param("value") boolean value);

}
