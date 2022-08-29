package com.weblinestudio.mypoint.validate;

import com.weblinestudio.mypoint.dto.ItemRequestDto;
import com.weblinestudio.mypoint.entity.business.Item;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql",
        "/create-item-before.sql",
        "/create-category-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-roles-after.sql",
        "/create-user-after.sql",
        "/create-item-after.sql",
        "/create-category-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ItemFormValidatorTest {

    @Autowired
    private ItemFormValidator itemFormValidator;

    @MockBean
    private BindingResult bindingResult;

    private ItemRequestDto itemRequestDto;

    @Test
    void contextLoadsTest() {
        assertThat(itemFormValidator).isNotNull();
    }

    @Test
    void correctItem1Test() {
        ItemRequestDto item = new ItemRequestDto();
        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setAmount("10");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);


        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(false, itemFormValidator.isError());
    }

    @Test
    void priceItem1Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setPrice(null);

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setAmount("10");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void priceItem2Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setPrice("");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setAmount("10");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void priceItem3Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setPrice("fgddsf");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setAmount("10");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void amountItem1Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setAmount("");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void amountItem2Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setAmount(null);

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void amountItem3Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setAmount("asdasd");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setWeight("0.5");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void weightItem1Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setWeight(null);

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setAmount("10");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void weightItem2Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setWeight("");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setAmount("10");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }

    @Test
    void weightItem3Test() {
        ItemRequestDto item = new ItemRequestDto();

        item.setWeight("asdas");

        item.setId(1L);
        item.setUserId(10L);
        item.setTitleName("Name");
        item.setCategory("Category");
        item.setPrice("10.1");
        item.setAmount("10");
        item.setCreationDate("08-26-2022+11:48:40");
        item.setStatus(Item.Status.ACTIVE);

        itemFormValidator.verify(item, bindingResult);
        Assert.assertEquals(true, itemFormValidator.isError());
    }



}