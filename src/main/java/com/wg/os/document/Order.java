/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wg.os.document;

import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Lenovo
 */
@Getter
@Setter
@Document("order")
public class Order {

    @Id
    private String orderId;
    private Boolean paided;
    private Double discount;
    private Date dueDate;
    private Double paidAmount;
    private Date orderDate;
    private Date updatDate;
    private Double orderTotalAmt;
    private Integer orderTotalQty;
    private Customer customer;
    private String accountId;
    private Double deliveryFee;
    private Payment payment;
    private String remark;
    private List<OrderDetail> listOrderDetail;
    private List<PaymentDetail> listPaymentDetail;
    private Boolean deleted;
    private Boolean deliveredStatus;

}
