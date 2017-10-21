package br.com.unicamp.bookstore.status;

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
import br.unicamp.bookstore.model.StatusEncomenda;
import br.unicamp.bookstore.service.StatusEntregaService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Quando;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;

public class ConsultaStatusEntregaSteps {
	
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
		wireMockServer = new WireMockServer(9876);
		wireMockServer.start();
		MockitoAnnotations.initMocks(this);
		Mockito.when(configuration.getStatusEntregaUrl()).thenReturn("http://localhost:9876/ws");
		status = null;
		codigo = null;
		throwable = null;
	}

	@After
	public void tearDown()
	{
		wireMockServer.stop();
	}
	
	
	@Dado("^um codigo de rastreamento válido:$")
	public void um_codigo_de_rastreamento_válido(Map<String, String> map) throws Throwable {
		codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigo + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega.xml")));
	}

	@Quando("^eu informo o codigo de rastreamento válido$")
	public void eu_informo_o_codigo_de_rastreamento_válido() throws Throwable {
		throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(codigo));
	}

	@Então("^o resultado deve ser:$")
	public void o_resultado_deve_ser(List<Map<String,String>> resultado) throws Throwable {
		assertThat(this.status.getCodigo()).isEqualTo(resultado.get(0).get("Codigo"));
		assertThat(this.status.getDescricao()).isEqualTo(resultado.get(0).get("Descricao"));
		assertThat(throwable).isNull();
	}

	@Dado("^um codigo de rastreamento invalido:$")
	public void um_codigo_de_rastreamento_invalido(Map<String, String> map) throws Throwable {
		codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + codigo + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega_ERR.xml")));
	}

	@Quando("^eu informo um codigo fora do padrao do manual$")
	public void eu_informo_um_codigo_fora_do_padrao_do_manual() throws Throwable {
	    throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(codigo));
	}	

	@Quando("^eu informo um codigo com menos de 13 caracteres$")
	public void eu_informo_um_codigo_com_menos_de_caracteres() throws Throwable {
		throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(codigo));
	}

	@E("^o serviço nao retorna resultados$")
	public void o_serviço_nao_retorna_resultados() throws Throwable {
		//codigo = map.get("codigo");
		wireMockServer.stubFor(get(urlMatching("/ws/" + ".*")).willReturn(aResponse().withStatus(200)
				.withHeader("Content-Type", "text/xml").withBodyFile("resultado-pesquisa-StatusEntrega_VAZIO.xml")));
		
		throwable = catchThrowable(() -> this.status = statusEntregaService.buscar(this.codigo));
	}
	
	@Então("^uma exceção deve ser lançada com a mensagem de erro:$")
	public void uma_exceção_deve_ser_lançada_com_a_mensagem_de_erro(String erro) throws Throwable {
		assertThat(this.status.getErro()).isEqualTo(erro);
	}
}
