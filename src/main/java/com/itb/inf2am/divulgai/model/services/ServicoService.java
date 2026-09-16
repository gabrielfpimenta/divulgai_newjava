package com.itb.inf2am.divulgai.model.services;

import com.itb.inf2am.divulgai.dto.ServicoDTO;
import com.itb.inf2am.divulgai.model.entity.Categoria;
import com.itb.inf2am.divulgai.model.entity.Prestador;
import com.itb.inf2am.divulgai.model.entity.Servico;
import com.itb.inf2am.divulgai.model.repository.CategoriaRepository;
import com.itb.inf2am.divulgai.model.repository.PrestadorRepository;
import com.itb.inf2am.divulgai.model.repository.ServicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public Servico findById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
    }

    public Servico save(Servico servico) {
        servico.setStatusServico(true);

        if (servico.getContador() == null) {
            servico.setContador(0);
        }

        return servicoRepository.save(servico);
    }

    public Servico saveFromDTO(ServicoDTO dto) {

        if (dto == null) {
            throw new RuntimeException("DTO nulo");
        }

        Servico servico = new Servico();

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setStatusServico(true);

        Integer contador = dto.getContador();
        if (contador == null) {
            contador = 0;
        }

        if (contador < 0) {
            throw new RuntimeException("contador não pode ser negativo");
        }

        servico.setContador(contador);

        // FOTO BASE64 → BYTE[]
        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {
                byte[] imagemBytes = Base64.getDecoder().decode(dto.getFoto());
                servico.setFoto(imagemBytes);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao decodificar imagem Base64", e);
            }
        }

        // PRESTADOR
        if (dto.getPrestadorId() == null) {
            throw new RuntimeException("prestadorId ausente");
        }

        Prestador prestador = prestadorRepository.findById(dto.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        servico.setPrestador(prestador);

        // CATEGORIA
        if (dto.getCategoriaId() == null) {
            throw new RuntimeException("categoriaId ausente");
        }

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        servico.setCategoria(categoria);

        return servicoRepository.save(servico);
    }

    public Servico updateFromDTO(Long id, ServicoDTO dto) {
        if (dto == null) {
            throw new RuntimeException("DTO nulo");
        }

        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com o ID: " + id));

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());

        if (dto.getFoto() != null && !dto.getFoto().isEmpty()) {
            try {
                byte[] imagemBytes = Base64.getDecoder().decode(dto.getFoto());
                servico.setFoto(imagemBytes);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao decodificar imagem Base64", e);
            }
        }

        if (dto.getPrestadorId() != null) {
            Prestador prestador = prestadorRepository.findById(dto.getPrestadorId())
                    .orElseThrow(() -> new RuntimeException("Prestador não encontrado: " + dto.getPrestadorId()));
            servico.setPrestador(prestador);
        }

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + dto.getCategoriaId()));
            servico.setCategoria(categoria);
        }

        return servicoRepository.save(servico);
    }

    @Transactional
    public ServicoDTO incrementarContador(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));

        servicoRepository.incrementarContadorDirect(id);

        servico.setContador((servico.getContador() == null ? 0 : servico.getContador()) + 1);

        return new ServicoDTO(servico);
    }
}