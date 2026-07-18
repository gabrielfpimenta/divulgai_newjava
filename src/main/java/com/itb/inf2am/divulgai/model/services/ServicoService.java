package com.itb.inf2am.divulgai.model.services;

import com.itb.inf2am.divulgai.dto.ServicoDTO;
import com.itb.inf2am.divulgai.dto.ServicoPesquisaDTO;
import com.itb.inf2am.divulgai.model.entity.Categoria;
import com.itb.inf2am.divulgai.model.entity.Prestador;
import com.itb.inf2am.divulgai.model.entity.Servico;
import com.itb.inf2am.divulgai.model.repository.CategoriaRepository;
import com.itb.inf2am.divulgai.model.repository.PrestadorRepository;
import com.itb.inf2am.divulgai.model.repository.ServicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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


    public Page<ServicoPesquisaDTO> pesquisar(String nomeServico, Long categoriaId, String cidade, String uf,
                                              String orderBy, int page, int size) {
        int paginaSegura = Math.max(page, 0);
        int tamanhoSeguro = Math.min(Math.max(size, 1), 100);
        Sort sort = "contador_desc".equalsIgnoreCase(orderBy)
                ? Sort.by(Sort.Order.desc("contador"), Sort.Order.asc("id"))
                : Sort.by(Sort.Order.asc("nome"), Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(paginaSegura, tamanhoSeguro, sort);

        return servicoRepository.pesquisarServicos(
                normalizarTexto(nomeServico),
                categoriaId,
                normalizarTexto(cidade),
                normalizarUf(uf),
                "ATIVO",
                "ATIVO",
                pageable
        );
    }

    private String normalizarTexto(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.trim();
    }

    private String normalizarUf(String uf) {
        String valor = normalizarTexto(uf);
        return valor == null ? null : valor.toUpperCase();
    }

    public Servico findById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
    }

    public Servico save(Servico servico) {
        servico.setStatusServico("ATIVO");
        return servicoRepository.save(servico);
    }

    public Servico saveFromDTO(ServicoDTO dto) {

        if (dto == null) {
            throw new RuntimeException("DTO nulo");
        }

        Servico servico = new Servico();

        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setStatusServico("ATIVO");

        Integer contador = dto.getContador();
        if (contador == null) {
            contador = 0;
        }

        if (contador < 0) {
            throw new RuntimeException("contador não pode ser negativo");
        }

        servico.setContador(contador);

        // =========================
        // FOTO BASE64 → BYTE[]
        // =========================
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

    public ServicoDTO incrementarContador(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Integer contadorAtual = servico.getContador();

        if (contadorAtual == null) {
            contadorAtual = 0;
        }

        servico.setContador(contadorAtual + 1);

        Servico servicoSalvo = servicoRepository.save(servico);

        return new ServicoDTO(servicoSalvo);
    }
}