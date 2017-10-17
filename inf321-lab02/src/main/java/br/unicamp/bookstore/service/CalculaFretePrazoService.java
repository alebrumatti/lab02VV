package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.PrecoPrazo;

public class CalculaFretePrazoService {
	
	private Configuracao configuracao;

	public PrecoPrazo calcular(String cep, String peso, String largura, String altura, String comprimento, String tipoDeEntrega) throws Exception {
	    String url = String.format("%s/%s/xml", configuracao.getBuscarEnderecoUrl(), cep);
	    return new RemoteService().getAndParseXml(url, PrecoPrazo.class);
	  }
}
