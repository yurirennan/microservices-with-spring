package com.renyu.orderservice.exceptions;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductsNotFoundException extends RuntimeException {

    private List<String> skuCodes;

    public ProductsNotFoundException(List<String> skuCodes) {
        this.skuCodes = skuCodes;
    }

    @Override
    public String getMessage() {
        return "Os produtos com os seguintes sku_codes estão fora de estoque: " + this.getSkuCodesInMessage();
    }

    private String getSkuCodesInMessage() {
        return String.join(", ", this.skuCodes);
    }
}
