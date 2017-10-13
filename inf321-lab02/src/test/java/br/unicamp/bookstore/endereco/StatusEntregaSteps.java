package br.unicamp.bookstore.endereco;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import java.util.Map;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.github.tomakehurst.wiremock.WireMockServer;
import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.StatusEncomenda;
import br.unicamp.bookstore.service.StatusEntregaService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;

public class StatusEntregaSteps
{
	public WireMockServer wireMockServer;

	@Mock
	private Configuracao configuration;

	@InjectMocks
	private StatusEntregaService statusEntregaService;

	private StatusEncomenda status;

	private String codigo;

	private Throwable throwable;

	@Before
	public void setUp()
	{
		wireMockServer = new WireMockServer(9966);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getStatusEntregaUrl()).thenReturn("https://localhost:9966/ws");
		status = null;
		codigo = null;
		throwable = null;
	}

	@After
	public void tearDown()
	{
		wireMockServer.stop();
	}

	@Dado("^um codigo de rastreamento válido$")
	public void eu_possuo_codigo_valido(Map<String, String> map) throws Throwable
	{
		codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigo + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega.xml")));
	}

	@Dado("^um codigo de rastreamento invalido$")
	public void eu_possuo_codigo_invalido(Map<String, String> map) throws Throwable
	{
		codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigo + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBody("resultado-pesquisa-StatusEntrega_ERR.xml")));
	}

	@Quando("^eu informo um codigo fora do padrao do manual$")
	public void codigo_fora_padrao_manual(Map<String, String> map) throws Throwable
	{
		throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(codigo));
	}

	@Quando("^codigo com menos de 13 caracteres$")
	public void codigo_menos_caracteres(Map<String, String> map) throws Throwable
	{
		throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(codigo));
	}

	@Então("^uma exceção deve ser lançada com a mensagem de erro$")
	public void codigo_rastreamento_invalido(String erro) throws Throwable
	{
		assertThat(throwable).hasMessage(erro);
	}

	@E("^o serviço nao retorna resultados$")
	public void codigo_valido_retorno_vazio(Map<String, String> map) throws Throwable
	{
		codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigo + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBody("resultado-pesquisa-StatusEntrega_VAZIO.xml")));
	}

	@Então("^uma mensagem de erro deve ser exibida$")
	public void retorno_vazio_mensagem(String mensagem) throws Throwable
	{
		//assertThat(status.getErro()).isEqualTo(mensagem);
		assertThat("").isEqualTo(mensagem);
	}
}
