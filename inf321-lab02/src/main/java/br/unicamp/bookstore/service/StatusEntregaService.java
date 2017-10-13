package br.unicamp.bookstore.service;

import br.unicamp.bookstore.Configuracao;
import br.unicamp.bookstore.model.StatusEncomenda;

public class StatusEntregaService
{
	private Configuracao configuracao;

	public StatusEncomenda buscar(String codigo) throws Exception
	{
		String url = String.format("%s/%s/xml", configuracao.getStatusEntregaUrl(), codigo);
		return new RemoteService().getAndParseXml(url, StatusEncomenda.class);
	}
}
