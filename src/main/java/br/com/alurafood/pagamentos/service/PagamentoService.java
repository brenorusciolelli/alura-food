package br.com.alurafood.pagamentos.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper; 

    public Page<PagamentoDTO> getAll(Pageable pagination) {
        return repository
               .findAll(pagination)
               .map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO getByID(Long id) {
        Pagamento pagamento = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException());

            return modelMapper.map(pagamento, PagamentoDTO.class);
    }
    
    public PagamentoDTO createPayment(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO editPayment(PagamentoDTO pagamentoDTO, Long id) {
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);
        
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public void deletePayment(Long id){
        repository.deleteById(id);
    }
}
