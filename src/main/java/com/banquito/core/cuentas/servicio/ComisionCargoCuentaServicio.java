package com.banquito.core.cuentas.servicio;

import com.banquito.core.cuentas.cliente.CuentasClientesCliente;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaRequestDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaResponseDTO;
import com.banquito.core.cuentas.dto.ComisionCargoCuentaDetalleDTO;
import com.banquito.core.cuentas.dto.TransaccionesSolicitudDTO;
import com.banquito.core.cuentas.dto.TransaccionesRespuestaDTO;
import com.banquito.core.cuentas.enums.TipoTransaccionEnum;
import com.banquito.core.cuentas.excepciones.RecursoDuplicadoException;
import com.banquito.core.cuentas.excepciones.RecursoNoEncontradoException;
import com.banquito.core.cuentas.mapper.ComisionCargoCuentaMapper;
import com.banquito.core.cuentas.modelo.ComisionCargo;
import com.banquito.core.cuentas.modelo.ComisionCargoCuenta;
import com.banquito.core.cuentas.repositorio.ComisionCargoCuentaRepositorio;
import com.banquito.core.cuentas.repositorio.ComisionCargoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ComisionCargoCuentaServicio {

    private final ComisionCargoCuentaRepositorio asignRepo;
    private final ComisionCargoRepositorio cargoRepo;
    private final CuentasClientesCliente cuentasCliente;

    public ComisionCargoCuentaServicio(
            ComisionCargoCuentaRepositorio asignRepo,
            ComisionCargoRepositorio cargoRepo,
            CuentasClientesCliente cuentasCliente) {
        this.asignRepo = asignRepo;
        this.cargoRepo = cargoRepo;
        this.cuentasCliente = cuentasCliente;
    }

    /** Asigna una comisión/cargo a una cuenta (evitando duplicados) */
    public ComisionCargoCuentaResponseDTO asignar(ComisionCargoCuentaRequestDTO dto) {
        log.info("Asignando comision/cargo {} a cuentaCliente {}", dto.getComisionCargoId(), dto.getCuentaClienteId());
        boolean dup = asignRepo.existsByComisionCargoIdAndCuentaClienteIdAndEstado(
                dto.getComisionCargoId(), dto.getCuentaClienteId(), ComisionCargoCuenta.ESTADO_ACTIVO);
        if (dup) {
            throw new RecursoDuplicadoException("Ya existe una asignación activa");
        }
        ComisionCargoCuenta e = ComisionCargoCuentaMapper.toEntity(dto);
        ComisionCargoCuenta saved = asignRepo.save(e);
        return ComisionCargoCuentaMapper.toDto(saved);
    }

    /** Desactiva la asignación (borrado lógico) */
    public void desactivar(String id) {
        log.info("Desactivando asignacion {}", id);
        ComisionCargoCuenta e = asignRepo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargoCuenta", id));
        if (!ComisionCargoCuenta.ESTADO_INACTIVO.equals(e.getEstado())) {
            e.setEstado(ComisionCargoCuenta.ESTADO_INACTIVO);
            e.setVersion(e.getVersion() + 1);
            asignRepo.save(e);
        }
    }

    /** Lista las comisiones/cargos activos de una cuenta, con nombre y monto */
    public List<ComisionCargoCuentaDetalleDTO> listarPorCuenta(String cuentaClienteId) {
        log.info("Listando comisiones para cuentaCliente {}", cuentaClienteId);
        return asignRepo.findByCuentaClienteIdAndEstado(cuentaClienteId, ComisionCargoCuenta.ESTADO_ACTIVO)
                .stream()
                .map(asign -> {
                    ComisionCargo c = cargoRepo.findById(asign.getComisionCargoId())
                            .orElseThrow(() -> new RecursoNoEncontradoException(
                                    "ComisionCargo", asign.getComisionCargoId()));
                    return new ComisionCargoCuentaDetalleDTO(
                            asign.getId(),
                            c.getNombre(),
                            c.getMonto(),
                            c.getFrecuencia(),
                            asign.getFechaAsignacion(),
                            asign.getEstado(),
                            asign.getVersion());
                })
                .collect(Collectors.toList());
    }

    /**
     * Procesa el cobro de una comisión: debita el saldo del cliente vía
     * microservicio transaccional
     */
    public TransaccionesRespuestaDTO procesarCobro(String asignacionId) {
        log.info("Procesando cobro de comision asignacion {}", asignacionId);
        ComisionCargoCuenta asign = asignRepo.findById(asignacionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargoCuenta", asignacionId));
        ComisionCargo c = cargoRepo.findById(asign.getComisionCargoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("ComisionCargo", asign.getComisionCargoId()));

        // Preparar solicitud de retiro
        TransaccionesSolicitudDTO sol = TransaccionesSolicitudDTO.builder()
                .idCuentaClienteOrigen(Integer.valueOf(asign.getCuentaClienteId()))
                .tipoTransaccion(TipoTransaccionEnum.RETIRO)
                .monto(c.getMonto())
                .descripcion("Cobro de comision: " + c.getNombre())
                .build();

        // Llamada Feign al servicio transaccional
        TransaccionesRespuestaDTO resp = cuentasCliente.retiro(sol);
        log.info("Cobro procesado, transacción ID={}", resp.getId());
        return resp;
    }
}