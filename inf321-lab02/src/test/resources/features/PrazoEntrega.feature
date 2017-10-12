# language: pt
Funcionalidade: Calcular frete e tempo de entrega​ previsto
	Como um usuário do sistema Bookstore
	Desejo visualizar o valor que será cobrado pela entrega do
	meu pedido e o tempo máximo que meu pedido levará para chegar
	ao endereço desejado.
	
	Cenário: Frete e tempo de entrega calculados
	  Dado um produto válido:
	  	| peso | 5 |
	  	| largura | 20 |
	  	| altura | 50 |
	  	| comprimento | 50 |
	  Dado um tipo de entrega válido:
	  	| tipoDeEntrega | PAC |
	    	
	  		  	
	  	
	Cenário: Erro na consulta do frete
	
	Cenário: Dado de frete enviado incorreto
	
	Cenário: Serviço dos Correios não retornou resposta de frete
	
	Cenário: Erro na consulta do prazo
	
	Cenário: Dado de prazo enviado incorreto
	
	Cenário: Serviço dos Correios não retornou resposta de prazo
	
	Cenário: CEP inválido  