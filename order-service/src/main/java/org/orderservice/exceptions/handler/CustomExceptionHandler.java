package org.orderservice.exceptions.handler;

import org.orderservice.dto.inventory.InventoryResponseDTO;
import org.orderservice.exceptions.ProductOutOfStockException;
import org.orderservice.exceptions.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ProductOutOfStockException.class})
    protected ResponseEntity<Object> handleConflict(final ProductOutOfStockException ex) {

        final List<InventoryResponseDTO> productsOutOfStock = ex.getProductsOutOfStock();

        final StringBuilder messageBuilder = new StringBuilder("Os seguintes produtos estão fora de estoque: ");

        for (int i = 0; i < productsOutOfStock.size(); i++) {
            final InventoryResponseDTO inventoryResponseDTO = productsOutOfStock.get(i);

            messageBuilder.append(inventoryResponseDTO.getUpcCode());

            if (i < productsOutOfStock.size() - 1) {
                messageBuilder.append(", ");
            } else {
                messageBuilder.append(".");
            }
        }

        final String messageError = messageBuilder.toString();

        final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, messageError, messageError);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
