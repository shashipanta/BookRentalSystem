package com.brs.bookrentalsystem.projections;

public interface BookTransactionProjection {
    Long getBookTransactionId();
    String getTransactionCode();
    String getRentFrom();
    String getRentTo();
    String getRentStatus();

    // book
    Integer getBookId();
    String getBookName();
    String getBookCoverImage();
    String getIsbn();
    Integer getStockCount();
    String getPublishedDate();

    // category
    Short getCategoryId();
    String getCategoryName();

    // member
    Integer getMemberId();
    String getMemberName();
    String getMemberMobileNumber();

}
