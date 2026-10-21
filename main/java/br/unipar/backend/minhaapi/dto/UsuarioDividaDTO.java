
package br.unipar.backend.minhaapi.dto;

import java.math.BigDecimal;

public record UsuarioDividaDTO(
        String nome,
        BigDecimal totalDivida
) {
}
