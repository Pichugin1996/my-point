package com.weblinestudio.mypoint.validate;

import com.weblinestudio.mypoint.dto.ItemRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;

@Component
@Slf4j
public class ItemFormValidator {

    private final ValidatorProperties validatorProperties;

    private boolean error = false;
    private ItemRequestDto item;
    private BindingResult bindingResult;

    @Autowired
    public ItemFormValidator(ValidatorProperties validatorProperties) {
        this.validatorProperties = validatorProperties;
    }

    public void verify(ItemRequestDto item, BindingResult bindingResult) {
        init(item, bindingResult);

        checkPrice();
        checkAmount();
        checkWeight();
    }

    private void init(ItemRequestDto item, BindingResult bindingResult) {
        log.debug("verify -- start item: {}", item);
        error = false;
        this.item = item;
        this.bindingResult = bindingResult;
        log.debug("verify -- start Validator: {}", this);
    }

    private void checkPrice() {
        log.debug("checkPrice -- start");
        if (item.getPrice() == null) {
            item.setPrice("0.0");
            log.debug("checkPrice -- checkNull:error price");
            error = true;
        } else if (item.getPrice() == "") {
            item.setPrice("0.0");
            log.debug("checkPrice -- checkEquals:error price");
            error = true;
        } else {
            item.setPrice(item.getPrice().replaceAll("\\s+", ""));
        }

        try {
            BigDecimal bigDecimal = new BigDecimal(item.getPrice());
            item.setPrice(bigDecimal.toString());
        } catch (Exception ex) {
            error = true;
            item.setPrice("0.0");
            log.debug("checkPrice -- checkBigDecimal:error price");
        }
    }

    private void checkAmount() {
        log.debug("checkAmount -- start");
        if (item.getAmount() == null) {
            item.setAmount("0");
            log.debug("checkAmount -- checkNull:error amount");
            error = true;
        } else if (item.getAmount().equals("")) {
            item.setAmount("0");
            log.debug("checkAmount -- checkEquals:error amount");
            error = true;
        } else {
            item.setAmount(item.getAmount().replaceAll("\\s+", ""));
        }
        try {
            Long longString = Long.valueOf(item.getAmount());
            item.setAmount(longString.toString());
        } catch (Exception ex) {
            error = true;
            item.setAmount("0");
            log.debug("checkAmount -- checkLong:error amount");
        }
    }

    private void checkWeight() {
        log.debug("checkWeight -- start");
        if (item.getWeight() == null) {
            item.setWeight("0");
            log.debug("checkWeight -- checkNull:error weight");
            error = true;
        } else if (item.getWeight().equals("")) {
            item.setWeight("0");
            log.debug("checkWeight -- checkEquals:error weight");
            error = true;
        } else {
            item.setWeight(item.getWeight().replaceAll("\\s+", ""));
        }
        try {
            Double doubleString = Double.valueOf(item.getWeight());
            item.setWeight(doubleString.toString());
        } catch (Exception ex) {
            error = true;
            item.setWeight("0");
            log.debug("checkWeight -- checkDouble:error weight");
        }
    }

    public boolean isError() {
        return error;
    }
}
