package com.block_chain.KLTN.domain.transferObject;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public record TransferObjectRequest(
        @NotNull Boolean atOfficeFlg,
        @NotNull ReceiveShift receiveShift,
        @NotNull Long customerId,
        Long postOfficeId,
        OffsetDateTime actionDate
) {
}
