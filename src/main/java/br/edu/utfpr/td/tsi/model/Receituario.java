package br.edu.utfpr.td.tsi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "receituarios")
@Getter
@Setter
public class Receituario {

    @Id
    private String id;

    @Field("DATA_EMISSAO")
    @NotNull(message = "Data de emissão é obrigatória.")
    private LocalDateTime dataEmissao = LocalDateTime.now();

    @Field("DATA_VALIDADE")
    private LocalDate dataValidade;

    @Field("TIPO_RECEITA")
    @NotBlank(message = "Tipo de receita é obrigatório.")
    private String tipoReceita; 

    @Field("PACIENTE_ID")
    private String pacienteId;

    @Field("PACIENTE_NOME")
    @NotBlank(message = "Nome do paciente é obrigatório.")
    private String pacienteNome;

    @Field("PACIENTE_CPF")
    @NotBlank(message = "CPF do paciente é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos.")
    private String pacienteCpf;

    @Field("MEDICO_ID")
    private String medicoId;

    @Field("MEDICO_NOME")
    @NotBlank(message = "Nome do médico é obrigatório.")
    private String medicoNome;

    @Field("MEDICO_CRM")
    @NotBlank(message = "CRM do médico é obrigatório.")
    private String medicoCrm;

    @Field("MEDICO_UF")
    @NotBlank(message = "UF do CRM é obrigatória.")
    private String medicoUf;

    @Field("MEDICO_ESPECIALIDADE")
    private String medicoEspecialidade;


    @Field("MEDICAMENTOS")
    @NotEmpty(message = "O receituário deve conter ao menos um medicamento.")
    @Valid
    private List<ItemReceituario> itensReceituario;

    @Field("STATUS")
    private String status = "ATIVA"; 
}
