package com.block_chain.KLTN.domain.location_tag;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;

@EntityView(LocationTagEntity.class)
public interface LocationTagEV {
    @IdMapping
    Long getId();
    String getProvince();
    String getDistrict();
    String getWard();
    String getPhone();
    String getCode();
}
