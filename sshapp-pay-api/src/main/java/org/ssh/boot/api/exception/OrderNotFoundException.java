package org.ssh.boot.api.exception;

/**
 * Created on 2021/10/8.
 *
 * @author yzm
 */
public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
