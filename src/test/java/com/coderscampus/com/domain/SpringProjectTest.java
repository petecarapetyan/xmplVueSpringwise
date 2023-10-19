package com.coderscampus.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coderscampus.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpringProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpringProject.class);
        SpringProject springProject1 = new SpringProject();
        springProject1.setId(1L);
        SpringProject springProject2 = new SpringProject();
        springProject2.setId(springProject1.getId());
        assertThat(springProject1).isEqualTo(springProject2);
        springProject2.setId(2L);
        assertThat(springProject1).isNotEqualTo(springProject2);
        springProject1.setId(null);
        assertThat(springProject1).isNotEqualTo(springProject2);
    }
}
