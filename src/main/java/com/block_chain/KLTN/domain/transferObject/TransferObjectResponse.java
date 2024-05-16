package com.block_chain.KLTN.domain.transferObject;

import java.time.OffsetDateTime;

public record TransferObjectResponse(
        Long id,
        Boolean atOfficeFlg,
        ReceiveShift receiveShift,
        CustomerShortResponse customer,
        PostOfficesShortResponse postOffice,
        OffsetDateTime actionDate
) {
    public record CustomerShortResponse(
            Long id,
            String fullName,
            String email,
            String phone
    ){
    }

    public record PostOfficesShortResponse(
            Long id,
            String name,
            String phone,
            String address
    ){}
}
