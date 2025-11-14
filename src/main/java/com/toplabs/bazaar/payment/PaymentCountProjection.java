package com.toplabs.bazaar.payment;

public interface PaymentCountProjection {
    Boolean getIsPaid();
    Long getCount();
}
