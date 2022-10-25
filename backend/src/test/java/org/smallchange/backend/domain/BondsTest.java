package org.smallchange.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.smallchange.backend.web.rest.TestUtil;

class BondsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bonds.class);
        Bonds bonds1 = new Bonds();
        bonds1.setId(1L);
        Bonds bonds2 = new Bonds();
        bonds2.setId(bonds1.getId());
        assertThat(bonds1).isEqualTo(bonds2);
        bonds2.setId(2L);
        assertThat(bonds1).isNotEqualTo(bonds2);
        bonds1.setId(null);
        assertThat(bonds1).isNotEqualTo(bonds2);
    }
}
