package ch.postfinance.swiss.hacks.service;

import ch.postfinance.swiss.hacks.domain.SupportContactInformation;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class SupportService {

    public List<SupportContactInformation> getSupportInformation() {
        return SupportContactInformation.findAll().list();
    }
}
