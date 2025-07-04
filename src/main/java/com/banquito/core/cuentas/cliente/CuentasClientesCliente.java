package com.banquito.core.cuentas.cliente;

import com.banquito.core.cuentas.dto.TransaccionesSolicitudDTO;
import com.banquito.core.cuentas.dto.TransaccionesRespuestaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
  name = "cuentas-transaccional",
  url  = "${cuentas.transaccional.url}"
)
public interface CuentasClientesCliente {

    @PostMapping("/api/v1/transacciones/retiro")
    TransaccionesRespuestaDTO retiro(@RequestBody TransaccionesSolicitudDTO dto);

}
