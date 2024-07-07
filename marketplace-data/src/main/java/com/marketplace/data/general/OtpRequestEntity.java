package com.marketplace.data.general;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Objects;

import com.marketplace.domain.Constants;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "OtpRequest")
@Table(name = Constants.TABLE_PREFIX + "otp_request")
public class OtpRequestEntity {
	
	@EmbeddedId
	private OtpRequestEntity.ID id;

	private int count;
	
	@Version
	private long version;
	
	public OtpRequestEntity() {
		this.id = new ID();
	}

	@Getter
	@Setter
	@Embeddable
	public static class ID implements Serializable {

		private static final long serialVersionUID = 1L;

		private String phone;

		private String date; // yyyy-MM-dd

		public ID() {

		}

		public ID(String phone) {
			this(phone, LocalDate.now(ZoneOffset.UTC).toString());
		}

		public ID(String phone, String date) {
			this.phone = phone;
			this.date = date;
		}

		@Override
		public int hashCode() {
			return Objects.hash(date, phone);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ID other = (ID) obj;
			return Objects.equals(date, other.date) && Objects.equals(phone, other.phone);
		}

	}

}
