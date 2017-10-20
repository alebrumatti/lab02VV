# language: pt
Funcionalidade: Consulta de Status de Entrega
  Como um usuário do sistema Bookstore
  Desejo consultar o status de entrega do meu pedido
  Para que eu possa acompanhar os detalhes do andamento de entrega

  Cenário: Consultar com código válido
    Dado um codigo de rastreamento válido:
      | codigo | SQ458226057BR |
    Quando eu informo o codigo de rastreamento válido
    Então o resultado deve ser:
      | Codigo        | Status			|
      | SQ458226057BR | Objeto Postado 	|
      
   Cenário: Consultar um codigo de postagem inválido
    Dado um codigo de rastreamento invalido:
      | codigo        | 11CCCCCCCCC99 |    
    Quando eu informo um codigo fora do padrao do manual
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    Código de postagem inválido
    """    
    
  Cenário: Consultar um codigo de postagem inválido
    Dado um codigo de rastreamento invalido:
      | codigo 		| SQ4586057BR |
    Quando eu informo um codigo com menos de 13 caracteres
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    Código de postagem inválido
    """

  Cenário: Consultar com código válido sem retorno
    Dado um codigo de rastreamento válido:
      | codigo | SQ458226057BR |
    Quando eu informo o codigo de rastreamento válido
    E o serviço nao retorna resultados
    Então uma mensagem de erro deve ser exibida
    """
    Rastreamento não localizado
    """
  