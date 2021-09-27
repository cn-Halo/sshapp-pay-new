package org.ssh.boot.api.repository;

import org.ssh.boot.api.aggregate.Order;

/**
 * @author yzm
 * @date 2021/9/27 22:36
 */
public interface OrderRepository {
    Order findById(Long id);
}
