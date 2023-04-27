package com.shoppingcenter.data.sale;

import java.io.Serializable;
import java.time.YearMonth;

import com.shoppingcenter.data.AuditingEntity;
import com.shoppingcenter.data.shop.ShopEntity;
import com.shoppingcenter.domain.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "SaleHistory")
@Table(name = Constants.TABLE_PREFIX + "sale_history")
public class SaleHistoryEntity extends AuditingEntity {

    @EmbeddedId
    private SaleHistoryEntity.ID id;

    private double totalSale;

    @MapsId("shop_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private ShopEntity shop;

    public SaleHistoryEntity() {
        this.id = new ID();
    }

    @Getter
    @Setter
    @Embeddable
    public static class ID implements Serializable {

        private static final long serialVersionUID = 1L;

		@Column(name = "shop_id")
        private long shopId;

        private int year;

        private int month;

        public ID() {

        }

        public ID(long shopId) {
            this(shopId, YearMonth.now());
        }

        public ID(long shopId, YearMonth yearMonth) {
            this(shopId, yearMonth.getYear(), yearMonth.getMonthValue());
        }

        public ID(long shopId, int year, int month) {
            this.shopId = shopId;
            this.year = year;
            this.month = month;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (shopId ^ (shopId >>> 32));
            result = prime * result + year;
            result = prime * result + month;
            return result;
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
            if (shopId != other.shopId)
                return false;
            if (year != other.year)
                return false;
            if (month != other.month)
                return false;
            return true;
        }

    }
}