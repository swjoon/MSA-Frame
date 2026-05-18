package app.backend.itemservice.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.backend.itemservice.item.entity.Item;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, Long> {



}
