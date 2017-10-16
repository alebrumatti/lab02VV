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
	
	@Dado("^um CEP inválido:$")
	public void um_CEP_inválido(Map<String, String> map) throws Throwable {
		cep = map.get("cep");
		
		wireMockServer.stubFor(get(urlMatching("/ws/" + cep + ".*"))
				.willReturn(aResponse().withStatus(400).withHeader("Content-Type", "text/xml")
						.withBodyFile("resultado-pesquisa-CalculaFretePrazo_BAD.xml")));
	}
	
	@Quando("^eu informo o CEP onde o pedido deve ser entregue$")
	public void eu_informo_o_CEP_onde_o_pedido_deve_ser_entregue() throws Throwable {
		throwable = catchThrowable(() -> this.precoPrazo = calculaFretePrazoService.calcular(cep, peso, largura, altura, comprimento, tipoEntrega));
	}
	
	@E("^o serviço CalculaFretePrazo está indisponível$")
	public void o_servico_CalculaFretePrazo_esta_indisponível() throws Throwable {
		wireMockServer.stubFor(get(urlMatching("/ws/.*")).willReturn(aResponse().withStatus(200)
				.withFixedDelay(6000).withBodyFile("resultado-pesquisa-CalculaFretePrazo_out.xml")));
	}
	
	@Então("^o resultado deve ser o valor de frete e tempo de entrega:$")
	public void o_resultado_deve_ser_o_valor_de_frete_e_tempo_de_entrega(List<Map<String,String>> resultado)
			throws Throwable {
		assertThat(this.precoPrazo.getCodigo()).isEqualTo(resultado.get(0).get("Codigo"));
		assertThat(this.precoPrazo.getValor()).isEqualTo(resultado.get(0).get("Valor"));
		assertThat(this.precoPrazo.getPrazoEntrega()).isEqualTo(resultado.get(0).get("PrazoEntrega"));
		assertThat(this.precoPrazo.getValorMaoPropria()).isEqualTo(resultado.get(0).get("ValorMaoPropria"));
		assertThat(this.precoPrazo.getValorAvisoRecebimento()).isEqualTo(resultado.get(0).get("ValorAvisoRecebimento"));
		assertThat(this.precoPrazo.getValorValorDeclarado()).isEqualTo(resultado.get(0).get("ValorValorDeclarado"));
		assertThat(this.precoPrazo.getEntregaDomiciliar()).isEqualTo(resultado.get(0).get("EntregaDomiciliar"));
		assertThat(this.precoPrazo.getEntregaSabado()).isEqualTo(resultado.get(0).get("EntregaSabado"));
		assertThat(this.precoPrazo.getErro()).isEqualTo(resultado.get(0).get("Erro"));
		assertThat(this.precoPrazo.getMsgErro()).isEqualTo(resultado.get(0).get("MsgErro"));
		assertThat(throwable).isNull();
	}
	
	@Então("^uma exceção deve ser lançada com a mensagem de erro:$")
	public void uma_excecao_deve_ser_lancada_com_a_mensagem_de_erro(String message)
			throws Throwable {
		assertThat(throwable).hasMessage(message);
	}
}
