package br.com.libdolf.sgtesig.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class EntityManagerProducerTest {

    private EntityManagerProducer producer;

    @Mock
    private EntityManagerFactory mockFactory;

    @Mock
    private EntityManager mockEntityManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        producer = new EntityManagerProducer() {
            @Override
            public EntityManagerFactory createEntityManagerFactory() {
                return mockFactory;
            }
        };
    }

    @Test
    public void testCreateEntityManager() {
        when(mockFactory.createEntityManager()).thenReturn(mockEntityManager);

        EntityManager em = producer.createEntityManager();

        assertNotNull(em);
        assertEquals(mockEntityManager, em);
        verify(mockFactory).createEntityManager();
    }

    @Test
    public void testCloseEntityManager() {
        producer.closeEntityManager(mockEntityManager);

        verify(mockEntityManager).close();
    }
}
