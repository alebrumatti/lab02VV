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
import br.unicamp.bookstore.model.PrecoPrazo;
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

	private PrecoPrazo precoPrazo;
	
	private String cep;
	private String peso;
	private String altura;
	private String largura;
	private String comprimento;
	private String tipoEntrega;

	private Throwable throwable;


	@Before
	public void setUp() {
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getConsultaPrecoPrazoUrl()).thenReturn("http://localhost:9876/ws");
		cep = null;
		peso = null;
		altura = null;
		largura = null;
		comprimento = null;
		tipoEntrega = null;
		throwable = null;
	}

	@After
	public void teardown() {
		wireMockServer.stop();
	}
	
	@Dado("^uma entrega válida:$")
	public void uma_entrega_valida(Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		peso = map.get("peso");
		largura = map.get("largura");
		altura = map.get("altura");
		comprimento = map.get("comprimento");
		tipoEntrega = map.get("tipoEntrega");
		
		wireMockServer.stubFor(get(urlMatching("/ws/"+ cep + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-CalculaFretePrazo.xml")));
	}
	
	@Quando("^eu informo o CEP onde o pedido deve ser entregue$")
	public void eu_informo_o_CEP_onde_o_pedido_deve_ser_entregue() throws Throwable {
		throwable = catchThrowable(() -> this.precoPrazo = calculaFretePrazoService.calcular(cep, peso, largura, altura, comprimento, tipoEntrega));
	}
}
