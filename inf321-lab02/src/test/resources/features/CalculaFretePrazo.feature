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
	  Então o resultado deve ser o valor de frete e tempo de entrega:
	  	| Codigo | Valor | PrazoEntrega | ValorMaoPropria | ValorAvisoRecebimento | ValorValorDeclarado | EntregaDomiciliar | EntregaSabado | Erro | MsgErro |   	
	  	| 41106  | 16,10 | 5			| 0,00			  | 0,00				  | 0,00				| S					| N				| 0	   |		 |	 	  	
	  E o resultado deve ser salvo no banco de dados
	  	
	Cenário: CEP inválido
	  Dado um CEP inválido:
		| cep | 1234567890 |
	  Quando eu informo o CEP onde o pedido deve ser entregue				  
	  Então uma exceção deve ser lançada com o erro e a mensagem de erro:
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
	  E o serviço CalculaFretePrazo está indisponível
	  Quando eu informo o CEP onde o pedido deve ser entregue	
	  Então uma exceção deve ser lançada com o erro e a mensagem de erro:
	  	| Erro | MsgErro								|
		| 7	   | Serviço indisponível, tente mais tarde |
		