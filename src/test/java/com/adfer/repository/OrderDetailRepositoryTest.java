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

    private Customer persCustomer;

    private OrderHeader persOrderHeader;

    private Perfume persPerfume;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        persCustomer = Customer.builder()
            .firstName("First name")
            .lastName("Last name")
            .build();
        entityManager.persist(persCustomer);

        persOrderHeader = OrderHeader.builder()
            .customer(persCustomer)
            .build();
        entityManager.persist(persOrderHeader);

        persPerfume = Perfume.builder()
            .name("Perfume 1")
            .build();
        entityManager.persist(persPerfume);
    }

    @Test
    public void shouldPersistOneOrderDetail() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
                .header(persOrderHeader)
                .perfume(persPerfume)
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
        assertThat(persOrderDetail.getHeader().getId()).isEqualTo(persOrderHeader.getId());
        assertThat(persOrderDetail.getPerfume()).isNotNull();
        assertThat(persOrderDetail.getPerfume().getId()).isEqualTo(persPerfume.getId());
        assertThat(persOrderDetail.getQuantity()).isEqualTo(3);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhileSaveOrderDetailMissingPerfume() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
            .header(persOrderHeader)
            .quantity(3)
            .build();

        //when
        orderDetailRepository.save(orderDetail);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhileSaveOrderDetailMissingOrderHeader() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
            .perfume(persPerfume)
            .quantity(3)
            .build();

        //when
        orderDetailRepository.save(orderDetail);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhileSaveOrderDetailMissingQuantity() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
            .header(persOrderHeader)
            .perfume(persPerfume)
            .build();

        //when
        orderDetailRepository.save(orderDetail);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhileSaveOrderDetailNegativeQuantity() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
            .header(persOrderHeader)
            .perfume(persPerfume)
            .quantity(-1)
            .build();

        //when
        orderDetailRepository.save(orderDetail);
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhileSaveOrderDetailWithZeroQuantity() {
        //given
        OrderDetail orderDetail = OrderDetail.builder()
            .header(persOrderHeader)
            .perfume(persPerfume)
            .quantity(0)
            .build();

        //when
        orderDetailRepository.save(orderDetail);
    }

    @Test
    public void shouldReturnNullMissingOrderDetailWithGivenId() {
        //when
        OrderDetail persOrderDetail = orderDetailRepository.findOne(-100L);

        //then
        assertThat(persOrderDetail).isNull();
    }

    @Test
    public void shouldUpdateAlreadyExistOrderDetail() {
        //given
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
                .header(persOrderHeader)
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
