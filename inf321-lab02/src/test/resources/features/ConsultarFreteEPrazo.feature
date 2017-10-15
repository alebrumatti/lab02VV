# language: pt
Funcionalidade: Calcular frete e tempo de entrega​ previsto
	Como um usuário do sistema Bookstore
	Desejo visualizar o valor que será cobrado pela entrega do
	meu pedido e o tempo máximo que meu pedido levará para chegar
	ao endereço desejado.
	
	Cenário: Frete e tempo de entrega calculados
	  Dado uma entrega válida:
	  	| cep			| 09631050 |
	  	| peso 			| 5		   | 
	  	| largura 		| 20 	   |
	  	| altura 		| 50 	   |
	  	| comprimento 	| 50 	   |
	  	| tipoDeEntrega | 40010    |
	  Quando eu informo o CEP onde o pedido deve ser entregue
	  Entao o resultado de valor de frete e tempo de entrega deve ser:
	  	| Codigo | Valor | PrazoEntrega | ValorMaoPropria | ValorAvisoRecebimento | ValorValorDeclarado | EntregaDomiciliar | EntregaSabado | Erro | MsgErro |   	
	  	| 40010  | 13,20 | 1			| 0,00			  | 0,00				  | 0,00				| S					| S				| 0	   |		 |	 	  	
	  	
	Cenário: CEP inválido
	  Dado um CEP inválido:
		| cep | 1234567890 |
	  Quando eu informo o CEP onde o pedido deve ser entregue				  
	  Entao o resultado de valor de frete e tempo de entrega deve ser:
	  	| Erro | MsgErro 				 |   	
	  	| -3   | CEP de destino inválido |
	  
	Cenário: Erro na consulta do prazo
	  Dado uma entrega válida:
	  	| cep			| 09631050 |
	  	| peso 			| 5		   | 
	  	| largura 		| 20 	   |
	  	| altura 		| 50 	   |
	  	| comprimento 	| 50 	   |
	  	| tipoDeEntrega | 40010    |
	  E o serviço ConsultarFreteEPrazo está indisponível
	  Entao o resultado de valor de frete e tempo de entrega deve ser:
	  	| Erro | MsgErro								|
		| 7	   | Serviço indisponível, tente mais tarde |