/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts.dtos;

import java.sql.Timestamp;

/**
 *
 * @author SE130447
 */
public class DiscountCodeDTO {
    private String discountCode;
    private float percent;
    private Timestamp expiriDate;

    public DiscountCodeDTO() {
    }

    public DiscountCodeDTO(String discountCode, float percent, Timestamp expiriDate) {
        this.discountCode = discountCode;
        this.percent = percent;
        this.expiriDate = expiriDate;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Timestamp getExpiriDate() {
        return expiriDate;
    }

    public void setExpiriDate(Timestamp expiriDate) {
        this.expiriDate = expiriDate;
    }
    
    
}
