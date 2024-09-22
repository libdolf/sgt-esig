package br.com.libdolf.sgtesig.util;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransacionalInterceptor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (manager == null) {
            throw new IllegalStateException("EntityManager is not injected");
        }

        EntityTransaction trx = manager.getTransaction();
        boolean criador = false;

        try {
            if (!trx.isActive()) {
                trx.begin();
                criador = true;
            }

            Object result = context.proceed();
            if (criador) {
                trx.commit();
            }

            return result;
        } catch (Exception e) {
            if (criador) {
                trx.rollback();
            }
            throw e;
        } finally {
            if (!criador && trx.isActive()) {
                trx.commit();
            }
        }
    }
}