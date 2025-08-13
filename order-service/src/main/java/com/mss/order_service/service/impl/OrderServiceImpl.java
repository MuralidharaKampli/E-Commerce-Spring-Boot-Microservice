package com.mss.order_service.service.impl;

import com.mss.order_service.dto.Message;
import com.mss.order_service.dto.OrderItemDTO;
import com.mss.order_service.dto.OrderRequest;
import com.mss.order_service.dto.OrderRequest.OrderDTO;
import com.mss.order_service.dto.OrderResponse;
import com.mss.order_service.dto.PaymentDTO;
import com.mss.order_service.dto.UserDTO;
import com.mss.order_service.entity.Address;
import com.mss.order_service.entity.Order;
import com.mss.order_service.entity.OrderItem;
import com.mss.order_service.entity.OrderStatus;
import com.mss.order_service.exception.OrderNotFoundException;
import com.mss.order_service.exception.ProductNotFoundException;
import com.mss.order_service.exception.ProductStockExceedException;
import com.mss.order_service.feignclient.PaymentServiceClient;
import com.mss.order_service.feignclient.ProductServiceClient;
import com.mss.order_service.feignclient.UserServiceClient;
import com.mss.order_service.repository.OrderRepository;
import com.mss.order_service.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;
    
    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderDTO orderDTO = request.getOrder();

        UserDTO user = userServiceClient.getUserById(orderDTO.getUserId());
        if (user == null) {
			throw new OrderNotFoundException("User not found with ID: " + orderDTO.getUserId());
		}

        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            double price = productServiceClient.getProductPrice(itemDTO.getProductId());
            if (price == 0.0) {
                throw new ProductNotFoundException("Product not found with ID: " + itemDTO.getProductId());
            }

            OrderItem item = new OrderItem();
            item.setProductId(itemDTO.getProductId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(price);
            item.setOrder(order);

            System.out.println(itemDTO.getProductId() + " " + itemDTO.getQuantity());

            int productStock = productServiceClient.updateProductStock(itemDTO.getProductId(), itemDTO.getQuantity());
            System.out.println(productStock);
            if (productStock < 0 || productStock == -1) {
                throw new ProductStockExceedException("Product stock is out of stock or limited, buy the product with the stock available for product: " + itemDTO.getProductId());
            }

            items.add(item);
        }
        
        order.setItems(items);

        double totalAmount = 0.0;
        for (OrderItem i : items) {
            totalAmount += i.getPrice() * i.getQuantity();
        }
        order.setTotalAmount(totalAmount);
        
		Address address = new Address();
		address.setStreet(orderDTO.getAddress().getStreet());
		address.setDistrict(orderDTO.getAddress().getDistrict());
		address.setState(orderDTO.getAddress().getState());
		address.setCountry(orderDTO.getAddress().getCountry());
		address.setPincode(orderDTO.getAddress().getPincode());
		address.setOrder(order);
		order.setAddress(address);
		
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(totalAmount);
		paymentDTO.setTxnType("UPI");
		Long paymentId = paymentServiceClient.addPayment(paymentDTO);
		order.setTxnId(paymentId);

        Order savedOrder = orderRepository.save(order);

        OrderResponse response = new OrderResponse();
        OrderResponse.OrderDetails details = new OrderResponse.OrderDetails();
        details.setOrderId(savedOrder.getOrderId());
        details.setUserId(savedOrder.getUserId());
        details.setOrderDate(savedOrder.getOrderDate());
        details.setStatus(savedOrder.getStatus().name());
        details.setTotalAmount(savedOrder.getTotalAmount());
        
        com.mss.order_service.dto.Address address1 = new com.mss.order_service.dto.Address();
        address1.setStreet(savedOrder.getAddress().getStreet());
		address1.setDistrict(savedOrder.getAddress().getDistrict());
		address1.setState(savedOrder.getAddress().getState());
		address1.setCountry(savedOrder.getAddress().getCountry());
		address1.setPincode(savedOrder.getAddress().getPincode());
		address1.setAddressId(savedOrder.getAddress().getAddressId());
		details.setAddress(address1 );

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem i : savedOrder.getItems()) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductId(i.getProductId());
            dto.setQuantity(i.getQuantity());
            dto.setPrice(i.getPrice());
            itemDTOs.add(dto);
        }

        details.setItems(itemDTOs);
        details.setTxnId(paymentId);
        response.setMessage("Order stored successfully");
        response.setOrder(details);

        return response;
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        OrderResponse response = new OrderResponse();
        OrderResponse.OrderDetails details = new OrderResponse.OrderDetails();
        details.setOrderId(order.getOrderId());
        details.setUserId(order.getUserId());
        details.setOrderDate(order.getOrderDate());
        details.setStatus(order.getStatus().name());
        details.setTotalAmount(order.getTotalAmount());
        
		com.mss.order_service.dto.Address address1 = new com.mss.order_service.dto.Address();
		address1.setAddressId(order.getAddress().getAddressId());
		address1.setStreet(order.getAddress().getStreet());
		address1.setDistrict(order.getAddress().getDistrict());
		address1.setState(order.getAddress().getState());
		address1.setCountry(order.getAddress().getCountry());
		address1.setPincode(order.getAddress().getPincode());
		details.setAddress(address1);

        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem i : order.getItems()) {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setProductId(i.getProductId());
            dto.setQuantity(i.getQuantity());
            dto.setPrice(i.getPrice());
            itemDTOs.add(dto);
        }

        details.setItems(itemDTOs);
        response.setMessage("Order fetched successfully");
        response.setOrder(details);

        return response;
    }

    @Override
    public Message updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        Message message = new Message();
        message.setMessage("Order status updated successfully");
        return message;
    }

    @Override
    public Message deleteOrder(Long id) {
        Optional<Order> orderFound = orderRepository.findById(id);
        Message message = new Message();
        if (orderFound.isPresent()) {
            orderRepository.deleteById(id);
            message.setMessage("Order deleted successfully");
        } else {
            message.setMessage("Order delete unsuccessfully, Order id not found");
        }
        return message;
    }
}
