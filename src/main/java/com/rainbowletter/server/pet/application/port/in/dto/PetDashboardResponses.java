package com.rainbowletter.server.pet.application.port.in.dto;

import com.rainbowletter.server.pet.application.port.out.dto.PetDashboardResponse;
import java.util.List;

public record PetDashboardResponses(List<PetDashboardResponse> pets) {

    public static PetDashboardResponses from(final List<PetDashboardResponse> pets) {
        return new PetDashboardResponses(pets);
    }

}
