package com.rainbowletter.server.pet.application.port.out;

import com.rainbowletter.server.pet.application.port.out.dto.PetDashboardResponse;
import java.util.List;

public interface LoadPetDashboardPort {

    List<PetDashboardResponse> loadPetDashboard(String email);

}
