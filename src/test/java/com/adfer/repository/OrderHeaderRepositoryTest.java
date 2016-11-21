package com.adfer.repository;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderHeaderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Test
    public void shouldPersistOneOrderHeader() {
        //given
        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .build();
        entityManager.persist(customer);

        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();

        //when
        orderHeaderRepository.save(orderHeader);

        //then
        OrderHeader persOrderHeader = entityManager.find(OrderHeader.class, orderHeader.getId());

        assertThat(persOrderHeader).isNotNull();
        assertThat(persOrderHeader.getId()).isNotNull();
        assertThat(persOrderHeader.getId()).isEqualTo(orderHeader.getId());
    }

    @Test
    public void shouldReturnNullMissingOrderHeaderWithGivenId() {
        //given
        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .build();
        entityManager.persist(customer);

        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();

        //when
        Long orderHeaderId = entityManager.persistAndGetId(orderHeader, Long.class);

        //then
        OrderHeader persOrderHeader = orderHeaderRepository.findOne(orderHeaderId + 100);

        assertThat(persOrderHeader).isNull();
    }

    @Test
    public void shouldUpdateAlreadyExistOrderHeader() {
        //given
        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .build();

        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();

        Long customerId = entityManager.persistAndGetId(customer, Long.class);
        Long orderHeaderId = entityManager.persistAndGetId(orderHeader, Long.class);

        //when
        OrderHeader updatedOrderHeader = orderHeaderRepository.findOne(orderHeaderId);
        updatedOrderHeader.getCustomer().setFirstName("New first name");
        updatedOrderHeader.getCustomer().setLastName("New last name");
        orderHeaderRepository.save(updatedOrderHeader);

        //then
        OrderHeader persOrderHeader = entityManager.find(OrderHeader.class, orderHeaderId);

        assertNotNull(persOrderHeader);
        assertThat(persOrderHeader.getId()).isEqualTo(orderHeaderId);
        assertNotNull(persOrderHeader.getCustomer());
        assertThat(persOrderHeader.getCustomer().getId()).isEqualTo(customerId);
        assertThat(persOrderHeader.getCustomer().getFirstName()).isEqualTo("New first name");
        assertThat(persOrderHeader.getCustomer().getLastName()).isEqualTo("New last name");
    }


}
