package com.coderscampus.com.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.coderscampus.com.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CodingCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CodingCategory.class);
        CodingCategory codingCategory1 = new CodingCategory();
        codingCategory1.setId(1L);
        CodingCategory codingCategory2 = new CodingCategory();
        codingCategory2.setId(codingCategory1.getId());
        assertThat(codingCategory1).isEqualTo(codingCategory2);
        codingCategory2.setId(2L);
        assertThat(codingCategory1).isNotEqualTo(codingCategory2);
        codingCategory1.setId(null);
        assertThat(codingCategory1).isNotEqualTo(codingCategory2);
    }
}
