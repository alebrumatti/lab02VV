# language: pt
Funcionalidade: Consultar Prazo
  Como um usuário do sistema Bookstore
  Desejo consultar o prazo de uma entrega a partir do logradouro e cidade
  
  Cenário: Consultar o prazo de entrega de endereço válido
    Dado um endereço válido:
      | logradouro | Avenida do Estado |
      | cidade     | Sao Paulo         |
    Quando eu informo o endereço para consulta de prazo
    Então o resultado deve ser a quantidade de dias:
      | qtdDias       | 25   |

  Cenário: Consultar um endereço invalido
    Dado um endereço não existente:
      | logradouro | Prassa da Se |
      | cidade     | Sao Paulo    |
    Quando eu informo o endereço para consulta de prazo
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    O endereço informado é invalido
    """

  Cenário: Consultar o prazo de entrega de endereço válido com serviço indisponivel
    Dado um endereco válido:
      | logradouro | Praca da Se |
      | cidade     | Sao Paulo   |
    Quando eu informo o endereço para consulta de prazo
    Então uma exceção deve ser lançada com a mensagem de erro:
    """
    Serviço indisponivel
    """
