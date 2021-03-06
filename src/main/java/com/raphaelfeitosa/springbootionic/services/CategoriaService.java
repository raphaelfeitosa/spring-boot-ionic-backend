package com.raphaelfeitosa.springbootionic.services;

import com.raphaelfeitosa.springbootionic.domain.Categoria;
import com.raphaelfeitosa.springbootionic.dto.CategoriaDTO;
import com.raphaelfeitosa.springbootionic.repositories.CategoriaRepository;
import com.raphaelfeitosa.springbootionic.services.exceptions.DataIntegrityException;
import com.raphaelfeitosa.springbootionic.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria find(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        Categoria newCategoria = find(categoria.getId());
        updateData(newCategoria, categoria);
        return categoriaRepository.save(newCategoria);
    }

    private void updateData(Categoria newCategoria, Categoria cliente) {
        newCategoria.setNome(cliente.getNome());
    }

    public void delete(Long id) {
        find(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possuí produtos relacionados");
        }
    }

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    //Paginação de categoria
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoriaRepository.findAll(pageRequest);
    }
    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
}

}
