package com.raphaelfeitosa.springbootionic.resources;

import com.raphaelfeitosa.springbootionic.domain.Categoria;
import com.raphaelfeitosa.springbootionic.domain.Pedido;
import com.raphaelfeitosa.springbootionic.dto.CategoriaDTO;
import com.raphaelfeitosa.springbootionic.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService pedidoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Long id){

        Pedido pedido = pedidoService.find(id);
        return ResponseEntity.ok().body(pedido);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido){
        pedido = pedidoService.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
