package com.block_chain.KLTN.domain.transfer_object;

import java.time.OffsetDateTime;

import com.block_chain.KLTN.domain.location_tag.LocationTagResponse;

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
        String phone,
        String address,
        LocationTagResponse locationTag
    ){}

    public record PostOfficesShortResponse(
        Long id,
        String name,
        String phone,
        String address
    ){}
}
