package com.pcr.urlshortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {}