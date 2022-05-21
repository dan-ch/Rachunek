package com.example.rachunek.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KieConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/CartRules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/PieczywoRules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/NapojeRules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/ElektronikaRules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/OwoceRules.drl"));
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules/WarzywaRules.drl"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
