package com.rainbowletter.server.faq.application.port.out;

import com.rainbowletter.server.faq.application.domain.model.Faq;

public interface SaveFaqPort {

    Faq saveFaq(Faq faq);

}
