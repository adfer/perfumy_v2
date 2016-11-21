package com.adfer.repository;

import com.adfer.entity.Customer;
import com.adfer.entity.OrderDetail;
import com.adfer.entity.OrderHeader;
import com.adfer.entity.Perfume;
import com.adfer.service.PerfumeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adrianferenc on 08.11.2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderDetailRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Mock
    private PerfumeService perfumeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldPersistOneOrderDetail() {
        //given
        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .build();
        entityManager.persist(customer);

        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();
        entityManager.persist(orderHeader);

        Perfume perfume = Perfume.builder().build();
        entityManager.persist(perfume);

        OrderDetail orderDetail = OrderDetail.builder()
                .header(orderHeader)
                .perfume(perfume)
                .quantity(3)
                .build();

        //when
        orderDetailRepository.save(orderDetail);

        //then
        OrderDetail persOrderDetail = entityManager.find(OrderDetail.class, orderDetail.getId());

        assertThat(persOrderDetail).isNotNull();
        assertThat(persOrderDetail.getId()).isNotNull();
        assertThat(persOrderDetail.getId()).isEqualTo(orderDetail.getId());
        assertThat(persOrderDetail.getHeader()).isNotNull();
        assertThat(persOrderDetail.getPerfume()).isNotNull();
        assertThat(persOrderDetail.getQuantity()).isEqualTo(3);
    }

    @Test
    public void shouldReturnNullMissingOrderDetailWithGivenId() {
        //given
        OrderDetail persOrderDetail = orderDetailRepository.findOne(100L);

        assertThat(persOrderDetail).isNull();
    }

    @Test
    public void shouldUpdateAlreadyExistOrderDetail() {
        //given
        //given
        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .build();
        entityManager.persist(customer);

        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customer)
                .build();
        entityManager.persist(orderHeader);

        Perfume perfume1 = Perfume.builder()
                .name("Perfume 1")
                .brand("Brand 1")
                .build();

        Perfume perfume2= Perfume.builder()
                .name("Perfume 2")
                .brand("Brand 2")
                .build();

        entityManager.persist(perfume1);
        entityManager.persist(perfume2);

        OrderDetail orderDetail = OrderDetail.builder()
                .header(orderHeader)
                .perfume(perfume1)
                .quantity(3)
                .build();

        Long orderDetailId = entityManager.persistAndGetId(orderDetail, Long.class);

        //when
        OrderDetail updatedOrderDetail = orderDetailRepository.findOne(orderDetailId);
        updatedOrderDetail.setPerfume(perfume2);
        updatedOrderDetail.setQuantity(2);
        orderDetailRepository.save(updatedOrderDetail);

        //then
        OrderDetail persOrderDetail = entityManager.find(OrderDetail.class, orderDetailId);

        assertThat(persOrderDetail).isNotNull();
        assertThat(persOrderDetail.getId()).isEqualTo(orderDetailId);
        assertThat(persOrderDetail.getPerfume().getName()).isEqualTo("Perfume 2");
        assertThat(persOrderDetail.getPerfume().getBrand()).isEqualTo("Brand 2");
        assertThat(persOrderDetail.getQuantity()).isEqualTo(2);
    }


}
