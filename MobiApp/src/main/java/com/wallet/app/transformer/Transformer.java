package com.wallet.app.transformer;

import org.hibernate.service.spi.ServiceException;

public interface Transformer<T,V> {
	
	public T transform(V v) throws ServiceException;
}
