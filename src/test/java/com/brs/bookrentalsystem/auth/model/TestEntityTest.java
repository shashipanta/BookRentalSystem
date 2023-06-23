package com.brs.bookrentalsystem.auth.model;

import com.brs.bookrentalsystem.auth.repo.TestEntityRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(rollbackOn = {DataIntegrityViolationException.class})
class TestEntityTest {

    @Autowired
    private TestEntityRepo testEntityRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final TestEntity testEntity = new TestEntity();

    @Test
    @Rollback(value = false)
    public void whenEntityCreated_thenDeletedIsFalse() {
        TestEntity test = TestEntity.builder()
                .name("Default Name")
                .desc("Default description")
                .build();
//                testEntity.setDeleted(false);
        TestEntity save = testEntityRepo.save(test);
        assertNotNull(save, "Couldn't save entity");
        assertFalse(save.isDeleted(), "Default value of deleted is not set to false");
    }

    @Test
    @Rollback(value = false)
    public void shouldToggleDeleted_whenEntityIsDeleted() {

        testEntityRepo.deleteById(Short.valueOf("1"));
        try {
            TestEntity deletedTestEntity = testEntityRepo.findById(Short.valueOf("1")).orElseThrow();
            assertTrue(deletedTestEntity.isDeleted());
        } catch (Exception e){
            System.out.println("Message : " + e.getMessage());

        }

    }

    // expected exception
    @Test
    @Rollback(value = false)
    public void shouldThrowException_WhenSameEntityTriedToPersist() {
        TestEntity defaultTestEntity = TestEntity.builder()
                .name("Default Name")
                .desc("Default description")
                .build();
        try {
            TestEntity save = testEntityRepo.save(defaultTestEntity);
            fail("Duplicate value can be inserted : ");
        } catch (Exception e) {
            assertEquals(e.getMessage(), "");
        }

    }

    @Test
    @Rollback(value = false)
    public void entityIsSaved_whenUniqueNameIsProvided() {
        TestEntity uniqueTestEntity = TestEntity.builder()
                .name("Unique Name")
                .desc("Unique description")
                .build();
        TestEntity save = testEntityRepo.save(uniqueTestEntity);
        assertFalse(save.isDeleted());
        assertFalse(testEntityRepo.findById(save.getId()).orElseThrow().isDeleted(), "deleted : is not set false by default");
    }

}