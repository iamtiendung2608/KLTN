package com.block_chain.KLTN.domain.transfer_object;

import javax.validation.constraints.NotNull;

public record TransferObjectRequest(
        @NotNull Boolean atOfficeFlg,
        @NotNull ReceiveShift receiveShift,
        @NotNull Long customerId,
        Long postOfficeId
) {
}
