package com.block_chain.KLTN.domain.transfer_object;

import lombok.Getter;

@Getter
public enum ReceiveShift {
    MORNING,
    AFTERNOON,
    NIGHT,
    WORK_HOUR,
    WEEKEND,
    HOLIDAY,
    ALL_DAY
}
