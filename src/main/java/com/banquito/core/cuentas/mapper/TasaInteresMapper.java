// src/main/java/com/banquito/core/cuentas/mapper/TasaInteresMapper.java
package com.banquito.core.cuentas.mapper;

import com.banquito.core.cuentas.dto.*;
import com.banquito.core.cuentas.modelo.*;
import java.util.stream.Collectors;

public class TasaInteresMapper {

    public static TasaInteres toEntity(TasaInteresRequestDTO dto) {
        TasaInteres e = new TasaInteres();
        e.setBaseCalculo(dto.getBaseCalculo());
        e.setMetodoCalculo(dto.getMetodoCalculo());
        e.setFrecuenciaCapitalizacion(dto.getFrecuenciaCapitalizacion());
        e.setFechaInicioVigencia(dto.getFechaInicioVigencia());
        e.setFechaFinVigencia(dto.getFechaFinVigencia());
        e.setEstado(TasaInteres.ESTADO_ACTIVO);
        e.setVersion(1);
        e.setPlazos(dto.getPlazos().stream()
                .map(p -> {
                    TasaPlazo tp = new TasaPlazo();
                    tp.setMinMeses(p.getMinMeses());
                    tp.setMaxMeses(p.getMaxMeses());
                    tp.setTasa(p.getTasa());
                    tp.setEstado(p.getEstado() != null ? p.getEstado() : TasaInteres.ESTADO_ACTIVO);
                    tp.setVersion(p.getVersion() != null ? p.getVersion() : 1L);
                    return tp;
                })
                .collect(Collectors.toList()));
        e.setSaldos(dto.getSaldos().stream()
                .map(s -> {
                    TasaSaldo ts = new TasaSaldo();
                    ts.setSaldoMinimo(s.getSaldoMinimo());
                    ts.setSaldoMaximo(s.getSaldoMaximo());
                    ts.setTasa(s.getTasa());
                    ts.setEstado(s.getEstado() != null ? s.getEstado() : TasaInteres.ESTADO_ACTIVO);
                    ts.setVersion(s.getVersion() != null ? s.getVersion() : 1);
                    return ts;
                })
                .collect(Collectors.toList()));
        return e;
    }

    public static TasaInteresResponseDTO toDto(TasaInteres e) {
        TasaInteresResponseDTO r = new TasaInteresResponseDTO();
        r.setId(e.getId());
        r.setBaseCalculo(e.getBaseCalculo());
        r.setMetodoCalculo(e.getMetodoCalculo());
        r.setFrecuenciaCapitalizacion(e.getFrecuenciaCapitalizacion());
        r.setFechaInicioVigencia(e.getFechaInicioVigencia());
        r.setFechaFinVigencia(e.getFechaFinVigencia());
        r.setEstado(e.getEstado());
        r.setVersion(e.getVersion());
        r.setPlazos(e.getPlazos().stream()
                .map(p -> new TasaPlazoDTO(
                        p.getMinMeses(),
                        p.getMaxMeses(),
                        p.getTasa(),
                        p.getEstado(),
                        p.getVersion()))
                .collect(Collectors.toList()));
        r.setSaldos(e.getSaldos().stream()
                .map(s -> new TasaSaldoDTO(
                        s.getSaldoMinimo(),
                        s.getSaldoMaximo(),
                        s.getTasa(),
                        s.getEstado(),
                        s.getVersion()))
                .collect(Collectors.toList()));
        return r;
    }
}
