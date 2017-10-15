package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.PrecoPrazo;
import br.unicamp.bookstore.model.Produto;
import br.unicamp.bookstore.model.TipoEntregaEnum;

public class CalculaFretePrazoService {
	
	private Configuracao configuracao;

	public PrecoPrazo calcular(Produto produto, TipoEntregaEnum tipoEntregar) throws Exception {
	    String url = String.format("%s/%s/xml", configuracao.getBuscarEnderecoUrl(), cep);
	    return new RemoteService().getAndParseXml(url, Endereco.class);
	  }
	
}
