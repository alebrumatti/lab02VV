package br.com.unicamp.bookstore.status;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:target/cucumber" },
        glue = "br.unicamp.bookstore.status",
        features = "classpath:features/StatusEntrega.feature"
)
public class RunConsultaStatusEntregaTest {

}
