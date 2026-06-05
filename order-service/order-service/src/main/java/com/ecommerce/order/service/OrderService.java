package com.ecommerce.order.service;

import com.ecommerce.order.client.AvailabilityRequest;
import com.ecommerce.order.client.AvailabilityResponse;
import com.ecommerce.order.client.ProductDTO;
import com.ecommerce.order.client.ProductServiceClient;
import com.ecommerce.order.client.UserServiceClient;
import com.ecommerce.order.exception.BadRequestException;
import com.ecommerce.order.exception.ResourceNotFoundException;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    public Order createOrder(CreateOrderRequest request) {
        // Validate inputs
        if (request.getUserId() == null || request.getProductId() == null || request.getQuantity() == null) {
            throw new BadRequestException("UserId, ProductId, and Quantity are required");
        }
        if (request.getQuantity() <= 0) {
            throw new BadRequestException("Quantity must be greater than 0");
        }

        try {
            // 1. Validate user exists
            System.out.println("Fetching user with ID: " + request.getUserId());
            userServiceClient.getUser(request.getUserId());
            System.out.println("User found!");

            // 2. Validate product exists
            System.out.println("Fetching product with ID: " + request.getProductId());
            var product = productServiceClient.getProduct(request.getProductId());
            System.out.println("Product found! Price: " + product.getPrice());

            // 3. Check product availability
            AvailabilityRequest availReq = new AvailabilityRequest(request.getProductId(), request.getQuantity());
            System.out.println("Checking availability...");
            var availResp = productServiceClient.checkAvailability(availReq);

            if (!availResp.isAvailable()) {
                throw new BadRequestException("Product not available in requested quantity");
            }

            // 4. Create order
            Order order = new Order();
            order.setUserId(request.getUserId());
            order.setProductId(request.getProductId());
            order.setQuantity(request.getQuantity());
            order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            order.setShippingAddress(request.getShippingAddress());
            order.setPhoneNumber(request.getPhoneNumber());
            order.setNotes(request.getNotes());

            System.out.println("Creating order with total price: " + order.getTotalPrice());
            Order savedOrder = orderRepository.save(order);

            // 5. Reduce product quantity
            System.out.println("Reducing product quantity...");
            productServiceClient.reduceQuantity(availReq);

            System.out.println("Order created successfully!");
            return savedOrder;
        } catch (Exception e) {
            System.err.println("ERROR in createOrder: " + e.getMessage());
            e.printStackTrace();
            throw new BadRequestException("Failed to create order: " + e.getMessage());
        }
    }


    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+id));
    }

    public List<Order> getOrdersByUserId(Long userId){
//        return orderRepository.findByUserId(userId);
        List<Order> orders=orderRepository.findByUserId(userId);
        if (orders.isEmpty()){
            throw new ResourceNotFoundException("No orders found for user id: " + userId);
        }
        return orders;
    }

    public Order updateOrderStatus(Long id, String status){
        Order order=getOrderById(id);

        if(status==null || status.trim().isEmpty()){
            throw new BadRequestException("Status cannot be empty");
        }

        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    public Order updatePaymentStatus(Long id, String status){
        Order order=getOrderById(id);

        if (status == null || status.trim().isEmpty()) {
            throw new BadRequestException("Status cannot be empty");
        }

        order.setPaymentStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id){
        Order order=getOrderById(id);
        order.setOrderStatus("CANCELLED");
        orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}

