package com.example.be_java_hisp_w23_g3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import jakarta.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ProductDTO {

    @JsonProperty("product_id")
    @NotNull(message = "El campo product_id no puede estar vacío")
    @Positive(message = "El product_id debe ser mayor que cero")
    private Long productId;

    @NotNull(message = "El campo product_name no puede estar vacío")
    @NotBlank(message = "El campo product_name no puede estar vacío")
    @Size(max = 40, message = "La longitud del campo product_name no puede superar los 40 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo product_name no puede poseer caracteres especiales")
    @JsonProperty("product_name")
    private String productName;

    @NotNull(message = "El campo type no puede estar vacío")
    @NotBlank(message = "El campo type no puede estar vacío")
    @Size(max = 15, message = "La longitud del campo no puede superar los 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo type no puede poseer caracteres especiales")
    private String type;

    @NotNull(message = "El campo brand no puede estar vacío")
    @NotBlank(message = "El campo brand no puede estar vacío")
    @Size(max = 25, message = "La longitud del campo no puede superar los 25 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo brand no puede poseer caracteres especiales")
    @JsonProperty("brand")
    private String brand;

    @NotNull(message = "El campo color no puede estar vacío")
    @NotBlank(message = "El campo color no puede estar vacío")
    @Size(max = 15, message = "La longitud del campo color no puede superar los 15 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo color no puede poseer caracteres especiales")
    @JsonProperty("color")
    private String color;

    @Size(max = 80, message = "La longitud del campo notes no puede superar los 80 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "El campo notes no puede poseer caracteres especiales")
    @JsonProperty("notes")
    private String notes;

}
