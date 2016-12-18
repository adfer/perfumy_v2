package com.adfer.exception;

/**
 * Created by adrianferenc on 18.12.2016.
 */
public class OrderHeaderNotFoundException extends RuntimeException {
    public OrderHeaderNotFoundException(long orderHeaderId) {
        super(prepareMessage(orderHeaderId));
    }

    private static String prepareMessage(long orderHeaderId) {
        StringBuilder message = new StringBuilder();
        message.append("Order header with ID ");
        message.append(orderHeaderId);
        message.append(" not found!");
        return message.toString();
    }
}
