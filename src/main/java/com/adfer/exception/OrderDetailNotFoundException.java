package com.adfer.exception;

/**
 * Created by adrianferenc on 18.12.2016.
 */
public class OrderDetailNotFoundException extends RuntimeException {
    public OrderDetailNotFoundException(long orderDetailId) {
        super(prepareMessage(orderDetailId));
    }

    private static String prepareMessage(long orderDetailId) {
        StringBuilder message = new StringBuilder();
        message.append("Order detail with ID ");
        message.append(orderDetailId);
        message.append(" not found!");
        return message.toString();
    }
}
