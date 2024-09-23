package br.com.libdolf.sgtesig.util;

import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacionalInterceptorTest {
    private TransacionalInterceptor interceptor;
    private EntityTransaction mockTransaction;
    private InvocationContext mockContext;

    @BeforeEach
    public void setUp() {
        interceptor = new TransacionalInterceptor();
        EntityManager mockEntityManager = mock(EntityManager.class);
        mockTransaction = mock(EntityTransaction.class);
        mockContext = mock(InvocationContext.class);

        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        interceptor.setManager(mockEntityManager);
    }

    @Test
    public void testInvoke_CreatesAndCommitsTransaction() throws Exception {
        when(mockContext.proceed()).thenReturn("result");

        Object result = interceptor.invoke(mockContext);

        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
        assertEquals("result", result);
    }

    @Test
    public void testInvoke_RollbacksTransactionOnException() throws Exception {
        when(mockContext.proceed()).thenThrow(new RuntimeException("Test exception"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            interceptor.invoke(mockContext);
        });

        assertEquals("Test exception", exception.getMessage());
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }

    @Test
    public void testInvoke_NoTransactionIfAlreadyActive() throws Exception {
        when(mockTransaction.isActive()).thenReturn(true);
        when(mockContext.proceed()).thenReturn("result");

        Object result = interceptor.invoke(mockContext);

        verify(mockTransaction, never()).begin();
        verify(mockTransaction, never()).rollback();
        verify(mockTransaction).commit();
        assertEquals("result", result);
    }

}