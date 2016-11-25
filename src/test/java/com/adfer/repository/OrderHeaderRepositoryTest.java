package com.adfer.repository;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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

  private Customer persCustomer;

  private OrderHeader orderHeader;

  @Before
  public void setUp() {
    persCustomer = Customer.builder()
        .firstName("First name")
        .lastName("Last name")
        .build();
    entityManager.persist(persCustomer);

    orderHeader = OrderHeader.builder()
        .customer(persCustomer)
        .build();
  }

  @Test
  public void shouldPersistOneOrderHeader() {
    //when
    orderHeaderRepository.save(orderHeader);

    //then
    OrderHeader persOrderHeader = entityManager.find(OrderHeader.class, orderHeader.getId());

    assertThat(persOrderHeader).isNotNull();
    assertThat(persOrderHeader.getId()).isNotNull();
    assertThat(persOrderHeader.getId()).isEqualTo(orderHeader.getId());
    assertThat(persOrderHeader.getCustomer()).isNotNull();
  }

  @Test
  public void shouldReturnNullMissingOrderHeaderWithGivenId() {
    //given
    Long orderHeaderId = entityManager.persistAndGetId(orderHeader, Long.class);

    //when
    OrderHeader persOrderHeader = orderHeaderRepository.findOne(orderHeaderId + 100);

    //then
    assertThat(persOrderHeader).isNull();
  }

  @Test
  public void shouldReturnOneOrderHeader() {
    //given
    Long orderHeaderId = entityManager.persistAndGetId(orderHeader, Long.class);

    //when
    OrderHeader persOrderHeader = orderHeaderRepository.findOne(orderHeaderId);

    //then
    assertThat(persOrderHeader).isNotNull();
    assertThat(persOrderHeader.getId()).isEqualTo(orderHeaderId);
  }

  @Test
  public void shouldReturnListWithTwoOrderHeaders() {
    //given
    OrderHeader orderHeader1 = OrderHeader.builder()
        .customer(persCustomer)
        .build();
    entityManager.persist(orderHeader1);

    OrderHeader orderHeader2 = OrderHeader.builder()
        .customer(persCustomer)
        .build();
    entityManager.persist(orderHeader2);

    //given
    Iterable<OrderHeader> persOrderHeaders = orderHeaderRepository.findAll();

    //then
    List<OrderHeader> orderHeaderList = new ArrayList<>();
    persOrderHeaders.forEach(orderHeader -> orderHeaderList.add(orderHeader));

    assertThat(orderHeaderList.size()).isEqualTo(2);
  }

  @Test
  public void shouldUpdateAlreadyExistOrderHeader() {
    //given
    Long customerId = persCustomer.getId();
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

  @Test
  public void shouldNotUpdateOtherOrderHeader() {
    //given
    Long orderHeaderId = entityManager.persistAndGetId(orderHeader, Long.class);

    Customer otherCustomer = Customer.builder()
        .firstName("Other customer's first name")
        .lastName("Other customer's last name")
        .build();
    entityManager.persist(otherCustomer);

    OrderHeader otherOrderHeader = OrderHeader.builder()
        .customer(otherCustomer)
        .build();
    Long otherOrderHeaderId = entityManager.persistAndGetId(otherOrderHeader, Long.class);

    //when
    OrderHeader updatedOrderHeader = orderHeaderRepository.findOne(orderHeaderId);
    updatedOrderHeader.getCustomer().setFirstName("New first name");
    updatedOrderHeader.getCustomer().setLastName("New last name");
    orderHeaderRepository.save(updatedOrderHeader);

    //then
    OrderHeader persOtherOrderHeader = entityManager.find(OrderHeader.class, otherOrderHeaderId);

    assertThat(persOtherOrderHeader.getId()).isEqualTo(otherOrderHeaderId);
    assertThat(persOtherOrderHeader.getCustomer().getFirstName()).isEqualTo("Other customer's first name");
    assertThat(persOtherOrderHeader.getCustomer().getLastName()).isEqualTo("Other customer's last name");
  }

}
