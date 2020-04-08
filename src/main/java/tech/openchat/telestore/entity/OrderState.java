package tech.openchat.telestore.entity;

/**
 * @author vgorin
 * file created on 2020-04-07 16:34
 */

public enum OrderState {
    UNPAID, // awaiting payment
    PAID, // awaiting shipment
    SHIPPED, // awaiting delivery
    DELIVERED, // awaiting feedback (optionally)
    EXPIRED, // payment wasn't done in a timely manner, order is no longer valid
}
