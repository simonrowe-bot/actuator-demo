package io.pivotal.pa.actuatordemo;

import org.springframework.data.repository.CrudRepository;

public interface CounterDao extends CrudRepository<Counter, Long> {
}
