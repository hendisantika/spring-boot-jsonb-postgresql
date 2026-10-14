package com.hendisantika.repository;

import com.hendisantika.entity.Bio;
import com.hendisantika.entity.Child;
import com.hendisantika.entity.Parent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ParentRepositoryTest {

    @Autowired
    ParentRepository parentRepository;

    @Test
    void saveAndLoadJsonbColumns() {
        Parent saved = parentRepository.saveAndFlush(new Parent("Minato",
                List.of(new Child("Naruto"), new Child("Boruto")), new Bio("Fourth Hokage")));

        Parent loaded = parentRepository.findById(saved.getId()).orElseThrow();

        assertThat(loaded.getChildren()).extracting(Child::getName).containsExactly("Naruto", "Boruto");
        assertThat(loaded.getBio().getText()).isEqualTo("Fourth Hokage");
    }
}
