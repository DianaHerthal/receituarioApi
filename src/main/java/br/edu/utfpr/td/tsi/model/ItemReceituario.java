package br.edu.utfpr.td.tsi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public class ItemReceituario {

    @Field("MEDICAMENTO_ID")
    private String medicamentoId;

    @Field("NOME_PRODUTO")
    @NotBlank(message = "Nome do medicamento é obrigatório.")
    private String nomeMedicamento;

    @Field("PRINCIPIO_ATIVO")
    private String principioAtivo;

    @Field("NUMERO_REGISTRO_PRODUTO")
    private String numeroRegistroProduto;

    @Field("DOSAGEM")
    @NotBlank(message = "Dosagem é obrigatória.")
    private String dosagem; 

    @Field("POSOLOGIA")
    @NotBlank(message = "Posologia é obrigatória.")
    private String posologia;

    @Field("FREQUENCIA")
    private String frequencia;

    @Field("DURACAO_TRATAMENTO")
    private String duracaoTratamento; 

    @Field("QUANTIDADE_PRESCRITA")
    private String quantidadePrescrita;
}
