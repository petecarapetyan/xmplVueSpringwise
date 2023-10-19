package com.coderscampus.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coderscampus.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScoreTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScoreType.class);
        ScoreType scoreType1 = new ScoreType();
        scoreType1.setId(1L);
        ScoreType scoreType2 = new ScoreType();
        scoreType2.setId(scoreType1.getId());
        assertThat(scoreType1).isEqualTo(scoreType2);
        scoreType2.setId(2L);
        assertThat(scoreType1).isNotEqualTo(scoreType2);
        scoreType1.setId(null);
        assertThat(scoreType1).isNotEqualTo(scoreType2);
    }
}
