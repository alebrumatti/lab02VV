package br.unicamp.bookstore.endereco;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.tomakehurst.wiremock.WireMockServer;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.Endereco;
import br.unicamp.bookstore.service.CalculaFretePrazoService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class CalculaFretePrazoSteps {

	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private CalculaFretePrazoService calculaFretePrazoService;

	private String cep;
	private float peso;
	private float altura;
	private float largura;
	private float comprimento;
	private int	tipoEntrega;

	private Throwable throwable;


	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9876/ws");
		cep = null;
		peso = 0;
		altura = 0;
		largura = 0;
		comprimento = 0;
		tipoEntrega = 0;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}
}
