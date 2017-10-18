package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.dao.DadosDeEntregaDAO;
import br.unicamp.bookstore.model.PrecoPrazo;

public class CalculaFretePrazoService {

	private Configuracao configuracao;
	private DadosDeEntregaDAO dadosDeEntregaDAO;

	public CalculaFretePrazoService(Configuracao configuracao, DadosDeEntregaDAO dadosDeEntregaDAO) {
		this.configuracao = configuracao;
		this.dadosDeEntregaDAO = dadosDeEntregaDAO;
	}

	public PrecoPrazo calcular(String cep, String peso, String largura, String altura, String comprimento,
			String tipoDeEntrega) throws Exception {
		String url = String.format("%s/%s/xml", configuracao.getConsultaPrecoPrazoUrl(), cep);
		PrecoPrazo precoPrazo = new RemoteService().getAndParseXml(url, PrecoPrazo.class);
		
		try {
			dadosDeEntregaDAO.saveDadosDeEntrega(precoPrazo.getValorFrete(), precoPrazo.getPrazoEntrega());
		}catch (NullPointerException e) {
			return precoPrazo;
		}
		
		return precoPrazo;
	}
}
