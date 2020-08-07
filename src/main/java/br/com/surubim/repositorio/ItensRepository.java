package br.com.surubim.repositorio;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.surubim.model.ItemEntity;

public interface ItensRepository extends PagingAndSortingRepository<ItemEntity, Long> {
	List<ItemEntity> findByNameIgnoreCaseContaining(String name);
}