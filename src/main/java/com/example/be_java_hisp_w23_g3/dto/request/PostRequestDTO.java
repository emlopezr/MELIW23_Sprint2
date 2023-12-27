package com.example.be_java_hisp_w23_g3.dto.request;

import com.example.be_java_hisp_w23_g3.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import jakarta.validation.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PostRequestDTO {

    @JsonProperty("user_id")
    @NotNull(message = "El user_id no puede estar vacío")
    @Positive(message = "El user_id debe ser mayor que cero")
    private Long userId;

    @NotNull(message = "La fecha no puede estar vacía")
    @NotBlank(message = "La fecha no puede estar vacía")
    @JsonProperty("date")
    private String date;

    @Valid
    @JsonProperty("product")
    private ProductDTO product;

    @NotNull(message = "El campo category no puede estar vacío")
    @JsonProperty("category")
    private int category;

    @NotNull(message = "El campo price no puede estar vacío")
    @DecimalMax(value = "10000000", message = "El precio máximo por producto es de 10.000.000")
    @JsonProperty("price")
    private Double price;

}
