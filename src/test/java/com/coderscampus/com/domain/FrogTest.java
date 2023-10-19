package com.coderscampus.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coderscampus.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frog.class);
        Frog frog1 = new Frog();
        frog1.setId(1L);
        Frog frog2 = new Frog();
        frog2.setId(frog1.getId());
        assertThat(frog1).isEqualTo(frog2);
        frog2.setId(2L);
        assertThat(frog1).isNotEqualTo(frog2);
        frog1.setId(null);
        assertThat(frog1).isNotEqualTo(frog2);
    }
}
