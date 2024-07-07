package com.marketplace.data.general;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRequestRepo extends JpaRepository<OtpRequestEntity, OtpRequestEntity.ID> {

}
