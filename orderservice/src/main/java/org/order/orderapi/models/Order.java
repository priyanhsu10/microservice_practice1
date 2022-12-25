package org.order.orderapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "public",name = "tblorder")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private  String orderNumber;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;
    private   String orderStatus;



}
