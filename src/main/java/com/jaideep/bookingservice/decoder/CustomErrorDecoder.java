package com.jaideep.bookingservice.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaideep.bookingservice.exception.BookingException;
import com.jaideep.bookingservice.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new BookingException(
                    errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(),
                    response.status()
            );
        }
        catch (IOException e) {
            throw new BookingException("Internal Server Error", "INTERNAL SERVER ERROR", 500);
        }
    }
}
