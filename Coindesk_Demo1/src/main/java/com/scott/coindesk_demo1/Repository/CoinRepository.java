package com.scott.coindesk_demo1.Repository;

import com.scott.coindesk_demo1.pojo.CoinDesk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<CoinDesk, String> {

//    CoinDesk findByCode(String code);

    Optional<CoinDesk> findByCode(String s);

    @Transactional
    int deleteByCode(String code);
}
