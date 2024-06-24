package org.example.networktechnologieslab.controller.datatransferobjects;

import java.sql.Date;

public class ReturnDto {
    private Long loanId;

    private Date returnDate;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
